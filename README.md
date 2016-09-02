# Apache Spark & GraphX [![Awesome](https://cdn.rawgit.com/sindresorhus/awesome/d7305f38d29fed78fa85652e3a63e154dd8e8829/media/badge.svg)](https://github.com/sindresorhus/awesome)

[![Build Status](https://img.shields.io/travis/KunalKapadia/express-mongoose-es6-rest-api/master.svg?style=flat-square)](https://travis-ci.org/KunalKapadia/express-mongoose-es6-rest-api)

# [![graphx](http://spark.apache.org/docs/latest/img/graphx_logo.png)](http://spark.apache.org/graphx/)
# [![cloudera](http://imageshack.com/a/img923/5526/xS3REN.png)](https://www.cloudera.com/)
## Tabla de contenido

* [Resumen](#resumen)
* [Archivos que contiene el repositorio](#archivos-que-contiene-el-repositorio)
* [Herramientas](#herramientas)
* [Clúster](#clúster)
* [Implementaciones](#implementaciones)
* [Datasets](#datasets)
* [Inicialización](#inicialización)
	* [Instalar sbt](#instalar-sbt)
	* [Servicios Cloudera Manager Server](#servicios-cloudera-manager-server)
	* [Clonar repositorio](#clonar-repositorio)
	* [Cargar datos en HDFS](#cargar-datos-en-hdfs)
	* [Ejecutar código](#ejecutar-código)
	* [Resultados](#resultados)
* [Creador](#creador)


# Resumen

En el siguiente repositorio se encuentran una serie de implementaciones utilizando **GraphX** que permiten el manejo y análisis de grafos de gran escala. Estas implementaciones permiten realizar análisis de redes mediante el algoritmo de **PageRank**, **ShortestPaths**, **Triangle Count**, outDegree, **inDegree** y **ConnectedComponents**. Para el desarrollo de este proyecto se utilizó la distribución Cloudera para un **clúster Hadoop** multi nodo, **Apache Spark**, **GraphX**, **Scala** y la herramienta de visualización **Gephi**.

# Archivos que contiene el repositorio

El siguiente repositorio contiene las siguientes carpetas con los diversos archivos:

```
Apache-Spark-GraphX/
├── data/
│   ├── Facebook.txt
│   └── egonets/
│   	├── 0.egonet 
│   	├── ...
│   	└── 27022.egonet
├── doc/
│   ├── Tesis.ppt
│   └── Tesis.pdf
├── gephi/
│   └── grafo.gexf
├── lib/
│   ├── breeze_2.10-0.12.jar 
│   ├── breeze-viz_2.10-0.12.jar
│   ├── gs-core-1.2.jar
│   ├── gs-ui-1.2.jar
│   ├── jcommon-1.0.16.jar
│   ├── jfreechart-1.0.13.jar 
│   └── pherd-1.0.jar
├── output/
│   └── ...
├── target/
│   └── scala-2.10/
│    	└── grafos-de-gran-escala_2.10-1.0.jar
├── src/
│   └── SocialMedia.scala
└── style/
    ├── stylesheet
    └── stylesheet-simple
```

# Herramientas

En el presente proyecto se utilizó diferentes herramientas con respectivas versiones:

| Herramienta                         |         Versión                                                                                                                                                                                                                                         |
|----------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Distribución Apache Hadoop.            | [Cloudera Manager 5.8.1](http://www.cloudera.com/downloads/manager/5-8-1.html).  |
| Gephi.                	 	 | [Gephi 0.9.1](https://gephi.org/users/download/). |
| Apache Spark.                	 	 | [Apache Spark 2.0.0](http://spark.apache.org/). |
| GraphX.                	 	 | [GraphX 2.10](http://spark.apache.org/graphx/). |

# Clúster
Para el desarrollo de la solución se instaló un clúster Hadoop multi nodo (nodos físicos) utilizando la distribución Cloudera mediante 4 equipos conectados por LAN con las siguientes características:


| Componente                         |         Especificación                                                                                                                                                                                                                                         |
|----------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Sistema Operativo.                	 | CentOS 7.|
| Memoria.                    		 | 3,7 GiB.  |
| Procesador.                	 	 | Intel Core i5-3470 CPU @ 3.20GHz x 4. |
| Disco.                	 	 | 980,1 GB. |
| OS Type.                	 	 | 64-bit. |


# Implementaciones
En este proyecto se desarrollaron las siguientes implementaciones:

| Implementación                        |            Resumen                                                                                                                                                                                                                                          |
|----------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| *InDegree*              	 | Calcula los grados de entrada de todos los nodos.  |
| *OutDegree*               	 | Calcula los grados de salida de todos los nodos. |
| *PageRank*               	         | Mide la influencia de los vértices de un grafo. |
| *TriangleCount*               	 | Mide que tan conectado se encuentra un grafo. |
| *ShortestPaths*                	 |  Calcula todas las distancias entre todos los nodos. |
| *ConnectedComponents*                	 |  Calcula las componentes de una egonet.|
| *Gephi*                	 | Genera un .gexf que puede ser utilizado en Gephi. |

# Datasets
Los conjuntos de datos utilizados fueron los siguientes:

| Dataset                        		 |            Descargar                            									  | Nodos | Aristas  |
|----------------------------------------|------------------------------------------------------------------------------------|-------|----------|
| Facebook.txt               	 | [Facebook](https://snap.stanford.edu/data/facebook.tar.gz).									  | 88234 | 4039     |
| Friendster.txt                			 | [Friendster](https://snap.stanford.edu/data/bigdata/communities/com-friendster.ungraph.txt.gz). | 65608366 | 1806067135   |
| egonets                			 | [Egonets](https://snap.stanford.edu/data/facebook_combined.txt.gz). | - | -   |

# Inicialización
### Instalar sbt
```sh
wget http://dl.bintray.com/sbt/rpm/sbt-0.13.5.rpm
sudo yum localinstall sbt-0.13.5.rpm
sbt -version
```

### Servicios Cloudera Manager Server

Iniciar servicios del Cloudera Manager Server:
```sh
sudo service cloudera-scm-server start
```
Detener servicios del Cloudera Manager Server:
```sh
 sudo service cloudera-scm-server stop
```
Reiniciar servicios del Cloudera Manager Server:
```sh
sudo service cloudera-scm-server restart 
```
### Clonar repositorio
Clonar el siguiente repositorio:
```sh
git clone https://github.com/ericbellet/Apache-Spark-GraphX
cd Apache-Spark-GraphX
sbt package
```

### Cargar datos en HDFS
```sh

hadoop fs -mkdir input
cd data
hadoop fs -put Facebook.txt input
hadoop fs -put egonets

cd Downloads
hadoop fs -mkdir friendster
hadoop fs -put Friendster.txt friendster

```
### Ejecutar código
El código se ejecutará mediante Apache Spark utilizando datos de HDFS y almacenando resultados en HDFS.
Ejecutar código en modo local:
```sh
spark-submit --class com.cloudera.sparksocialmedia.SparkSocialMedia --master local target/scala-2.10/grafos-de-gran-escala_2.10-1.0.jar input egonets Descripcion ShortestPaths TriangleCount PageRank ConnectedComponents
```
Ejecutar código en modo YARN CLIENT:
```sh
spark-submit --class com.cloudera.sparksocialmedia.SparkSocialMedia --master yarn --deploy-mode client target/scala-2.10/grafos-de-gran-escala_2.10-1.0.jar input egonets Descripcion ShortestPaths TriangleCount PageRank ConnectedComponents

```
Ejecutar código en modo YARN CLUSTER:
```sh
spark-submit --class com.cloudera.sparksocialmedia.SparkSocialMedia --master yarn --deploy-mode cluster  --num-executors 4 --driver-memory 2g --executor-memory 2g --executor-cores 4 --queue default target/scala-2.10/grafos-de-gran-escala_2.10-1.0.jar input egonets Descripcion ShortestPaths TriangleCount PageRank ConnectedComponents


```

### En el caso de querer volver a ejecutar el comando anterior es necesario borrar los siguientes archivos distribuidos:
```sh
hdfs dfs -rmr Descripcion
hdfs dfs -rmr ShortestPaths
hdfs dfs -rmr TriangleCount
hdfs dfs -rmr PageRank
hdfs dfs -rmr ConnectedComponents

hdfs dfs -ls
```

### Resultados
Para obtener los resultados:

```sh
hadoop fs -cat Descripcion/*
hadoop fs -get Descripcion ./output

hadoop fs -cat ShortestPaths/*
hadoop fs -get ShortestPaths ./output

hadoop fs -cat TriangleCount/*
hadoop fs -get TriangleCount ./output

hadoop fs -cat PageRank/*
hadoop fs -get PageRank ./output

hadoop fs -cat ConnectedComponents/*
hadoop fs -get ConnectedComponents ./output

cd Apache-Spark-GraphX/gephi
```

# Creador

**Eric Bellet**

* <https://ve.linkedin.com/in/belleteric>
