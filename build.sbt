name := "sotm-fe"
organization := "style.tailored"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.amazonaws" % "aws-java-sdk" % "1.11.70"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "style.tailored.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "style.tailored.binders._"
