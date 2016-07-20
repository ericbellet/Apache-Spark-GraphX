
//Invoking ShortestPaths
lib.ShortestPaths.run(myGraph,Array(3)).vertices.collect

//Invoking connectedComponents()
val egonets = sc.wholeTextFiles("egonets")

// returns the userId from a file path with the format
// <path>/<userId>.egonet
def extract(s: String) = {
 val Pattern = """^.*?(\d+).egonet""".r
 val Pattern(num) = s
 num
}
//Find and list social circles
// Processes a line from an egonet file to return a
// Array of edges in a tuple
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
// Constructs Edges tuples from an egonet file
// contents
def make_edges(contents: String) = {
 val lines = contents.split("\n")
 val unflat = for {
line <- lines
 } yield {
get_edges_from_line(line)
 }
 // We want an Array of tuples to pass to Graph.fromEdgeTuples
 // but we have an Array of Arrays of tuples. Luckily we can
 // call flatten() to sort this out.
 val flat = unflat.flatten
 flat
}
// Constructs a graph from Edge tuples
// and runs connectedComponents returning
// the results as a string
def get_circles(flat: Array[(Long, Long)]) = {
 val edges = sc.makeRDD(flat)
 val g = Graph.fromEdgeTuples(edges,1)
 val cc = g.connectedComponents()
cc.vertices.map(x => (x._2, Array(x._1))).
reduceByKey( (a,b) => a ++ b).
values.map(_.mkString(" ")).collect.mkString(";")
}
val egonets = sc.wholeTextFiles("socialcircles/data/egonets")
val egonet_numbers = egonets.map(x => extract(x._1)).collect
val egonet_edges = egonets.map(x => make_edges(x._2)).collect
val egonet_circles = egonet_edges.toList.map(x => get_circles(x))
println("UserId,Prediction")
val result = egonet_numbers.zip(egonet_circles).map(x => x._1 + "," + x._2)
println(result.mkString("\n"))

//Invoking LabelPropagation
val v = sc.makeRDD(Array((1L,""), (2L,""), (3L,""), (4L,""), (5L,""),
 (6L,""), (7L,""), (8L,"")))
val e = sc.makeRDD(Array(Edge(1L,2L,""), Edge(2L,3L,""), Edge(3L,4L,""),
 Edge(4L,1L,""), Edge(1L,3L,""), Edge(2L,4L,""), Edge(4L,5L,""),
 Edge(5L,6L,""), Edge(6L,7L,""), Edge(7L,8L,""), Edge(8L,5L,""),
 Edge(5L,7L,""), Edge(6L,8L,"")))
lib.LabelPropagation.run(Graph(v,e),5).vertices.collect.
 sortWith(_._1<_._1)
