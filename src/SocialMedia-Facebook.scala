//spark-shell --jars lib/gs-core-1.2.jar,lib/gs-ui-1.2.jar,lib/jcommon-1.0.16.jar,lib/jfreechart-1.0.13.jar,lib/breeze_2.10-0.12.jar,lib/breeze-viz_2.10-0.12.jar,lib/pherd-1.0.jar -i src///SocialMedia-Facebook.scala
// Set log level to error, suppress info and warn messages
//

import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.graphstream.graph.implementations._

Logger.getLogger("org").setLevel(Level.ERROR)
Logger.getLogger("akka").setLevel(Level.ERROR)


//
// Hands On: Building A Graph
//
import org.apache.spark.graphx._
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

val conf = new SparkConf().setAppName("cust data").setMaster("local[*]");
val sc = new SparkContext(conf)


val facebookGraph = GraphLoader.edgeListFile(sc, "data/facebook_combined.txt")



//
// Hands On: Network Connectedness and Clustering Components
//

import org.graphstream.graph.implementations._

val graph: SingleGraph = new SingleGraph("facebookGraph")

// Set up the visual attributes for graph visualization.
graph.addAttribute("ui.stylesheet","url(file://style/stylesheet-simple)")
graph.addAttribute("ui.quality")
graph.addAttribute("ui.antialias")
// Given the facebookGraph, load the graphX vertices into GraphStream
for ((id, _) <- facebookGraph.vertices.collect()) {
  graph.addNode(id.toString).asInstanceOf[SingleNode]
}

// Load the graphX edges into GraphStream edges
for ((Edge(x, y, _), count) <- facebookGraph.edges.collect().zipWithIndex) {
  graph.addEdge(count.toString, x.toString, y.toString).asInstanceOf[AbstractEdge]
}


facebookGraph.numEdges
facebookGraph.numVertices


def max(a: (VertexId, Int), b: (VertexId, Int)): (VertexId, Int) = {
if (a._2 > b._2) a else b
}

def min(a: (VertexId, Int), b: (VertexId, Int)): (VertexId, Int) = {
if (a._2 <= b._2) a else b
}

println("----------------------outDegrees---------------------- \n")
facebookGraph.outDegrees.reduce(max)
println("----------------------inDegrees---------------------- \n")
facebookGraph.inDegrees.reduce(max)

println("----------------------ShortestPaths---------------------- \n")
//Invoking ShortestPaths
lib.ShortestPaths.run(facebookGraph,Array(3)).vertices.collect

println("----------------------LabelPropagation---------------------- \n")
//Invoking LabelPropagation

lib.LabelPropagation.run(facebookGraph,5).vertices.collect. sortWith(_._1<_._1)

 
println("----------------------PageRank---------------------- \n")


val fb = facebookGraph.pageRank(0.001).vertices


fb.reduce((a,b) => if (a._2 > b._2) a else b)

//Observemos algunos vertices y su PageRank
fb.take(15)


println("----------------------connectedComponents---------------------- \n")
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
values.map(_.mkString(" ")).collect.mkString(";")
}

//-------------------------------------------
val egonets = sc.wholeTextFiles("socialcircles/data/egonets")
//wholeTextFiles retorna un PairRDD con un elemento de cada archivo donde la clave es el
//ruta de la carpeta en el archivo, y el valor es el contenido del archivo

val egonet_numbers = egonets.map(x => extraer(x._1)).collect
//extract: utiliza una expresión regular para extraer el ID de usuario del nombre de archivo

val egonet_edges = egonets.map(x => crear_aristas(x._2)).collect
//crear_aristas: crea aristas entre cada amigo

val egonet_circles = egonet_edges.toList.map(x => obtener_circulos(x))

println("UserId,Prediction")
//Invoking connectedComponents()
val result = egonet_numbers.zip(egonet_circles).map(x => x._1 + "," + x._2)

println(result.mkString("\n"))


// Display the graph.
graph.display()


