package io.github.rafafrdz.teckel.transformation

import cats.MonadThrow
import cats.implicits.{catsSyntaxApplicativeErrorId, catsSyntaxApplicativeId}
import io.github.rafafrdz.teckel.core.Source
import io.github.rafafrdz.teckel.core.Source.Unknown
import io.github.rafafrdz.teckel.core.transformation.join.Join.RelationField
import io.github.rafafrdz.teckel.core.transformation.join.{Join, JoinType}
import io.github.rafafrdz.teckel.serializer.model.TransformationYaml
import io.github.rafafrdz.teckel.serializer.model.TransformationYaml.{JoinSourceYaml, JoinTransformationYaml}
import org.typelevel.log4cats.Logger

object TransformationT {

  def transformation[F[_]: MonadThrow: Logger]: CoreFrom[F, TransformationYaml, Source] =
    (value: TransformationYaml) =>
      from(value) match {
        case Some(f) => f.pure[F]
        case None    => new Exception(s"Invalid transformation: $value").raiseError[F, Source]
      }

  private def from[F[_]: Logger](transformation: TransformationYaml): Option[Source] = {
    transformation match {
      case join: JoinTransformationYaml => Option(from(join))
      case _                            => Option.empty[Source]
    }
  }
  private def from[F[_]: Logger](transformation: JoinTransformationYaml): Source = {
    val joinType: JoinType          = parse[F](transformation.join.joinType)
    val name: String                = transformation.name
    val left: String                = transformation.join.relation.left
    val right: List[JoinSourceYaml] = transformation.join.relation.right
    joinStack(name, joinType, left, right.reverse)
  }

  private def joinStack(
      name: String,
      joinType: JoinType,
      left: String,
      right: List[JoinSourceYaml]
  ): Source = {
    right match {
      case ::(head, Nil) =>
        Source.From(
          name = name,
          operation = Join(
            joinType = joinType,
            relation = Join.JoinRelation(
              left = Unknown(left),
              right = Unknown(head.name),
              on = head.fields.map { case (k, v) =>
                RelationField(Join.Column(k), Join.Column(v))
              }.toList
            )
          ),
          select = "",
          where = ""
        )
      case ::(head, tail) =>
        Source.From(
          name = head.name,
          operation = Join(
            joinType = joinType,
            relation = Join.JoinRelation(
              left = joinStack(name, joinType, left, tail),
              right = Unknown(head.name),
              on = head.fields.map { case (l, r) =>
                RelationField(Join.Column(l), Join.Column(r))
              }.toList
            )
          ),
          select = "",
          where = ""
        )
    }
  }

  private def parse[F[_]: Logger](joinType: String): JoinType = {
    val formatJoinType: String =
      joinType.toLowerCase
        .replace(" ", "")
        .replace("-", "")
        .replace("_", "")

    formatJoinType match {
      case "anti"       => JoinType.Anti
      case "cross"      => JoinType.Cross
      case "full"       => JoinType.Full
      case "fullouter"  => JoinType.FullOuter
      case "inner"      => JoinType.Inner
      case "left"       => JoinType.Left
      case "leftanti"   => JoinType.LeftAnti
      case "leftouter"  => JoinType.LeftOuter
      case "leftsemi"   => JoinType.LeftSemi
      case "right"      => JoinType.Right
      case "rightouter" => JoinType.RightOuter
      case "semi"       => JoinType.Semi
      case _ =>
        implicitly[Logger[F]].error(
          s"Join type $joinType not supported. Using Inner join as default"
        )
        JoinType.Inner

    }
  }

}
