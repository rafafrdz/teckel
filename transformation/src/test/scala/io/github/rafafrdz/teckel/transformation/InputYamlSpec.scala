package io.github.rafafrdz.teckel.transformation

import cats.effect._
import io.github.rafafrdz.teckel.core.Operation.Input
import io.github.rafafrdz.teckel.core.{Format, Operation, Source}
import io.github.rafafrdz.teckel.serializer.model._
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers._

class InputYamlSpec extends AnyFlatSpecLike with Matchers with CommonTest {

  object Resources {
    val input: InputYaml =
      InputYaml("table1", "csv", Map("header" -> "true", "sep" -> "|"), "/path/path1")

  }

  "InputYaml" should "be mapped to an Input Source" in {
    SourceT.input[IO].from(Resources.input).unsafeRunSync() shouldBe Source.From(
      "table1",
      Input(
        format = Format.CSV,
        options = List(Operation.Option("header", "true"), Operation.Option("sep", "|")),
        path = "/path/path1"
      ),
      "",
      ""
    )
  }

}
