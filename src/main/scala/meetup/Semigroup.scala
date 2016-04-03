/**  SEMIGROUPS  */
trait Semigroup[A] {
  def op: A => A => A
}

object Semigroup {
  def apply[A: Semigroup] : Semigroup[A] = implicitly[Semigroup[A]]

  def lift[A](f: (A, A) => A) : Semigroup[A] = new Semigroup[A] { def op = a => b => f(a,b) }

  //lift all numeric to semigroups
  implicit def numeric[A: Numeric] = lift(implicitly[Numeric[A]].plus)
  implicit val strs = lift[String](_ + _)
  implicit def lists[A] : Semigroup[List[A]]= Semigroup.lift(_ ::: _)
}

final class SemigroupOps[A: Semigroup](val self: A) {
  final def |+|(other: => A): A = Semigroup[A].op(self)(other)
}

object SemigroupOps {
  implicit def toSemigroupOps[A: Semigroup](a: A) : SemigroupOps[A] = new SemigroupOps(a)
}

import SemigroupOps._
import EqualOps._

object SemigroupLawsVerbose {
  def associativity[A: Semigroup : Equal](a: A, b: A, c: A): Boolean = {
    val sg = implicitly[Semigroup[A]]
    val eq = implicitly[Equal[A]]
    eq.eq(sg.op(sg.op(a)(b))(c), sg.op(a)(sg.op(b)(c)))
  }
}

object SemigroupLaws {
  def associativity[A: Semigroup : Equal](a: A, b: A, c: A): Boolean = {
    ((a |+| b) |+| c) ===  (a |+| (b |+| c))
  }
}

import org.scalacheck._, Prop.forAll

object SemigroupProps {
  def laws[A: Semigroup: Equal: Arbitrary] = new Properties("semigroup") {
    property("associativity") = forAll(SemigroupLaws.associativity[A] _)
  }
}

