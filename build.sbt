name := "SprayTestKitDSL"

version := "1.0"

scalaVersion := "2.12.4"

val http4sVersion = "0.17.5"
val circeVersion = "0.8.0"

libraryDependencies ++= Seq(
   "org.http4s" %% "http4s-core" % http4sVersion,
   "org.http4s" %% "http4s-blaze-client" % http4sVersion,
   "io.circe" %% "circe-core" % circeVersion,
   "io.circe" %% "circe-generic" % circeVersion,
   "io.circe" %% "circe-parser" % circeVersion
)