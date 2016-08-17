# Apache Spark & GraphX [![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome)

[![Build Status](https://img.shields.io/travis/KunalKapadia/express-mongoose-es6-rest-api/master.svg?style=flat-square)](https://travis-ci.org/KunalKapadia/express-mongoose-es6-rest-api)

# [![graphx](http://spark.apache.org/docs/latest/img/graphx_logo.png)](http://spark.apache.org/graphx/)
# [![cloudera](http://imageshack.com/a/img923/5526/xS3REN.png)](https://www.cloudera.com/)
## Tabla de contenido

* [Resumen](#resumen)
* [Archivos que contiene el repositorio](#archivos-que-contiene-el-repositorio)
* [Herramientas](#herramientas)
* [Implementaciones](#implementaciones)
* [Datasets](#datasets)
* [Inicialización](#inicialización)
	* [Dijkstra](#dijkstra)
	* [TriangleCount](#triangleCount)
	* [PageRank](#pageRank)
	* [SocialMedia](#socialMedia)
* [Creador](#creador)


# Resumen

En el siguiente repositorio se encuentran una serie de implementaciones utilizando GraphX que permiten el manejo y análisis de grafos de gran escala. Estas implementaciones permiten realizar análisis de redes mediante el algoritmo de PageRank, Shortest Paths, Label Propagation y Connected Components. Para el desarrollo de este proyecto se utilizo un sandbox de **Cloudera**, la distribución Cloudera para un **clúster Hadoop** multi nodo, **Apache Spark**, **GraphX**, **Scala** y las herramientas de visualización **Gephi** y **GraphStream**.

# Archivos que contiene el repositorio

El siguiente repositorio contiene las siguientes carpetas con los diversos archivos:

```
Apache-Spark-GraphX/
├── data/
│   ├── Friendster.txt
│   ├── Facebook.txt
│   └── egonets/
│   	├── 0.egonet 
│   	├── ...
│   	└── 27022.egonet
├── doc/
│   ├── Tesis.ppt
│   └── Tesis.pdf
├── gephi/
│   └── facebookGraph.gexf
├── lib/
│   ├── breeze_2.10-0.12.jar 
│   ├── breeze-viz_2.10-0.12.jar
│   ├── gs-core-1.2.jar
│   ├── gs-ui-1.2.jar
│   ├── jcommon-1.0.16.jar
│   ├── jfreechart-1.0.13.jar 
│   └── pherd-1.0.jar
├── src/
│   ├── Dijkstra.scala
│   ├── TriangleCount.scala
│   ├── PageRank.scala
│   └── SocialMedia.scala
└── style/
    ├── stylesheet
    └── stylesheet-simple
```

# Herramientas

En el presente proyecto se utilizó diferentes herramientas con respectivas versiones:

| Herramienta                         |            Versión                                                                                                                                                                                                                                         |
|----------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Sistema Operativo.                	 | CentOS 6.7.|
| Distribución Apache Hadoop.            | [Cloudera Quickstarts VM](https://www.cloudera.com/downloads.html).  |
| Gephi.                	 			 | [Gephi 0.9.1](https://gephi.org/users/download/). |
| Breeze-Viz.                	 		 | [Breeze-Viz 0.12](http://mvnrepository.com/artifact/org.scalanlp/breeze-viz_2.10/0.12). |
| Apache Zeppelin.                	     | [Zeppelin 0.5.6](https://zeppelin.apache.org/download.html). |

# Implementaciones
En este proyecto se desarrollaron las siguientes implementaciones:

| Implementación                        |            Resumen                                                                                                                                                                                                                                          |
|----------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| *Dijkstra*.scala                	 | Este algoritmo busca el camino más corto desde un vértice a otro utilizando aristas que poseen peso o ponderación.|
| *TriangleCount*.scala                | Mide la conectividad de un grafo o un subgrafo. |
| *PageRank*.scala                	 | Mide la influencia de los vértices de un grafo. |
| *SocialMedia*.scala                	 | Contiene diversas funciones que permiten realizar análisis de redes sociales. Contiene ShortestPaths, ConnectedComponents y LabelPropagation. |

# Datasets
Los conjuntos de datos utilizados fueron los siguientes:

| Dataset                        		 |            Resumen                            									  | Nodos | Aristas  |
|----------------------------------------|------------------------------------------------------------------------------------|-------|----------|
| facebook_combined.txt               	 | Representa un grafo de la red social Facebook.									  | 88234 | 4039     |
| Slashdot.txt                			 | Representa una red de enlaces de amigos y enemigos entre los usuarios de Slashdot. | 77360 | 905468   |


# Inicialización
Clonar el siguiente repositorio:
```sh
git clone https://github.com/ericbellet/Apache-Spark-GraphX
cd Apache-Spark-GraphX
```
### Instalar sbt:
```sh
wget http://dl.bintray.com/sbt/rpm/sbt-0.13.5.rpm
sudo yum localinstall sbt-0.13.5.rpm
sbt -version
sbt package
```

### Cargar datos en HDFS:
```sh
hadoop fs -mkdir input
cd data
hadoop fs -put facebook_combined.txt input
cd ..

hadoop fs -mkdir egonets
cd data
hadoop fs -put egonets/239.egonet egonets
```
### Ejecutar código en GraphX en Apache Spark, Yarn mode cluster (multi node) utilizando datos de HDFS y almacenando resultados en HDFS:
```sh
spark-submit --class com.cloudera.sparksocialmedia.SparkSocialMedia --master yarn --deploy-mode cluster target/scala-2.10/social-media-facebook_2.10-0.1.jar input egonets numAristas numVertices outDegrees inDegrees  ShortestPaths LabelPropagation PageRank connectedComponents
```

### En el caso de querer volver a ejecutar el comando anterior es necesario borrar los siguientes archivos distribuidos:
```sh
hdfs dfs -rmr inDegrees
hdfs dfs -rmr outDegrees
hdfs dfs -rmr numAristas
hdfs dfs -rmr numVertices
hdfs dfs -rmr ShortestPaths
hdfs dfs -rmr LabelPropagation
hdfs dfs -rmr PageRank
hdfs dfs -rmr connectedComponents
hdfs dfs -rmr gephi

hdfs dfs -ls
```

### Para obtener los resultados:

```sh
hadoop fs -cat numVertices/*
hadoop fs -cat numAristas/*
hadoop fs -cat inDegrees/*
hadoop fs -cat outDegrees/*
hadoop fs -cat ShortestPaths/*
hadoop fs -cat LabelPropagation/*
hadoop fs -cat PageRank/*
hadoop fs -cat connectedComponents/*

```


### Almacenar grafos en HDFS
```sh
cd data
hdfs dfs -put "data"
hdfs dfs -ls
cd ..
```

###
```sh
spark-submit --class com.cloudera.sparksocialmedia.SparkSocialMedia --master local target/scala-2.10/social-media-facebook_2.10-0.1.jar
```

### TriangleCount

Iniciar la consola de Spark:
```sh
spark-shell  -i TriangleCount.scala

```

### PageRank

Iniciar la consola de Spark:
```sh
spark-shell -i PageRank.scala

```

### SocialMedia

Iniciar la consola de Spark:
```sh
spark-shell --jars lib/gs-core-1.2.jar,lib/gs-ui-1.2.jar,lib/jcommon-1.0.16.jar,lib/jfreechart-1.0.13.jar,lib/breeze_2.10-0.12.jar,lib/breeze-viz_2.10-0.12.jar,lib/pherd-1.0.jar -i SocialMedia.scala

```

### Gephi
Iniciar terminal:
```sh
./bin/gephi.sh

```

### Apache Zeppelin con D3.js
Iniciar terminal:
```sh
tar -xzvf zeppelin-0.5.6-incubating-bin-all.tgz./zeppelin-0.5.6-incubating-bin-all/bin/zeppelin-daemon.sh start
xdg-open http://localhost:8080

```

### Dijkstra

Iniciar la consola de Spark:
```sh
spark-shell -i Dijkstra.scala

```
# Creador

**Eric Bellet**

* <https://ve.linkedin.com/in/belleteric>
