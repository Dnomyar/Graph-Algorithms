package fr.damienraymond.graph
package graphimplementation.adjacencylist

import fr.damienraymond.graph.model.matgraph.AdjMatGraph
import fr.damienraymond.graph.IUndirectedGraph
import fr.damienraymond.graph.model.{AbstractUndirectedNode, UndirectedNode, WeightedDirectedNode}

import scalaz.std.AllInstances._

/**
  * Created by damien on 11/01/2017.
  */
case class AdjacencyListUndirectedGraph(nodes: Set[UndirectedNode])
  extends AbstractAdjacencyListGraph[Int, UndirectedNode, IUndirectedGraph[Int]]
    with IUndirectedGraph[Int]
    with IUnweightedUndirectedGraph[Int] {


  override val nbLinks: Int = nodes.toList.map(_.successorsOrSiblings.size).sum / 2
  override val nbEdges: Int = nbLinks

  override def createGraph(data: Set[UndirectedNode]): IUndirectedGraph[Int] =
    AdjacencyListUndirectedGraph(data)


  override def addEdge(node1: Int, node2: Int): IUndirectedGraph[Int] =
    new AdjacencyListUndirectedGraph(
        nodes.map {
          case node if node.id == node1 =>
            node.copy(siblings = node.siblings + nodes.find(_.id == node2).get.id)
          case node if node.id == node2 =>
            node.copy(siblings = node.siblings + nodes.find(_.id == node1).get.id)
          case other => other
        }
      )

}

object AdjacencyListUndirectedGraph {

  def apply(graph: IUndirectedGraph[Int]): AdjacencyListUndirectedGraph =
    AdjacencyListUndirectedGraph({
      for {
        i <- 0 until graph.nbEdges
        siblings = graph.getNeighbours(i)
      } yield UndirectedNode(i, siblings)
    }.toSet)
  
  def apply(mat: AdjMatGraph): AdjacencyListUndirectedGraph = {
    val nodes =
      mat.mat.zipWithIndex.map{
        case (line, i) =>
          val succs =
            line.zipWithIndex.collect {
              case (el, j) if el == 1 => j
            }
          UndirectedNode(i, succs.toSet)
      }

    println(nodes)

    AdjacencyListUndirectedGraph(nodes.toSet)
  }


}