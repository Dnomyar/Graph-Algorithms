package fr.damienraymond.graph


import scala.collection.immutable.Seq
import scala.util.Random

/**
  * Created by damien on 11/01/2017.
  */
object GraphTools {


  /**
    * Generate the matrix representation of a graph (directed or not)
    */
  def generateGraphData(graphOrder: Int, numberOfArcs: Int, isUndirected: Boolean): MatGraph = {

    /**
      * Generate list of unique tuple (of size `numberOfArcs`) that are the coordinates of the 1 in the matrix
      * Manage directed and undirected matrix
      */
    def generateCoords(graphOrder: Int, numberOfArcs: Int, isUndirected: Boolean, acc: List[(Int, Int)] = List()): List[(Int, Int)] = numberOfArcs match {
      case 0 => acc
      case _ =>
        val coord @ (x, y) = (Random.nextInt(graphOrder), Random.nextInt(graphOrder))
        if (acc.contains(coord) || x == y || (y > x && isUndirected)){
          generateCoords(graphOrder, numberOfArcs, isUndirected, acc)
        }else {
          if (isUndirected)
            generateCoords(graphOrder, numberOfArcs - 1, isUndirected, coord :: coord.swap :: acc)
          else
            generateCoords(graphOrder, numberOfArcs - 1, isUndirected, coord :: acc)
        }
    }

    val coordArcs = generateCoords(graphOrder, numberOfArcs, isUndirected)

    MatGraph.fromList({
      for{
        i <- 0 until graphOrder
        j <- 0 until graphOrder
        isOne = coordArcs contains (i, j)
      } yield isOne.toInt
    }.toList)
  }



}
