package fr.damienraymond.graph
package graphimplementation.adjacencylist

import fr.damienraymond.graph.graphimplementation.{IWeightedDirectedGraph, IWeightedGraph}
import fr.damienraymond.graph.model.matgraph.AdjMatGraph
import fr.damienraymond.graph.model._
import fr.damienraymond.graph.{IDirectedGraph, IUndirectedGraph}

import scalaz.std.AllInstances._

/**
  * Created by damien on 11/01/2017.
  */
case class AdjacencyListDirectedWeightGraph(nodes: Set[WeightedDirectedNode])
  extends AbstractAdjacencyListGraph[Weighted, WeightedDirectedNode, IDirectedGraph[Weighted, WeightedDirectedNode]]
    with IDirectedGraph[Weighted, WeightedDirectedNode]
    with IWeightedDirectedGraph[Weighted, WeightedDirectedNode] {


  override def createGraph(data: Set[WeightedDirectedNode]): IDirectedGraph[Weighted, WeightedDirectedNode] =
    AdjacencyListDirectedWeightGraph(data)

  override lazy val undirectedGraph: IUndirectedGraph[Weighted] = ???

  override def getPredecessors(node: Int): Set[Weighted] = ???

  override lazy  val inverse: IDirectedGraph[Weighted, WeightedDirectedNode] =
    createGraph({
        for{
          node <- nodes
          pred = getPredecessors(node.id)
        } yield WeightedDirectedNode(node.id, pred)
      })

  override def addArc(node1: Int, node2: Int, weight: Int): IDirectedGraph[Weighted, WeightedDirectedNode] =
    AdjacencyListDirectedWeightGraph(
        nodes.map {
          case node if node.id == node1 =>
            node.copy(successors = node.successors + Weighted(nodes.find(_.id == node2).get.id, Int.MaxValue)) // TODO : ugly
          case other => other
        }
      )


  override def getWeight(node1: Int, node2: Int): Option[Int] =
    nodes.find(_.id == node1).flatMap(_.successors.find(_.node == node2)).map(_.weight)


}

object AdjacencyListDirectedWeightGraph {

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
