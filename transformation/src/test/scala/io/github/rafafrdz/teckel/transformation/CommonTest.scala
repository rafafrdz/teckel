package io.github.rafafrdz.teckel.transformation

import cats.effect.IO
import cats.effect.unsafe.IORuntime
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

trait CommonTest {

  implicit val runtime: IORuntime = cats.effect.unsafe.IORuntime.global

  implicit val logger: Logger[IO] = Slf4jLogger.getLogger[IO]

}
