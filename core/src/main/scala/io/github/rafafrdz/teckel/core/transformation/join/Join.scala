package io.github.rafafrdz.teckel.core.transformation.join

import io.estatico.newtype.macros.newtype
import io.github.rafafrdz.teckel.core.Operation.Transformation
import io.github.rafafrdz.teckel.core.Source

case class Join(joinType: JoinType, relation: JoinRelation) extends Transformation
object Join {
  case class JoinRelation(left: Source, right: Source, on: List[RelationField])
  case class RelationField(left: Column, right: Column)
  @newtype
  case class Column(name: String)
}
