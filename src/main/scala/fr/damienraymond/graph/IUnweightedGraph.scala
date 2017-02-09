package fr.damienraymond.graph

import fr.damienraymond.graph.model.Node

/**
  * Created by damien on 31/01/2017.
  */
trait IUnweightedGraph


trait IUnweightedDirectedGraph[T, U <: Node[T]] extends IUnweightedGraph {

  def addArc(node1: Int, node2: Int): IDirectedGraph[T, U]// = add(node1, node2)

}

trait IUnweightedUndirectedGraph[T] extends IUnweightedGraph {

  def addEdge(node1: Int, node2: Int): IUndirectedGraph[T]// = add(node1, node2)

}



