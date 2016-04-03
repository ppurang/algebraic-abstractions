import SemigroupOps._
import EqualOps._
import Sums._
import Foldable._
import org.scalacheck.Test


object TestRun {
  def main(args: Array[String]) {
     SemigroupProps.laws[Int].check()

     SemigroupProps.laws[Int].check(Test.Parameters.default.withMinSuccessfulTests(1000000))

     sum(List(List(1,2,3), List(3,4,5)))

     SemigroupProps.laws[List[Int]].check()

     sum(Tree(1,2,3,4))

     val jan = Map("€" -> 100, "$" -> 900, "#" -> 300)

     val feb = Map("€" -> 900, "#" -> 500)

     sum(List(jan, feb))

     MonoidProps.laws[Int].check()

     sumMap(List('a','b', 'c'))(_.toInt)

    {

      implicit val mult = Monoid.lift(1)(Semigroup.lift(_ * _))

      assert(sum(Tree(1,2,3,4,5)) === 120)
    }


    {
      import Semigroup.{numeric => _}

      implicit val faultyInt : Semigroup[Int] = Semigroup.lift(_ - _)

      SemigroupProps.laws[Int].check()
    }

  }
}
