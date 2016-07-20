# Apache Spark & GraphX [![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome)

[![Build Status](https://img.shields.io/travis/KunalKapadia/express-mongoose-es6-rest-api/master.svg?style=flat-square)](https://travis-ci.org/KunalKapadia/express-mongoose-es6-rest-api)

# [![graphx](http://spark.apache.org/docs/latest/img/graphx_logo.png)](http://spark.apache.org/graphx/)
# [![cloudera](http://imageshack.com/a/img923/5526/xS3REN.png)](https://www.cloudera.com/)
## Tabla de contenido

* [Resumen](#resumen)
* [Archivos que contiene el repositorio](#archivos-que-contiene-el-repositorio)
* [Características](#características)
* [Implementaciones](#mplementaciones)
* [Inicialización](#inicialización)
	* [Dijkstra](#dijkstra)
	* [TriangleCount](#triangleCount)
	* [PageRank](#pageRank)
	* [SocialMedia](#socialMedia)
* [Creador](#creador)


### Resumen

El siguiente repositorio...

### Archivos que contiene el repositorio

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

### Características

| Características                        |                                                                                                                                                                                                                                                      |
|----------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Sistema Operativo.                	 | CentOS 6.7.|
| Distribución Apache Hadoop.            | [Cloudera Quickstarts VM](https://www.cloudera.com/downloads.html).  |

### Implementaciones

| Implementación                        |            Resumen                                                                                                                                                                                                                                          |
|----------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Dijkstra.scala                	 | Este algoritmo busca el camino más corto desde un vértice a otro utilizando aristas que poseen peso o ponderación.|
| TriangleCount.scala                | Mide la conectividad de un grafo o un subgrafo. |
| PageRank.scala                	 | Mide la influencia de los vértices de un grafo. |
| SocialMedia.scala                	 | Contiene diversas funciones que permiten realizar análisis de redes sociales. Contiene ShortestPaths, ConnectedComponents y LabelPropagation. |

### Inicialización

## Dijkstra
Clonar el siguiente repositorio:
```sh
git clone https://github.com/ericbellet/Apache-Spark-GraphX
cd Apache-Spark-GraphX
```
Inicia la consola de Spark:
```sh
spark-shell --jars lib/gs-core-1.2.jar,lib/gs-ui-1.2.jar,lib/jcommon-1.0.16.jar,lib/jfreechart-1.0.13.jar,lib/breeze_2.10-0.12.jar,lib/breeze-viz_2.10-0.12.jar,lib/pherd-1.0.jar -i Facebook.scala

```

## TriangleCount
Clonar el siguiente repositorio:
```sh
git clone https://github.com/ericbellet/Apache-Spark-GraphX
cd Apache-Spark-GraphX
```
Inicia la consola de Spark:
```sh
spark-shell --jars lib/gs-core-1.2.jar,lib/gs-ui-1.2.jar,lib/jcommon-1.0.16.jar,lib/jfreechart-1.0.13.jar,lib/breeze_2.10-0.12.jar,lib/breeze-viz_2.10-0.12.jar,lib/pherd-1.0.jar -i Facebook.scala

```

## PageRank
Clonar el siguiente repositorio:
```sh
git clone https://github.com/ericbellet/Apache-Spark-GraphX
cd Apache-Spark-GraphX
```
Inicia la consola de Spark:
```sh
spark-shell --jars lib/gs-core-1.2.jar,lib/gs-ui-1.2.jar,lib/jcommon-1.0.16.jar,lib/jfreechart-1.0.13.jar,lib/breeze_2.10-0.12.jar,lib/breeze-viz_2.10-0.12.jar,lib/pherd-1.0.jar -i Facebook.scala

```

## SocialMedia
Clonar el siguiente repositorio:
```sh
git clone https://github.com/ericbellet/Apache-Spark-GraphX
cd Apache-Spark-GraphX
```
Inicia la consola de Spark:
```sh
spark-shell --jars lib/gs-core-1.2.jar,lib/gs-ui-1.2.jar,lib/jcommon-1.0.16.jar,lib/jfreechart-1.0.13.jar,lib/breeze_2.10-0.12.jar,lib/breeze-viz_2.10-0.12.jar,lib/pherd-1.0.jar -i Facebook.scala

```

### Creador

**Eric Bellet**

* <https://ve.linkedin.com/in/belleteric>
