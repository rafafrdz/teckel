import Dependency.ProjectOps

lazy val root =
  (project in file("."))
    .disablePlugins(BuildPlugin, AssemblyPlugin, HeaderPlugin)
    .settings(
      name           := "teckel",
      publish / skip := true
    )
    .aggregate(
      model,
      semantic,
      serializer,
      api,
      example
    )

/**
 * Serializers --> Model --> Semantic
 *      \-----> API <--------- /
 */

lazy val model =
  (project in file("./model"))
    .settings(
      name := "teckel-model",
      libraryDependencies ++= Dependency.model,
      publish / skip := false
    )

lazy val semantic =
  (project in file("./semantic"))
    .dependsOn(model)
    .settings(
      name := "teckel-semantic",
      libraryDependencies ++= Dependency.semantic,
      publish / skip := false
    )
    .withKindProjector

/** Serializer */
lazy val serializer =
  (project in file("./serializer"))
    .dependsOn(model)
    .settings(
      name           := "teckel-serializer",
      publish / skip := false,
      libraryDependencies ++= Dependency.serializer
    )

lazy val api =
  (project in file("./api"))
    .dependsOn(model, semantic, serializer)
    .settings(
      name           := "teckel-api",
      publish / skip := false,
      libraryDependencies ++= Dependency.api
    )

lazy val example =
  (project in file("./example"))
    .dependsOn(api)
    .settings(
      name := "teckel-example"
    )
