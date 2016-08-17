// Project info
name := "Social Media Facebook"

version := "0.1"

scalaVersion := "2.10.3"


// https://mvnrepository.com/artifact/org.apache.spark/spark-graphx_2.10
libraryDependencies += "org.apache.spark" % "spark-graphx_2.10" % "2.0.0"

// https://mvnrepository.com/artifact/org.graphstream/gs-core
libraryDependencies += "org.graphstream" % "gs-core" % "1.2"


// https://mvnrepository.com/artifact/log4j/log4j
libraryDependencies += "log4j" % "log4j" % "1.2.17"


// RLCSA comes from the local Maven repo. See
// <http://stackoverflow.com/a/10778151/402891>
resolvers += "Local Maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository"

resolvers += "Sonatype" at "http://oss.sonatype.org/content/repositories/snapshots/"

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

resolvers += "Hadoop-BAM" at "http://hadoop-bam.sourceforge.net/maven/"

resolvers += "BioJava repository" at "http://www.biojava.org/download/maven/"
