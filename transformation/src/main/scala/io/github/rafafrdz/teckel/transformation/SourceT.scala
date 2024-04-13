package io.github.rafafrdz.teckel.transformation

import io.github.rafafrdz.teckel.core.Operation.{Input, Output}
import io.github.rafafrdz.teckel.core.Source._
import io.github.rafafrdz.teckel.core._
import io.github.rafafrdz.teckel.serializer.model._

object SourceT {

  def from(source: InputYaml): Source = {
    val sourceOpt: Option[From] =
      for {
        format <- FormatT.from(source.format)
        options = source.options.map { case (k, v) => Operation.Option(k, v) }.toList
      } yield Source.From(
        source.name,
        Input(format = format, options = options, path = source.path),
        "",
        ""
      )

    sourceOpt.getOrElse(Unknown(source.name))

  }

  def from(source: OutputYaml): Source = {
    val sourceOpt: Option[From] =
      for {
        format <- FormatT.from(source.format)
        options = source.options.map { case (k, v) => Operation.Option(k, v) }.toList
      } yield Source.From(
        source.name,
        Output(format = format, options = options, path = source.path),
        "",
        ""
      )

    sourceOpt.getOrElse(Unknown(source.name))
  }

}
