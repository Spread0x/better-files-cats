package com.github.spread0x.better_files_cats.algebra

trait SerDe[I, O, F[_]] {

  /**
    * Call [[serDe]] on any value i: I  for which an implicit instance of
    * [[serDe]]`[I]` exists in scope.
    *
    * @param i The input type to deserialize from
    * @tparam F   the type of wrapper
    * @tparam Err The type of Error
    * @return The result of the computation of `I`
    */
  def serDe(i: I): F[O]
}

object SerDe {
  def newInstance[I, O, F[_]](d: I => F[O]): SerDe[I, O, F] =
    new SerDe[I, O, F] {
      def serDe(i: I): F[O] = d(i)
    }

  def apply[I, O, F[_]](implicit SD: SerDe[I, O, F]): SerDe[I, O, F] = SD
}
