package fr.damienraymond.graph

import fr.damienraymond.graph.graphimplementation.adjacencylist.AbstractAdjacencyListGraph
import fr.damienraymond.graph.model.Node

/**
  * Created by damien on 11/01/2017.
  */
trait IDirectedGraph[T, U <: Node[T]] extends IGraph[T, IDirectedGraph[T, U]] {

//  override type Graph = IDirectedGraph[T]

  val inverse: IDirectedGraph[T, U]

  val nbArcs: Int = nbLinks

  val undirectedGraph: IUndirectedGraph[T]

  def isArc(from: Int, to: Int): Boolean = isLink(from, to)

  def removeArc(from: Int, to: Int): IDirectedGraph[T, U] = removeLink(from, to)

//  def addArc(from: Int, to: Int): IDirectedGraph[T]

  def getSuccessors(node: Int): Set[T] = getLinked(node)

  def getPredecessors(node: Int): Set[T]

}
