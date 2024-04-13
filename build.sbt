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
        estatico.newtype,
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
        cats.core,
        catsEffect.core,
        circe.generic,
        circe.yaml,
        log4cats.core,
        log4cats.slf4j,
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
        cats.core,
        log4cats.core,
        log4cats.slf4j,
        catsEffect.core,
        scalaTest.core,
        scalaTest.flatspec
      )
    )
