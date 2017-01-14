package fr.damienraymond.graph

import scala.collection.immutable.Seq

/**
  * Created by damien on 11/01/2017.
  */
class AdjacencyMatrixDirectedGraph(graph: AdjMatGraph) extends IDirectedGraph {

  override lazy val nbEdges: Int = graph.mat.map(_.count(_ == 1)).sum
  override lazy val nbNodes: Int = graph.mat.size

  override def isArc(x: Int, y: Int): Boolean =
    graph.mat.lift(x).flatMap(_.lift(y)).getOrElse(0) == 1

  private def updateGraph(x: Int, y: Int, value: Boolean) = {
    AdjacencyMatrixDirectedGraph(
      graph.mat.zipWithIndex.map {
        case (line, i) if i == x && line.length < y =>
          line.updated(y, value.toInt)
        case (line, _) => line
      }
    )
  }

  override def removeArc(x: Int, y: Int): AdjacencyMatrixDirectedGraph =
    updateGraph(x, y, value = false)

  override def addArc(x: Int, y: Int): AdjacencyMatrixDirectedGraph =
    updateGraph(x, y, value = true)


  override def getSuccessors(node: Int): Set[Int] =
    graph.mat
      .lift(node)
      .map(_.zipWithIndex
            .filter(_._1 == 1) // only when the is a edge
            .map(_._2)) // get index
      .getOrElse(List.empty)
      .toSet

  override def getPredecessors(node: Int): Set[Int] =
    graph.mat
      .flatMap{ line =>
        line.zipWithIndex
          .lift(node)
          .filter(_._1 == 1)
          .map(_._2)
      }
      .toSet


  override def toAdjacencyMatrix: AdjMatGraph = graph
}

object AdjacencyMatrixDirectedGraph {


  def apply(mat: List[List[Int]]): AdjacencyMatrixDirectedGraph = new AdjacencyMatrixDirectedGraph(AdjMatGraph(mat))
  
  def apply(graph: IDirectedGraph): AdjacencyMatrixDirectedGraph =
    AdjacencyMatrixDirectedGraph(
      (0 until graph.nbEdges).toList.map{ i =>
        (0 until graph.nbEdges).toList.map{ j =>
          graph.isArc(i, j).toInt
        }
      }
    )

}