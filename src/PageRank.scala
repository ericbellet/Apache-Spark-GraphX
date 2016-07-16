// En el siguiente codigo se quiere medir la influencia de papers cientificos dada una coleccion de citaciones bibliograficas
// utilizando PageRank orientado a objetos,
//spark-shell -i src/PageRank.scala
// El underscore es un wildcard que indica que todo lo de esa biblioteca debe ser importado
import org.apache.spark.graphx._

// GraphLoader es un objeto de la biblioteca GraphX que contiene un metodo llamado
// edgeListFile que permite cargar grafos de un formato .txt que contenga una lista de aristas

// edgeListFile recibe 2 parametros, el primero es SparkContext (sc) que es instanciada
// por la consola de Spark org.apache.spark.SparkContext que es el punto de entrada
// para distintas funcionalidades de Spark, y el segundo parametro es el archivo .txt

val PageRankGraph = GraphLoader.edgeListFile(sc, "Paper_citation_network.txt")
// Val es data inmutable  HEP-TH graph loaded into memory

//Analisis exploratorio
PageRankGraph.vertices.take(15)

//Cuando se ejecuta la funcion pageRank se crea otro grafo donde los vertices
//tienen como atributos los valores del PageRank.
//pageRank le asigna a cada vertice la medida de influencia que tiene contra
//el resto del grafo

val PRGraph = PageRankGraph.pageRank(0.001).vertices
 
//El valos 0,001 es la tolerancia que permite establecer un equilibro entre la
//velocidad y la exactitud del resultado final.
//Si la tolerancia es muy alto el algoritmo termina rapidamente su ejecucion
//pero se obtienen resultados imprecisos.
//Si la tolerancia es muy baja el algortimo tarda mucho tiempo en ejecutarse
//y no anade precision a los resultados.


//Obtengamos el vertice con el valor PageRank mas alto
//a._2 retorna el segundo valor de una tupla. (Funcion anonima)

PRGraph.reduce((a,b) => if (a._2 > b._2) a else b)

//Observemos algunos vertices y su PageRank
PRGraph.take(15)


