package io.github.rafafrdz.teckel.transformation

trait CoreFrom[F[_], I, O] {

  def from(value: I): F[O]

}
