package fr.damienraymond.graph
package graphimplementation.adjacencymatrix

import fr.damienraymond.graph.graphimplementation.IUnweightedDirectedGraph
import fr.damienraymond.graph.model.DirectedNode
import fr.damienraymond.graph.model.matgraph.AdjMatGraph

/**
  * Created by damien on 11/01/2017.
  */
case class AdjacencyMatrixDirectedGraph(graph: AdjMatGraph)
  extends AbstractAdjacencyMatrixGraph[Int, DirectedNode, IDirectedGraph[Int, DirectedNode]]
    with IDirectedGraph[Int, DirectedNode]
    with IUnweightedDirectedGraph[Int, DirectedNode] {

  override val nbArcs: Int = graph.mat.map(_.count(_ == 1)).sum
  override val nbLinks: Int = nbArcs


  override protected def updateGraph(x: Int, y: Int, value: Int): IDirectedGraph[Int, DirectedNode] = {
    AdjacencyMatrixDirectedGraph(
        graph.mat.zipWithIndex.map {
          case (line, i) if i == x && line.length > y =>
            line.updated(y, value)
          case (line, _) => line
        }
      )
    }

  override def createNodes(idx: Int, value: Int): Int = idx

  override lazy val inverse: IDirectedGraph[Int, DirectedNode] =
    AdjacencyMatrixDirectedGraph(graph.mat.transpose)

  override lazy val undirectedGraph: IUndirectedGraph[Int] =
    new AdjacencyMatrixUndirectedGraph(
        AdjMatGraph(
          graph.mat.zipWithIndex.map{
            case (line, x) =>
              line.zipWithIndex.map{
                case (e, y) if e == 1 || graph.mat(y)(x) == 1 => 1
                case _ => 0
              }
          }
        )
      )

  override def getPredecessors(node: Int): Set[Int] =
    graph.mat
      .zipWithIndex
      .map(line => (line._1.lift(node).filter(_ == 1), line._2))
      .filter(_._1.isDefined).map(_._2)
      .toSet

  override def addArc(node1: Int, node2: Int): IDirectedGraph[Int, DirectedNode] =
    updateGraph(node1, node2, 1)


}

object AdjacencyMatrixDirectedGraph {


  def apply(mat: List[List[Int]]): AdjacencyMatrixDirectedGraph = new AdjacencyMatrixDirectedGraph(AdjMatGraph(mat))

  def apply(graph: IDirectedGraph[Int, DirectedNode]): AdjacencyMatrixDirectedGraph =
    AdjacencyMatrixDirectedGraph(
      (0 until graph.nbArcs).toList.map{ i =>
        (0 until graph.nbArcs).toList.map{ j =>
          graph.isArc(i, j).toInt
        }
      }
    )

}