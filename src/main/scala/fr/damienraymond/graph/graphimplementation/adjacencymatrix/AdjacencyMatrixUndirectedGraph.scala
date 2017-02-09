package fr.damienraymond.graph
package graphimplementation.adjacencymatrix

import fr.damienraymond.graph.{IUndirectedGraph, IUnweightedUndirectedGraph}
import fr.damienraymond.graph.model.UndirectedNode
import fr.damienraymond.graph.model.matgraph.AdjMatGraph

/**
  * Created by damien on 11/01/2017.
  */
case class AdjacencyMatrixUndirectedGraph(graph: AdjMatGraph)
  extends AbstractAdjacencyMatrixGraph[Int, UndirectedNode, IUndirectedGraph[Int]]
    with IUndirectedGraph[Int]
    with IUnweightedUndirectedGraph[Int] {

  override val nbEdges: Int = graph.mat.map(_.count(_ == 1)).sum / 2
  override val nbLinks: Int = nbEdges


  override protected def updateGraph(x: Int, y: Int, value: Int): IUndirectedGraph[Int] =
    AdjacencyMatrixUndirectedGraph(
      graph.mat.zipWithIndex.map {
        case (line, i) if i == x && line.length > y =>
          line.updated(y, value.toInt)
        case (line, i) if i == y && line.length > x =>
          line.updated(x, value.toInt)
        case (line, _) => line
      }
    )

  override def createNodes(idx: Int, value: Int): Int = idx

  override def addEdge(node1: Int, node2: Int): IUndirectedGraph[Int] =
    updateGraph(node1, node2, 1)


}

object AdjacencyMatrixUndirectedGraph {


  def apply(mat: List[List[Int]]): AdjacencyMatrixUndirectedGraph = new AdjacencyMatrixUndirectedGraph(AdjMatGraph(mat))

  def apply(graph: IUndirectedGraph[Int]): AdjacencyMatrixUndirectedGraph =
    AdjacencyMatrixUndirectedGraph(
      (0 until graph.nbEdges).toList.map{ i =>
        (0 until graph.nbEdges).toList.map{ j =>
          graph.isEdge(i, j).toInt
        }
      }
    )

}