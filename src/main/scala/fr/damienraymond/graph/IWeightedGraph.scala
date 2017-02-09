package fr.damienraymond.graph

import fr.damienraymond.graph.model.Node

/**
  * Created by damien on 31/01/2017.
  */
trait IWeightedGraph {

  def getWeight(node1: Int, node2: Int): Option[Int]

}


trait IWeightedUndirectedGraph[T] extends IWeightedGraph {

  def addEdge(node1: Int, node2: Int, weight: Int): IUndirectedGraph[T]// = add(node1, node2, weight)

}

trait IWeightedDirectedGraph[T, U <: Node[T]] extends IWeightedGraph {

  def addArc(node1: Int, node2: Int, weight: Int): IDirectedGraph[T, U]// = add(node1, node2, weight)

}

