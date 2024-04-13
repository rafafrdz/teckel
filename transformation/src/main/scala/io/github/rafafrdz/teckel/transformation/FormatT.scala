package io.github.rafafrdz.teckel.transformation

import cats.ApplicativeThrow
import cats.implicits.{catsSyntaxApplicativeErrorId, catsSyntaxApplicativeId}
import io.github.rafafrdz.teckel.core.Format

object FormatT {
  def format[F[_]: ApplicativeThrow]: CoreFrom[F, String, Format] =
    (value: String) => fromRaw(value) match {
      case Some(f) => f.pure[F]
      case None => new Exception(s"Invalid format: $value").raiseError[F, Format]
    }

  def fromRaw(format: String): Option[Format] =
    format.toLowerCase() match {
      case "csv"     => Option(Format.CSV)
      case "parquet" => Option(Format.Parquet)
      case "json"    => Option(Format.Json)
      case "text"    => Option(Format.Text)
      case _         => None
    }
}
