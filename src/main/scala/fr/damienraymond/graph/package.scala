
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

}
