/** EQUAL */
trait Equal[A] {
  def eq(a: A, b: A): Boolean
}

object Equal {
  def apply[A: Equal] = implicitly[Equal[A]]

  def lift[A](f: (A, A) => Boolean) : Equal[A] = new Equal[A] { def eq(a: A, b: A) = f(a,b) }

  //lift all numeric to semigroups
  implicit def equal[A] : Equal[A] = lift(_ == _) //this is unsound! not for production code

}


final class EqualOps[A: Equal](val self: A) {
  final def ===(other: => A): Boolean = Equal[A].eq(self, other)
}

object EqualOps {
  implicit def toEqualOps[A: Equal](a: A) : EqualOps[A] = new EqualOps[A](a)
}
