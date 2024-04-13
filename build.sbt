import Dependencies.*
import sbt.*

lazy val teckel: Project =
  project
    .in(file("."))
    .disablePlugins(AssemblyPlugin)
    .aggregate(core, yaml, transformation)
    .settings(
      name := "teckel"
    )

lazy val core: Project =
  (project in file("core"))
    .settings(
      name           := "teckel-core",
      publish / skip := false,
      libraryDependencies ++= Seq(
        spark.core,
        spark.sql,
        scalaTest.core,
        scalaTest.flatspec
      )
    )

lazy val yaml: Project =
  (project in file("yaml"))
    .settings(
      name           := "teckel-yaml",
      publish / skip := false,
      libraryDependencies ++= Seq(
        circe.generic,
        circe.yaml,
        tofu.core,
        tofu.circe,
        scalaTest.core,
        scalaTest.flatspec
      )
    )

lazy val transformation: Project =
  (project in file("transformation"))
    .dependsOn(core, yaml)
    .settings(
      name           := "teckel-transformation",
      publish / skip := false,
      libraryDependencies ++= Seq(
        scalaTest.core,
        scalaTest.flatspec
      )
    )
