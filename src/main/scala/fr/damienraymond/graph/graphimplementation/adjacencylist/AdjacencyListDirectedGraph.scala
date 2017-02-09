package fr.damienraymond.graph
package graphimplementation.adjacencylist

import fr.damienraymond.graph.model.{AbstractDirectedNode, AbstractUndirectedNode, DirectedNode, UndirectedNode}
import fr.damienraymond.graph.model.matgraph.AdjMatGraph
import fr.damienraymond.graph.{IDirectedGraph, IUndirectedGraph}

import scalaz.std.AllInstances._


case class AdjacencyListDirectedGraph(nodes: Set[DirectedNode])
  extends AbstractAdjacencyListGraph[Int, DirectedNode, IDirectedGraph[Int, DirectedNode]]
    with IDirectedGraph[Int, DirectedNode]
    with IUnweightedDirectedGraph[Int, DirectedNode] {

  override val nbLinks: Int = nodes.toList.map(_.successorsOrSiblings.size).sum
  override val nbArcs: Int = nbLinks

  override lazy val undirectedGraph: IUndirectedGraph[Int] = {
      val directedNodes: Set[UndirectedNode] =
        nodes.map(node => UndirectedNode(node.id, node.successors))
      new AdjacencyListUndirectedGraph(
        nodes.foldLeft(directedNodes){ (acc, nodeDir) =>
          acc.map {
            case nodeUndir if nodeDir.successors.contains(nodeUndir.id) =>
              nodeUndir.copy(siblings = nodeUndir.siblings + nodeDir.id)
            case other => other
          }
        }
      )
    }

  override lazy val inverse: IDirectedGraph[Int, DirectedNode] =
    createGraph({
        for{
          node <- nodes
          pred = getPredecessors(node.id)
        } yield DirectedNode(node.id, pred)
      })

  override def createGraph(data: Set[DirectedNode]): IDirectedGraph[Int, DirectedNode] =
    AdjacencyListDirectedGraph(data)

  override def getPredecessors(node: Int): Set[Int] =
    nodes.filter(_.successors.exists(_ == node)).map(_.id)

  override def addArc(node1: Int, node2: Int): IDirectedGraph[Int, DirectedNode] =
    new AdjacencyListDirectedGraph(
        nodes.map {
          case node if node.id == node1 =>
            node.copy(successors = node.successors + nodes.find(_.id == node2).get.id)
          case other => other
        }
      )

}

object AdjacencyListDirectedGraph {

  def apply(mat: AdjMatGraph): AdjacencyListDirectedGraph = {
    val nodes =
      mat.mat.zipWithIndex.collect{
        case (line, i) =>
          val succs =
            line.zipWithIndex.collect {
              case (el, j) if el == 1 => j
            }
          DirectedNode(i, succs.toSet)
      }

    println(nodes)

    new AdjacencyListDirectedGraph(nodes.toSet)
  }
}