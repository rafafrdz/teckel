package io.github.rafafrdz.teckel.transformation

import cats.ApplicativeThrow
import cats.implicits.catsSyntaxApplicativeId
import io.github.rafafrdz.teckel.core.Operation.{Input, Output}
import io.github.rafafrdz.teckel.core.Source._
import io.github.rafafrdz.teckel.core._
import io.github.rafafrdz.teckel.serializer.model._
import org.typelevel.log4cats.Logger

object SourceT {

  def input[F[_]: ApplicativeThrow: Logger]: CoreFrom[F, InputYaml, Source] =
    (value: InputYaml) => from(value).pure[F]

  def output[F[_]: ApplicativeThrow: Logger]: CoreFrom[F, OutputYaml, Source] =
    (value: OutputYaml) => from(value).pure[F]

  private def from[F[_]: Logger](source: InputYaml): Source = {
    val sourceOpt: Option[From] =
      for {
        format <- FormatT.fromRaw(source.format)
        options = source.options.map { case (k, v) => Operation.Option(k, v) }.toList
      } yield Source.From(
        source.name,
        Input(format = format, options = options, path = source.path),
        "",
        ""
      )

    sourceOpt match {
      case Some(value) => value
      case None =>
        implicitly[Logger[F]].error(s"Unknown source: $source")
        Unknown(source.name)
    }

  }

  private def from[F[_]: Logger](source: OutputYaml): Source = {
    val sourceOpt: Option[From] =
      for {
        format <- FormatT.fromRaw(source.format)
        options = source.options.map { case (k, v) => Operation.Option(k, v) }.toList
      } yield Source.From(
        source.name,
        Output(format = format, options = options, path = source.path),
        "",
        ""
      )

    sourceOpt match {
      case Some(value) => value
      case None =>
        implicitly[Logger[F]].error(s"Unknown source: $source")
        Unknown(source.name)
    }
  }

}
