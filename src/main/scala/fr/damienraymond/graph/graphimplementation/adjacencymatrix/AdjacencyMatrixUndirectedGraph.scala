package fr.damienraymond.graph
package graphimplementation.adjacencymatrix

import fr.damienraymond.graph.IUndirectedGraph
import fr.damienraymond.graph.model.matgraph.AdjMatGraph

/**
  * Created by damien on 11/01/2017.
  */
case class AdjacencyMatrixUndirectedGraph(graph: AdjMatGraph) extends IUndirectedGraph {

  override lazy val nbEdges: Int = graph.mat.map(_.count(_ == 1)).sum / 2
  override lazy val nbNodes: Int = graph.mat.size

  override def isEdge(x: Int, y: Int): Boolean =
    graph.mat.lift(x).flatMap(_.lift(y)).getOrElse(0) == 1

  private def updateGraph(x: Int, y: Int, value: Boolean) =
    AdjacencyMatrixUndirectedGraph(
      graph.mat.zipWithIndex.map {
        case (line, i) if i == x && line.length > y =>
          line.updated(y, value.toInt)
        case (line, i) if i == y && line.length > x =>
          line.updated(x, value.toInt)
        case (line, _) => line
      }
    )

  override def removeEdge(x: Int, y: Int): AdjacencyMatrixUndirectedGraph =
    updateGraph(x, y, value = false)

  override def addEdge(x: Int, y: Int): AdjacencyMatrixUndirectedGraph =
    updateGraph(x, y, value = true)

  override def getNeighbours(x: Int): Set[Int] =
    graph.mat
      .lift(x)
      .map(_.zipWithIndex
            .filter(_._1 == 1) // only when the is a edge
            .map(_._2)) // get index
            .getOrElse(List.empty)
            .toSet


  override def toAdjacencyMatrix: AdjMatGraph = graph
}

object AdjacencyMatrixUndirectedGraph {


  def apply(mat: List[List[Int]]): AdjacencyMatrixUndirectedGraph = new AdjacencyMatrixUndirectedGraph(AdjMatGraph(mat))
  
  def apply(graph: IUndirectedGraph): AdjacencyMatrixUndirectedGraph =
    AdjacencyMatrixUndirectedGraph(
      (0 until graph.nbEdges).toList.map{ i =>
        (0 until graph.nbEdges).toList.map{ j =>
          graph.isEdge(i, j).toInt
        }
      }
    )

}