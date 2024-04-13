import sbt.*
object Dependencies {

  object cats {
    lazy val core: ModuleID = "org.typelevel" %% "cats-core" % Version.Cats

  }

  object catsEffect {
    lazy val core: ModuleID = "org.typelevel" %% "cats-effect" % Version.CatsEffect
  }

  object estatico {
    lazy val newtype: ModuleID = "io.estatico" %% "newtype" % Version.Estatico
  }

  object log4cats {
    lazy val core: ModuleID  = "org.typelevel" %% "log4cats-core"  % Version.Log4Cats
    lazy val slf4j: ModuleID = "org.typelevel" %% "log4cats-slf4j" % Version.Log4Cats
  }

  object spark {
    lazy val core = "org.apache.spark" %% "spark-core" % Version.Spark
    lazy val sql  = "org.apache.spark" %% "spark-sql"  % Version.Spark
  }

  object circe {
    lazy val yaml    = "io.circe" %% "circe-yaml"    % Version.Circe
    lazy val generic = "io.circe" %% "circe-generic" % Version.Circe
  }

  object tofu {
    lazy val core  = "tf.tofu" %% "derevo-core"           % Version.Tofu
    lazy val circe = "tf.tofu" %% "derevo-circe-magnolia" % Version.Tofu
  }

  object scalaTest {
    lazy val core     = "org.scalatest" %% "scalatest"          % Version.ScalaTest % Test
    lazy val flatspec = "org.scalatest" %% "scalatest-flatspec" % Version.ScalaTest % Test
  }

}
