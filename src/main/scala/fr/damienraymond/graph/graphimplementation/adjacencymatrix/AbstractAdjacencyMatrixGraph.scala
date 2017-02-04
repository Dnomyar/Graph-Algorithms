package fr.damienraymond.graph
package graphimplementation.adjacencymatrix

import fr.damienraymond.graph.IGraph
import fr.damienraymond.graph.model.Node
import fr.damienraymond.graph.model.matgraph.AdjMatGraph

/**
  * Created by damien on 04/02/2017.
  */
trait AbstractAdjacencyMatrixGraph[T, U <: Node[T], R <: IGraph[T, R]] extends IGraph[T, R] {

  val graph: AdjMatGraph

  override val nbNodes: Int = graph.mat.size
  override val allNodes: Set[Int] = (0 until nbNodes).toSet

  override def isLink(x: Int, y: Int): Boolean =
    graph.mat.lift(x).flatMap(_.lift(y)).getOrElse(0) == 1


//  def createGraph(data: Set[U]): R

  protected def updateGraph(x: Int, y: Int, value: Int): R
//  {
//    createGraph(
//      graph.mat.zipWithIndex.map {
//        case (line, i) if i == x && line.length > y =>
//          line.updated(y, value.toInt)
//        case (line, _) => line
//      }
//    )
//  }


  override def getLinkedNodes(node: Int): Set[Int] =
    graph.mat
      .lift(node)
      .map(_.zipWithIndex
        .filter(_._1 == 1) // only when the is a edge
        .map(_._2)) // get index
      .getOrElse(List.empty)
      .toSet

  override def removeLink(node1: Int, node2: Int): R =
    updateGraph(node1, node2, 0)


  def createNodes(idx: Int, value: Int): T

  // successors or siblings
  override def getLinked(node: Int): Set[T] =
    graph.mat
      .lift(node)
      .map(_.zipWithIndex
        .filter(_._1 == 1) // only when the is a edge
        .map{ case (value, idx) => createNodes(idx, value) })
      .getOrElse(List.empty)
      .toSet
}
