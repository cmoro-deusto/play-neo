name := """neo"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"
//scalaVersion := "2.10.4"

resolvers ++= Seq(
  "tuxburner.github.io" at "http://tuxburner.github.io/repo",
  "Neo4j" at "http://m2.neo4j.org/content/repositories/releases/",
  "Spring milestones" at "http://repo.spring.io/milestone",
  Resolver.mavenLocal
)


libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs
)


libraryDependencies += "com.github.tuxBurner" %% "play-neo4jplugin" % "1.4.5-SNAPSHOT"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
