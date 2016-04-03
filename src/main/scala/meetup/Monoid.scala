/** MONOIDS */
trait Monoid[A] extends Semigroup[A]  {
  def identity : A
  def ∅ = identity
}

object Monoid {
  def apply[A: Monoid] : Monoid[A] = implicitly[Monoid[A]]

  def lift[A: Semigroup](a: A) : Monoid[A] = new Monoid[A] { def identity = a; def op = Semigroup[A].op}
  def lift[A](z: A, f: (A, A) => A) = new Monoid[A] {def identity = z; def op = a => b => f(a, b)}

  implicit def int = lift(0)
  implicit val strs = lift("")
  implicit def lists[A] = new Monoid[List[A]] {  //no time to see why this doesn't work.. lift(Nil)
    def identity = Nil
    def op = a => b => a ::: b
  }

  implicit def MapMonoid[K, V: Semigroup]  = lift(Map[K,V](),(m1: Map[K, V], m2: Map[K, V]) => {
    //note we don't worry about efficiency just yet
    // how would we make this more efficient? map sizes?
    m1.foldLeft(m2){case (to,(k,v)) => to.updated(k, to.get(k).map(Semigroup[V].op(_)(v)).getOrElse(v))}
  })

}

import SemigroupOps._
import EqualOps._

object MonoidLaws {
  def associativity[A: Monoid : Equal](a: A, b: A, c: A): Boolean = {
    ((a |+| b) |+| c) ===  (a |+| (b |+| c))
  }
  def leftIdentity[A: Monoid : Equal](a: A): Boolean = {
    (Monoid[A].∅ |+| a) ===  a
  }
  def rightIdentity[A: Monoid : Equal](a: A): Boolean = {
    (a |+| Monoid[A].∅) ===  a
  }
}

import org.scalacheck._, Prop.forAll

object MonoidProps {
  def laws[A: Monoid: Equal: Arbitrary] = new Properties("monoid") {
    property("associativity") = forAll(MonoidLaws.associativity[A] _)
    property("left identity") = forAll(MonoidLaws.leftIdentity[A] _)
    property("right identity") = forAll(MonoidLaws.rightIdentity[A] _)
  }
}

