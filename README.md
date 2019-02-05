# Algebraic Abstractions

## Run locally

To run the presentation just load the file `presentation/algebraic-abstractions.html` in a browser.

*Note*: Chrome by default doesn't allow accessing local files. If you still want to use chrome &mdash; start chrome with `--allow-file-access-from-files`


## This repo

This repository holds the presentation and the code for a talk given at a Scala UG Berlin talk on  [Algebraic Abstractions](http://www.meetup.com/Scala-Berlin-Brandenburg/events/229456176/). The presentation is in the folder named presentation, `algebraic-abstractions.html` and a set of quotes on Abstractions can be seen in `quotes.html` also in the same folder.

This is also an sbt project with the source under `src/main/scala`. The source is organized into type-classes representing the various abstractions. `Sums` includes the `sum` used as a vehicle to introduce the various abstractions.

An `sbt console` session (REPL session) might look like this

```scala 
% sbt 
> console 
[info] Starting scala interpreter...
[info]
import SemigroupOps._
import EqualOps._
import Sums._
import Foldable._
import Monoid._
import org.scalacheck.Test
Welcome to Scala 2.11.8 (Java HotSpot(TM) 64-Bit Server VM, Java 1.8.0_51).
Type in expressions for evaluation. Or try :help.

scala> SemigroupProps.laws[Int].check()
+ semigroup.associativity: OK, passed 100 tests.

scala> val dp = Test.Parameters.default
dp: org.scalacheck.Test.Parameters = Parameters

scala> SemigroupProps.laws[Int].check(dp.withMinSuccessfulTests(1000000))
+ semigroup.associativity: OK, passed 1000000 tests.

scala> sum(List(List(1,2,3), List(3,4,5)))
res0: List[Int] = List(1, 2, 3, 3, 4, 5)

scala> SemigroupProps.laws[List[Int]].check()
+ semigroup.associativity: OK, passed 100 tests.

scala> sum(Tree(1,2,3,4))
res1: Int = 10

scala> val jan = Map("€" -> 100, "$" -> 900, "#" -> 300)
jan: scala.collection.immutable.Map[String,Int] = Map(€ -> 100, $ -> 900, # -> 300)

scala> val feb = Map("€" -> 900, "#" -> 500)
feb: scala.collection.immutable.Map[String,Int] = Map(€ -> 900, # -> 500)

scala> sum(List(jan, feb))
res3: scala.collection.immutable.Map[String,Int] = Map(€ -> 1000, # -> 800, $ -> 900)

scala> MonoidProps.laws[Int].check()
+ monoid.associativity: OK, passed 100 tests.
+ monoid.left identity: OK, passed 100 tests.
+ monoid.right identity: OK, passed 100 tests.

scala> sumMap(List('a','b', 'c'))(_.toInt)
res4: Int = 294

scala> val mult = Monoid.lift(1)(Semigroup.lift(_ * _))
mult: goto.Monoid[Int] = goto.Monoid$$anon$2@771786bd

scala> sum(Tree(1,2,3,4,5))(tree,mult)
res5: Int = 120

scala> val faultyIntSG : Semigroup[Int] = Semigroup.lift(_ - _)
faultyIntSG: Semigroup[Int] = Semigroup$$anon$1@56af11b7

scala> SemigroupProps.laws[Int](faultyIntSG, implicitly[Equal[Int]], implicitly[org.scalacheck.Arbitrary[Int]]).check()
! semigroup.associativity: Falsified after 0 passed tests.
> ARG_0: 519846777
> ARG_1: -1
> ARG_2: 2147483647
```

or you can checkout `TestRun` in `src/test` directory and run

```scala
% sbt
> test:compile
  ...
> test:runMain TestRun
  [info] Running TestRun
  [info]
  [info] + semigroup.associativity: OK, passed 100 tests.
  [info]
  [info] + semigroup.associativity: OK, passed 1000000 tests.
  [info]
  [info] + semigroup.associativity: OK, passed 100 tests.
  [info]
  [info] + monoid.associativity: OK, passed 100 tests.
  [info]
  [info] + monoid.left identity: OK, passed 100 tests.
  [info]
  [info] + monoid.right identity: OK, passed 100 tests.
  [info]
  [info] ! semigroup.associativity: Falsified after 0 passed tests.
  [info] > ARG_0: -476403604
  [info] > ARG_1: -1768763504
  [info] > ARG_2: 1
  [success] Total time: 3 s, completed Apr 3, 2016 3:54:37 PM
```

## Hosted versions

[Talk Slides](http://piyush.purang.net/abstr/abstractions.html#1) 

[Scastie](https://scastie.scala-lang.org/zX0s4LbwSwC69qvrhQYTbQ)


## Improvements

Use tut.



