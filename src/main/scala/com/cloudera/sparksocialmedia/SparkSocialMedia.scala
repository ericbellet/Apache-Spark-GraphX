/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
----------------------------------------------------------------------RUN CODE------------------------------------------------------------------------------------------------------------
Ejecutar script en modo LOCAL:

spark-submit --class com.cloudera.sparksocialmedia.SparkSocialMedia --master local target/scala-2.10/grafos-de-gran-escala_2.10-1.0.jar input egonets Descripcion ShortestPaths TriangleCount PageRank ConnectedComponents

Ejecutar script en modo YARN CLIENT:

spark-submit --class com.cloudera.sparksocialmedia.SparkSocialMedia --master yarn --deploy-mode client target/scala-2.10/grafos-de-gran-escala_2.10-1.0.jar input egonets Descripcion ShortestPaths TriangleCount PageRank ConnectedComponents

Ejecutar script en modo YARN CLUSTER:

---------------------------------------------------------------------INSTALL SBT---------------------------------------------------------------------------------------------------------------
Instalar y empaquetar con sbt:f
wget http://dl.bintray.com/sbt/rpm/sbt-0.13.5.rpm
sudo yum localinstall sbt-0.13.5.rpm
sbt -version
sbt package

--------------------------------------------------------------------SAVE DATA IN HDFS---------------------------------------------------------------------------------------------------------
Guardar los datasets en HDFS:

	hadoop fs -mkdir input
	hadoop fs -put Facebook.txt input

	hadoop fs -mkdir friendster
	hadoop fs -put Friendster.txt friendster

	hadoop fs -put egonets

-------------------------------------------------------------------REMOVE DATASET OF HDFS-----------------------------------------------------------------------------------------------------
Para volver ejecutar el codigo es necesario eliminar los resultados:

	hdfs dfs -rmr Descripcion
	hdfs dfs -rmr ShortestPaths
	hdfs dfs -rmr TriangleCount
	hdfs dfs -rmr PageRank
	hdfs dfs -rmr ConnectedComponents

	hdfs dfs -ls

-------------------------------------------------------------------OBTAIN RESULTS------------------------------------------------------------------------------------------------------------
Para visualizar los resultados en consola se utiliza "cat" y para guardar los archivos de forma local se utiliza "get":
*/
//	hadoop fs -cat Descripcion/*
//	hadoop fs -get Descripcion ./output

//	hadoop fs -cat ShortestPaths/*
//	hadoop fs -get ShortestPaths ./output

//	hadoop fs -cat TriangleCount/*
//	hadoop fs -get TriangleCount ./output

//	hadoop fs -cat ConnectedComponents/*
//	hadoop fs -get ConnectedComponents ./output

//	hadoop fs -cat PageRank/*
//	hadoop fs -get PageRank ./output

//	cd gephi

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//										sparksocialmedia
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

package com.cloudera.sparksocialmedia

//Importar las bibliotecas
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.graphx._
import org.apache.spark.rdd._

object SparkSocialMedia extends App {
	 
	//Generar un SparkContext
	val sc = new SparkContext(new SparkConf().setAppName("Social Media"))
	//Cargar grafo de HDFS
	val grafo = GraphLoader.edgeListFile(sc, args(0))

//-----------------------------------------------------------------------------ALGORITMO SHORTESTPATHS-----------------------------------------------------------------------------
	println("----------------------ShortestPaths---------------------- \n")
	//Obtengo todos los nodos y los almaceno en una secuencia
	val x = grafo.vertices.collect()
	val nodos = x.map(_._1).toSeq

	//Ejecuto ShortestPath
	//nodos.take(n) calcula la distancia de algunos nodos
	//nodos calcula la distancia de todos los nodos
	val shortest = lib.ShortestPaths.run(grafo,nodos.take(2)).vertices.collect()
	sc.parallelize(shortest,1).saveAsTextFile(args(3))

	//Calculo la distancia promedio
	//Almaceno todas las distancias
	val otro = shortest.map(_._2).toList

	//Calculo la suma de todas las distancias
	val total = for (e <- otro) yield  e.values.sum

	//Calculo la cantidad de distancias que existen
	val tamano = for (e <- otro) yield  e.size
	//Calculo el promedio
	val promedio = total.sum / tamano.sum

//-----------------------------------------------------------------------------ALGORITMO TRIANGLECOUNT-----------------------------------------------------------------------------
	println("----------------------TriangleCount---------------------- \n")

	//Calculo la cantidad de triangulos que posee cada nodo
	val triangulos = grafo.triangleCount.vertices.sortBy(_._2,ascending=false).collect()

	sc.parallelize(triangulos,1).saveAsTextFile(args(4))


//-----------------------------------------------------------------------------ALGORITMO PAGERANK-----------------------------------------------------------------------------------
	println("----------------------PageRank---------------------- \n")

	//Calculo la influencia de cada nodo y las ordeno de mayor a menor

	val influyentes = grafo.pageRank(0.001).vertices.sortBy(_._2,ascending=false).collect()
	sc.parallelize(influyentes,1).saveAsTextFile(args(5))



//-------------------------------------------------------------------ALGORITMO CONNECTEDCOMPONENTS-----------------------------------------------------------------------------------
	println("----------------------ConnectedComponents---------------------- \n")
	//Vamos a predecir circulos sociales
	//Un circulo social es algún tipo de agrupación de amigos de un usuario que tienen sentido para el.
	//Los datos se recogieron a partir de un pequeño número de usuarios de Facebook que habían suministrado información sobre amigos en su red.
	//Egonet:  describe individual users as egos and users’ connections as alters. 
	//El archivo Egonet enumera cada uno de los amigos del usuario y, para cada uno de esos amigos, sus conexiones.


	// returns the userId from a file path with the format
	// Retorna el userId de la ruta
	// <path>/<userId>.egonet
	def extraer(s: String) = {
	 val Pattern = """^.*?(\d+).egonet""".r
	 val Pattern(num) = s
	 num
	}
	//Busca y genera una lista de circulos de amigos
	// Procesa cada linea y retorna un arreglo de aristas en tuplas

	def get_edges_from_line(line: String): Array[(Long, Long)] = {
	 val ary = line.split(":")
	 val srcId = ary(0).toInt
	 val dstIds = ary(1).split(" ")
	 val edges = for {
	dstId <- dstIds
	if (dstId != "")
	} yield {
	(srcId.toLong, dstId.toLong)
	}
	// A subtle point: if the user is not connected to
	// anyone else then we generate a "self-connection"
	// so that the vertex will be included in the graph
	// created by Graph.fromEdgeTuples.
	if (edges.size > 0) edges else Array((srcId, srcId))
	}
	//-------------------------------------------
	// Constructs Edges tuples from an egonet file
	// contents
	def crear_aristas(contents: String) = {
	 val lines = contents.split("\n")
	 val unflat = for {
	line <- lines
	 } yield {
	get_edges_from_line(line)
	 }
	 //Necesito un arreglo de tuplas para poderselo pasar a Graph.fromEdgeTuples
	 //pero tengo un arreglo de arreglos de tuplas.
	 //Utilizo la funcion flatten para solventar el problema
	 
	 val flat = unflat.flatten
	 flat
	}
	//-------------------------------------------
	//Construyo un grafo utilizando las tuplas de aristas y ejecuto connectedComponents que me retorna un string

	def obtener_circulos(flat: Array[(Long, Long)]) = {
	 val edges = sc.makeRDD(flat)
	 val g = Graph.fromEdgeTuples(edges,1)
	 val cc = g.connectedComponents()
	cc.vertices.map(x => (x._2, Array(x._1))).
	reduceByKey( (a,b) => a ++ b).
	values.map(_.mkString(" ")).collect.mkString(" | ")
	}

	//-------------------------------------------
	val egonets = sc.wholeTextFiles(args(1))
	//wholeTextFiles retorna un PairRDD con un elemento de cada archivo donde la clave es el
	//ruta de la carpeta en el archivo, y el valor es el contenido del archivo

	val egonet_numbers = egonets.map(x => extraer(x._1)).collect
	//extract: utiliza una expresión regular para extraer el ID de usuario del nombre de archivo

	val egonet_edges = egonets.map(x => crear_aristas(x._2)).collect
	//crear_aristas: crea aristas entre cada amigo

	val egonet_circles = egonet_edges.toList.map(x => obtener_circulos(x))



	val result = egonet_numbers.zip(egonet_circles).map(x => "Egonet: " + x._1 +".\n" + "Componentes: " + x._2 + ".\n")



	sc.parallelize(List((result.mkString("\n"))),1).saveAsTextFile(args(6))

//-------------------------------------------------------------------ALGORITMO GEPHI-----------------------------------------------------------------------------------
	println("----------------------Gephi---------------------- \n")
//Creo un .gexf del grafo 
	def toGexf[VD,ED](g:Graph[VD,ED]) = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<gexf xmlns=\"http://www.gexf.net/1.2draft\" version=\"1.2\">\n" + "  <graph mode=\"static\" defaultedgetype=\"directed\">\n" + "    <nodes>\n" + g.vertices.map(v => "      <node id=\"" + v._1 + "\" label=\"" + v._2 + "\" />\n").collect.mkString + "    </nodes>\n" + "    <edges>\n" + g.edges.map(e => "      <edge source=\"" + e.srcId + "\" target=\"" + e.dstId + "\" label=\"" + e.attr + "\" />\n").collect.mkString + "    </edges>\n" + "  </graph>\n" + "</gexf>"

	val pw = new java.io.PrintWriter("gephi/grafo.gexf")
	pw.write(toGexf(grafo))
	pw.close

//-------------------------------------------------------------------ALGORITMO DESCRIPCION-----------------------------------------------------------------------------------
	println("----------------------Descripcion---------------------- \n")
	//Calculo la cantidad de vertices y aristas del grafo
	val aristas = grafo.numEdges
	val vertices = grafo.numVertices

	//Calculo el maximo grado
	def max(a: (VertexId, Int), b: (VertexId, Int)): (VertexId, Int) = {
	if (a._2 > b._2) a else b
	}

	//Calculo el minimo grado
	def min(a: (VertexId, Int), b: (VertexId, Int)): (VertexId, Int) = {
	if (a._2 <= b._2) a else b
	}
	
	//Obtengo el maximo outdegree
	val outMax = grafo.outDegrees.reduce(max)

	//Obtengo el maximo indegree
	val inMax = grafo.inDegrees.reduce(max)

	//Obtengo el minimo outdegree
	val outMin = grafo.outDegrees.reduce(min)

	//Obtengo el minimo indegree
	val inMin = grafo.inDegrees.reduce(min)


	//Calculo el total de triangulos, los 10 usuarios mas influyentes y la distancia promedio.
	 val descripcion = "Número de vértices: " + vertices + ".\n" + "Número de aristas: " + aristas + ".\n" + "Máximo outDegrees: " + "Nodo -> " + outMax._1 + ", Grados -> " + outMax._2 + ".\n" + "Máximo inDegrees: " + "Nodo -> " + inMax._1 + ", Grados -> " + inMax._2 + ".\n" + "Mínimo outDegrees: " + "Nodo -> " + outMin._1 + ", Grados -> " + outMin._2 + ".\n" + "Mínimo inDegrees: " + "Nodo -> " + inMin._1 + ", Grados -> " + inMin._2 + ".\n" + "Total de triángulos: " + triangulos.map(_._2).toSeq.foldLeft(0)(_+_) + ".\n" + "Usuarios más influyentes: " + influyentes.take(10).toList + ".\n" + "Distancia promedio entre todos los nodos del grafo: " + promedio + ".\n" 

	sc.parallelize(List((descripcion)),1).saveAsTextFile(args(2))


}
