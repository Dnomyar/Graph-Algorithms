package fr.damienraymond.graph
package graphimplementation.adjacencylist

import fr.damienraymond.graph.IDirectedGraph
import fr.damienraymond.graph.model.{AbstractDirectedNode, Node}
import fr.damienraymond.graph.model.matgraph.AdjMatGraph

/**
  * Created by damien on 11/01/2017.
  */

trait AbstractAdjacencyListGraph[T, U <: Node[T], R <: IGraph[T, R]] extends IGraph[T, R] /*with GraphFactory[T, U]*/ {


  val nodes: Set[U]

  override val nbNodes: Int = nodes.size
  override val allNodes: Set[Int] = nodes.map(_.id)
  override val nbLinks: Int = nodes.toList.map(_.successorsOrSiblings.size).sum

  def createGraph(data: Set[U]): R

  override def removeLink(node1: Int, node2: Int): R =
    createGraph(
      nodes.map {
        case node if node.id == node1 =>
          node.withNewSuccessorsOrSiblings(node.successorsOrSiblings.filterNot(_ == node2)).asInstanceOf[U]
        case other => other.asInstanceOf[U]
      }
    )

  override def isLink(x: Int, y: Int): Boolean =
    nodes.exists(node => node.id == x && node.hasAsSuccessorsOrSiblings(y))

  // successors or predecessors
  override def getLinked(node: Int): Set[T] =
    nodes.find(_.id == node).map(_.successorsOrSiblings).getOrElse(Set.empty)

  override def getLinkedNodes(node: Int): Set[Int] =
    nodes.find(_.id == node).map(_.successorsOrSiblingsNodes).getOrElse(Set.empty)

}
