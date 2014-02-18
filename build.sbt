name := "distributedwork"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "com.typesafe.akka" %% "akka-remote" % "2.2.3"
)     

play.Project.playScalaSettings
