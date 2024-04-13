package io.github.rafafrdz.teckel.transformation

import cats.effect._
import io.github.rafafrdz.teckel.core.Source
import io.github.rafafrdz.teckel.core.Source.Unknown
import io.github.rafafrdz.teckel.core.transformation.join.Join._
import io.github.rafafrdz.teckel.core.transformation.join._
import io.github.rafafrdz.teckel.serializer.model.TransformationYaml._
import io.github.rafafrdz.teckel.serializer.model._
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers._

class TransformationYamlSpec extends AnyFlatSpecLike with Matchers with CommonTest {

  object Resources {
    val join: TransformationYaml =
      JoinTransformationYaml(
        "result",
        JoinOperationYaml(
          "left",
          JoinRelationYaml(
            "table1",
            List(JoinSourceYaml("table2", Map("t1pk1" -> "t2pk1", "t1pk2" -> "t2pk2")))
          )
        )
      )

  }

  "JoinTransformationYaml" should "be mapped to an Output Source" in {
    TransformationT.transformation[IO].from(Resources.join).unsafeRunSync() shouldBe Source.From(
      "result",
      Join(
        joinType = JoinType.Left,
        relation = JoinRelation(
          left = Unknown("table1"),
          right = Unknown("table2"),
          on = List(
            RelationField(Column("t1pk1"), Column("t2pk1")),
            RelationField(Column("t1pk2"), Column("t2pk2"))
          )
        )
      ),
      "",
      ""
    )
  }

}
