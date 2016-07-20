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

El siguiente repositorio se encuentran una serie de implementaciones utilizando GraphX que permiten el manejo y análisis de grafos de gran escala. Estas implementaciones permiten realizar análisis de redes sociales, aplicar PageRank, Triangle Count y Dijkstra sobre distintos datasets. Para el desarrollo de este proyecto se utilizo un sandbox de **Cloudera**, **Apache Spark**, **GraphX**, **Scala** y las herramientas de visualización **Gephi**, **Breeze-Viz**, **Apache Zeppelin** y **D3.js**.
Este proyecto contiene diversas operaciones y algoritmos que provee GraphX que se pueden aplicar en distintas redes. Tambien provee una serie de algoritmos implementados en el lenguaje **scala** y como se pueden usar distintas herramientas de visualización sobre grafos de gran escala.

# Archivos que contiene el repositorio

El siguiente repositorio contiene las siguientes carpetas con los diversos archivos:

```
Apache-Spark-GraphX/
├── data/
│   ├── Slashdot.txt
│   ├── continent.csv
│   ├── facebook_combined.txt
│   └── egonets/
│   	├── 0.egonet 
│   	├── ...
│   	└── 27022.egonet
├── doc/
│   └── Comandos.txt
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

| Dataset                        |            Resumen                             | Nodos | Aristas                                                                                                                                                                                                              |
|----------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| facebook_combined.txt               	 | Representa un grafo de la red social Facebook.| 88234| 4039 |
| Slashdot.txt                			| Representa una red de enlaces de amigos y enemigos entre los usuarios de Slashdot. | 77360 | 905468|


# Inicialización
Clonar el siguiente repositorio:
```sh
git clone https://github.com/ericbellet/Apache-Spark-GraphX
cd Apache-Spark-GraphX
```
### Dijkstra

Iniciar la consola de Spark:
```sh
spark-shell -i Dijkstra.scala

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

# Creador

**Eric Bellet**

* <https://ve.linkedin.com/in/belleteric>
