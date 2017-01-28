package fr.damienraymond

import scalaz.{Ordering => O, _}

import scala.language.{higherKinds, implicitConversions}

package object graph {

  class AsInt(b: Boolean) {
    def toInt: Int = if(b) 1 else 0
  }

  implicit def convertBooleanToInt(b: Boolean): AsInt = new AsInt(b)


  class AsOption(b: Boolean) {
    def toOption: Option[Unit] = if(b) Some(()) else None
  }
  implicit def booleanToOption(b: Boolean): AsOption = new AsOption(b)


  implicit class MySeq[A](override val collection: Seq[A])
    extends MinBySafe[A, Seq]
    with MapIfDefined[A, Seq]

  implicit class MyList[A](override val collection: List[A])
    extends MinBySafe[A, List]
    with MapIfDefined[A, List]

  implicit class MySet[A](override val collection: Set[A])
    extends MinBySafe[A, Set]
    with MapIfDefined[A, Set]



  trait MapIfDefined[A, F[_]] {

    val collection: F[A]

    /**
      * mapIfDefined applies a function f to all elements of a Seq
      *
      *   Example of usage :
      *     ```
      *     @ (0 to 10).mapIfDefined{
      *         case e if e > 5 => e * e
      *     }
      *     res1: Seq[Int] = Vector(0, 1, 2, 3, 4, 5, 36, 49, 64, 81, 100)
      *     ```
      *
      *     Same as :
      *     ```
      *     @ (0 to 10).map{
      *         case e if e > 5 => e * e
      *         case e          => e
      *     }
      *     res2: collection.immutable.IndexedSeq[Int] = Vector(0, 1, 2, 3, 4, 5, 36, 49, 64, 81, 100)
      *     ```
      *
      * @param f is a partial function.
      * @return the updated seq
      */
    def mapIfDefined(f: PartialFunction[A, A])(implicit fn: Functor[F]): F[A] =
      fn.map(collection)(f.applyOrElse[A, A](_, identity))

  }


  trait MinBySafe[A, F[_]] {

    val collection: F[A]

    def minBySafe[B](f: A => B)(implicit cmp: Ordering[B], fn: Foldable[F]): Option[A] = {
      fn.foldLeft(collection, Option.empty[A]){
        case (None, sib) => Some(sib)
        case (Some(acc), sib) =>
          if (cmp.lt(f(acc), f(sib))) Some(acc)
          else Some(sib)
      }
    }

  }



}
