package io.github.rafafrdz.teckel.transformation

import cats.effect._
import io.github.rafafrdz.teckel.core.Operation.{Input, Output}
import io.github.rafafrdz.teckel.core.{Format, Operation, Source}
import io.github.rafafrdz.teckel.serializer.model._
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers._

class OutputYamlSpec extends AnyFlatSpecLike with Matchers with CommonTest {

  object Resources {
    val output: OutputYaml =
      OutputYaml("result", "csv", Map("header" -> "true", "sep" -> "|"), "/path/output")

  }

  "OutputYaml" should "be mapped to an Output Source" in {
    SourceT.output[IO].from(Resources.output).unsafeRunSync() shouldBe Source.From(
      "result",
      Output(
        format = Format.CSV,
        options = List(Operation.Option("header", "true"), Operation.Option("sep", "|")),
        path = "/path/output"
      ),
      "",
      ""
    )
  }
  
}
