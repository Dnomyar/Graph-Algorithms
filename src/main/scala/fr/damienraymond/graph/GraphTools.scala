package fr.damienraymond.graph


import scala.util.Random

/**
  * Created by damien on 11/01/2017.
  */
object GraphTools {


  /**
    * Generate the matrix representation of a graph (directed or not)
    * @param graphOrder number of nodes
    * @return either
    *          - an error : if the graph is undirected and we try to generate
    */
  def generateGraphData(graphOrder: Int, numberOfArcs: Int, isUndirected: Boolean): Either[String, MatGraph] = {

    if(graphOrder * 2 < numberOfArcs && isUndirected)
      return Left(s"The maximum of arc in graph with $graphOrder node is ${graphOrder * 2}, $numberOfArcs is too much !")

    val arcs = provideGenerator(graphOrder, numberOfArcs, isUndirected)

    val mat =
      (0 until graphOrder).toList.map { i =>
        (0 until graphOrder).toList.map { j =>
          arcs.shouldIKeepTheArc((i, j))
            .toInt // trick : true => 1 ; false => 0
        }
      }

    Right(MatGraph(mat))
  }

  def provideGenerator(graphOrder: Int, numberOfArcs: Int, isUndirected: Boolean): ArcGenerator =
    if(numberOfArcs < (graphOrder * graphOrder) / 2)
      new TopBottomArcGenerator(graphOrder, numberOfArcs, isUndirected)
    else new BottomTopArcGenerator(graphOrder, numberOfArcs, isUndirected)


}

trait ArcGenerator {

  def shouldIKeepTheArc(arc: (Int, Int)): Boolean

  protected def getCoordinates(graphOrder: Int, isUndirected: Boolean): Stream[(Int, Int)] =
    Stream.continually((Random.nextInt(graphOrder), Random.nextInt(graphOrder)))
      .filterNot(c => c._1 == c._2) // only 0 in the diagonal of the matrix
      .filterNot(c => c._2 > c._1 && isUndirected) // only a triangle if matrix is undirected
      .distinct

}


/**
  * Generate a list of arc (tuple (node, node))
  * Useful when `numberOfArcs >= (graphOrder ^ 2) / 2` (approximately)
  *   because to much arcs would generated
  */
class BottomTopArcGenerator(graphOrder: Int, numberOfArcs: Int, isUndirected: Boolean) extends ArcGenerator {

  private val arcs =
    getCoordinates(graphOrder, isUndirected)
      .take(graphOrder * graphOrder - numberOfArcs)
      .toSet

  override def shouldIKeepTheArc(arc: (Int, Int)): Boolean = ! (arcs contains arc)

}



/**
  * Generate a list of arc (tuple (node, node))
  * Useful when `numberOfArcs < (graphOrder ^ 2) / 2` (approximately)
  *   because the probability of getting a random value that respects conditions when numberOfArcs is close to `graphOrder ^ 2` falls
  */
class TopBottomArcGenerator(graphOrder: Int, numberOfArcs: Int, isUndirected: Boolean) extends ArcGenerator {

  private val arcs =
    getCoordinates(graphOrder, isUndirected)
      .take(numberOfArcs)
      .flatMap(arc => if (isUndirected) Stream(arc, arc.swap) else Stream(arc))
      .toSet

  override def shouldIKeepTheArc(arc: (Int, Int)): Boolean = arcs contains arc

}




