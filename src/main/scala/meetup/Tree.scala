import scala.annotation.tailrec

sealed trait Tree[A]
case class Leaf[A](a: A) extends Tree[A]
case class Fork[A](l: Tree[A], r : Tree[A]) extends Tree[A]

object Tree {
  def apply[A](a: A, as : A*) = {
    @tailrec
    def tree(as: List[(A,Int)], t: Tree[A]): Tree[A] = as match {
      case xi :: xss =>
        val (x,i) = xi
        if(i%2 == 0)
          tree(xss, Fork(Leaf(x),t))
        else
          tree(xss, Fork(t, Leaf(x)))

      case _ => t
    }
    tree(as.to[List].zipWithIndex, Leaf(a))
  }
}

