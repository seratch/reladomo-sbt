import java.nio.file.{Files, Paths}

import Dependencies._

//addCommandAlias("make-idea", ";update-classifiers; update-sbt-classifiers; gen-idea sbt-classifiers")

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
      "org.mariadb.jdbc" % "mariadb-java-client" % "1.5.9"
    )
//    hello := { println("Hello") },
//    scalacOptions := {
//      val out = streams.value
//      val log = out.log
//      log.info("123")
//      val ur = update.value
//      log.info("456")
//      ur.allConfigurations.take(3)
//    }
//    scalacOptions := List("-encoding", "utf8", "-Xfatal-warnings", "-deprecation", "-unchecked"),
//    scalacOptions := {
//      val old = scalacOptions.value
//      scalaBinaryVersion.value match {
//        case "2.12" => old
//        case _ => old filterNot Set("-Xfatal-warnings", "-deprecation").apply
//      }
//    }
  )

lazy val srcGenTask = taskKey[Unit]("Source generate task.")

antSettings
addAntProperties()
addAntTasks("generate-reladomo-classes")

compile := (compile in Compile).dependsOn(antTaskKey("generate-reladomo-classes")).value
//Files.walk()
//todo: hard code files first
unmanagedSourceDirectories in Compile += baseDirectory.value / "generated-src"
