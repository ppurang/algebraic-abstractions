/**  FOLDABLE */
trait Foldable[F[_]] {
  def foldMap[A, B : Monoid](m: F[A])(f: A => B): B
  def fold[A : Monoid](m: F[A]): A = foldMap(m)(x => x)
}

import SemigroupOps._

object Foldable {
  def apply[F[_]: Foldable] : Foldable[F] = implicitly[Foldable[F]]

  implicit val list : Foldable[List] = new Foldable[List] {
    def foldMap[A, B : Monoid](m: List[A])(f: A => B): B  = m.foldRight(Monoid[B].∅)(f(_) |+| _)
  }

  implicit val tree : Foldable[Tree] = new Foldable[Tree] {
    def foldMap[A, B : Monoid](m: Tree[A])(f: A => B): B = m match {
                case Leaf(a) => f(a)
                case Fork(l, r) => (foldMap(l)(f) |+| foldMap(r)(f))
    }
  }
}
