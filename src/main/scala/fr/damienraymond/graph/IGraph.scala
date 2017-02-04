package fr.damienraymond.graph

import fr.damienraymond.graph.model.Node
import fr.damienraymond.graph.model.matgraph.AdjMatGraph

trait IGraph[T, R <: IGraph[T, R]] {

  val nbNodes: Int
  val allNodes: Set[Int]

  def toAdjacencyMatrix: AdjMatGraph =
    AdjMatGraph(
      (0 until nbNodes).map { i =>
        (0 until nbNodes).map { j =>
          isLink(i, j).toInt
        }.toList
      }.toList
    )

  // arc or edges
  val nbLinks: Int

  def removeLink(node1: Int, node2: Int): R
  def isLink(node1: Int, node2: Int): Boolean

  // successors or siblings
  def getLinked(node: Int): Set[T]

  def getLinkedNodes(node: Int): Set[Int]

}
