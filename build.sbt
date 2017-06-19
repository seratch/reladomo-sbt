import java.nio.file.{Files, Paths}
import Dependencies._

lazy val hello = taskKey[Unit]("Hello task")

resolvers += "Typesafe Repositiory" at "https://repo.typesafe.com/typesafe/releases/"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.2",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    libraryDependencies ++= Seq(
      "com.github.pathikrit" %% "better-files" % "3.0.0",
      "com.goldmansachs.reladomo" % "reladomo" % "16.3.0",
      "org.mariadb.jdbc" % "mariadb-java-client" % "1.5.9",
      "org.scalatest" %% "scalatest" % "3.0.3" % "test"
    )
)

antSettings
addAntProperties()
addAntTasks("generate-reladomo-common")
addAntTasks("generate-reladomo-classes")

unmanagedSourceDirectories in Compile += target.value / "generated-src"

lazy val generateReladomoCommon = antTaskKey("generate-reladomo-common")
lazy val generateReladomoClasses = antTaskKey("generate-reladomo-classes")

// refs: https://github.com/sbt/sbt-scalariform/blob/v1.6.0/src/main/scala/com/typesafe/sbt/SbtScalariform.scala#L74-L75
// sbt compile
compileInputs in (Compile, compile) :=
  ((compileInputs in (Compile, compile)).dependsOn(
    (generateReladomoCommon in (Compile, compile)),
    (generateReladomoClasses in (Compile, compile))
  )).value

// sbt test
compileInputs in (Test, compile) :=
  ((compileInputs in (Test, compile)).dependsOn(
    (generateReladomoClasses in (Test, compile)),
    (generateReladomoCommon in (Test, compile))
  )).value
