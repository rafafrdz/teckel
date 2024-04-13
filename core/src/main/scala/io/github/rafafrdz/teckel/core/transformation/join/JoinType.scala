package io.github.rafafrdz.teckel.core.transformation.join

sealed trait JoinType

/** https://spark.apache.org/docs/latest/sql-ref-syntax-qry-select-join.html */
object JoinType {
  case object Inner      extends JoinType
  case object Left       extends JoinType
  case object LeftOuter  extends JoinType
  case object LeftSemi   extends JoinType
  case object LeftAnti   extends JoinType
  case object Right      extends JoinType
  case object RightOuter extends JoinType
  case object Cross      extends JoinType
  case object Semi       extends JoinType
  case object Full       extends JoinType
  case object Anti       extends JoinType
  case object FullOuter  extends JoinType

}
