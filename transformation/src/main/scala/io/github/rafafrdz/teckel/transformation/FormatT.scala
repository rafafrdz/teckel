package io.github.rafafrdz.teckel.transformation

import io.github.rafafrdz.teckel.core.Format

object FormatT {
  def from(format: String): Option[Format] =
    format.toLowerCase() match {
      case "csv"     => Option(Format.CSV)
      case "parquet" => Option(Format.Parquet)
      case "json"    => Option(Format.Json)
      case "text"    => Option(Format.Text)
      case _         => None
    }
}
