/* YOU can uncomment the comments below and the file will still compile.
   To make this possible we give each a different name like sumI, sumN etc.
   and they represent progressively more potent sum. */
object Sums {

  //def sumI(l: List[Int]) : Int = l.reduce(_ + _)

  //def sumN[A : Numeric](l: List[A]) : A = l.reduce(implicitly[Numeric[A]].plus(_,_))

  //def sumSG[A : Semigroup](l: List[A]) : A = {
  //  import SemigroupOps._
  //  l.reduce(_ |+| _)
  //}

  //def sumF[F[_]: Foldable, A : Monoid](l: F[A]) : A = Foldable[F].fold(l)

  def sum[F[_]: Foldable, A: Monoid](l: F[A]) : A = sumMap(l)(x => x)

  def sumMap[F[_]: Foldable, B: Monoid, A](l: F[A])(f: A => B) : B = Foldable[F].foldMap(l)(f)
}
