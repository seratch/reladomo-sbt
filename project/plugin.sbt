addSbtPlugin("de.johoop" % "ant4sbt" % "1.1.2")

libraryDependencies ++= Seq(
  "com.goldmansachs.reladomo" % "reladomogen" % "16.1.3",
  "com.goldmansachs.reladomo" % "reladomo-gen-util" % "16.1.3"
)

retrieveManaged := true