
package fr.damienraymond

import scala.language.implicitConversions

package object graph {

  class AsInt(b: Boolean) {
    def toInt: Int = if(b) 1 else 0
  }

  implicit def convertBooleanToInt(b: Boolean): AsInt = new AsInt(b)


  class AsOption(b: Boolean) {
    def toOption: Option[Unit] = if(b) Some(()) else None
  }
  implicit def booleanToOption(b: Boolean): AsOption = new AsOption(b)


  implicit class MySeq[A](r: Seq[A]) {
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
    def mapIfDefined(f: PartialFunction[A, A]): Seq[A] = {
      r.map(f.applyOrElse[A, A](_, identity))
    }
  }

  implicit class MyList[A](r: List[A]) {
    /**
      * mapIfDefined applies a function f to all elements of a List
      *   idem than `def mapIfDefined(f: PartialFunction[A, A]): Seq[A]`
      *
      * @param f is a partial function.
      * @return the updated list
      */
    def mapIfDefined(f: PartialFunction[A, A]): List[A] = {
      r.map(f.applyOrElse[A, A](_, identity))
    }
  }

}
