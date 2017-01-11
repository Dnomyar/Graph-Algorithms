
package fr.damienraymond

import scala.language.implicitConversions

package object graph {

  class asInt(b: Boolean) {
    def toInt: Int = if(b) 1 else 0
  }

  implicit def convertBooleanToInt(b: Boolean): asInt = new asInt(b)
}
