import sbt._

name := "algebraic-abstractions"

organization := "org.purang"

scalaVersion := "2.11.8"

libraryDependencies ++=  Seq(
  "org.scalacheck" %% "scalacheck" % "1.13.0"
)

logBuffered := false

scalacOptions ++= Seq("-unchecked",
  //"-optimize",
  "-feature",
  "-language:_",
  "-deprecation",
  //"-Xfatal-warnings",
  "-Xlint",
  "-encoding",  "UTF-8",
  "-target:jvm-1.8",
  "-Ywarn-adapted-args",
  "-Ywarn-value-discard",
  "-Xlog-reflective-calls",
  "-Yinline-warnings",
  "-Yclosure-elim",
  "-Yinline",
  "-Xverify")
  
cancelable := true

fork := true

initialCommands += """
    |import SemigroupOps._
    |import EqualOps._
    |import Sums._
    |import Foldable._ 
    |import Monoid._
    |import org.scalacheck.Test
  """.stripMargin
