//TriangleCount
//Cargo el dataset
val g = GraphLoader.edgeListFile(sc, "Slashdot.txt").cache

//Para poder utilizar este algoritmo GraphX inpuso una serie de condiciones.
//El grafo debe ser particionado (cualquier metodo)

//Realizo (0 to 6) un for imperativo
//Aplico triangleCount
val g2 = Graph(g.vertices, g.edges.map(e => if (e.srcId < e.dstId) e else new Edge(e.dstId, e.srcId, e.attr))).partitionBy(PartitionStrategy.RandomVertexCut)(0 to 6).map(i => g2.subgraph(vpred = (vid,_) => vid >= i*10000 && vid < (i+1)*10000).triangleCount.vertices.map(_._2).reduce(_ + _))