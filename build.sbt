import java.nio.file.{Files, Paths}

import Dependencies._

lazy val hello = taskKey[Unit]("Hello task")

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    libraryDependencies ++= Seq(
      "com.goldmansachs.reladomo" % "reladomo" % "16.1.3",
      "org.mariadb.jdbc" % "mariadb-java-client" % "1.5.9",
      "org.scalatest" %% "scalatest" % "3.0.1" % "test"
    )
)

antSettings
addAntProperties()
addAntTasks("generate-reladomo-classes")

compile := (compile in Compile).dependsOn(antTaskKey("generate-reladomo-classes")).value
unmanagedSourceDirectories in Compile += target.value / "generated-src"
