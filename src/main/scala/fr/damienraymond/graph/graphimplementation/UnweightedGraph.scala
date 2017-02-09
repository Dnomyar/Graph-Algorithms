package fr.damienraymond.graph.graphimplementation

import fr.damienraymond.graph.model.Node
import fr.damienraymond.graph.{IDirectedGraph, IUndirectedGraph}

/**
  * Created by damien on 31/01/2017.
  */
trait IUnweightedGraph {

  //val allLinks: Set[(Int, Int)]
//  def add(node1: Int, node2: Int): IUnweightedGraph
}


trait IWeightedGraph {

  //val allLinks: Set[((Int, Int), Int)]
  def getWeight(node1: Int, node2: Int): Option[Int]
//  def add(node1: Int, node2: Int, weight: Int): IWeightedGraph

}

trait IUnweightedDirectedGraph[T, U <: Node[T]] extends IUnweightedGraph {

  def addArc(node1: Int, node2: Int): IDirectedGraph[T, U]// = add(node1, node2)

}

trait IUnweightedUndirectedGraph[T] extends IUnweightedGraph {

  def addEdge(node1: Int, node2: Int): IUndirectedGraph[T]// = add(node1, node2)

}


trait IWeightedUndirectedGraph[T] extends IWeightedGraph {

  def addEdge(node1: Int, node2: Int, weight: Int): IUndirectedGraph[T]// = add(node1, node2, weight)

}

trait IWeightedDirectedGraph[T, U <: Node[T]] extends IWeightedGraph {

  def addArc(node1: Int, node2: Int, weight: Int): IDirectedGraph[T, U]// = add(node1, node2, weight)

}

