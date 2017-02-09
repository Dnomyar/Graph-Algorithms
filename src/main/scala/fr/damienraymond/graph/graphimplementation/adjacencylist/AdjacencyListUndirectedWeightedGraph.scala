package fr.damienraymond.graph
package graphimplementation.adjacencylist

import fr.damienraymond.graph.IUndirectedGraph
import fr.damienraymond.graph.model.matgraph.AdjMatGraph
import fr.damienraymond.graph.model.{UndirectedNode, Weighted, WeightedDirectedNode, WeightedUndirectedNode}

import scalaz.std.AllInstances._

/**
  * Created by damien on 11/01/2017.
  */
case class AdjacencyListUndirectedWeightedGraph(nodes: Set[WeightedUndirectedNode])
  extends AbstractAdjacencyListGraph[Weighted, WeightedUndirectedNode, IUndirectedGraph[Weighted]]
    with IUndirectedGraph[Weighted]
    with IWeightedUndirectedGraph[Weighted] {

  override val nbLinks: Int = nodes.toList.map(_.successorsOrSiblings.size).sum / 2
  override val nbEdges: Int = nbLinks

  override def createGraph(data: Set[WeightedUndirectedNode]): IUndirectedGraph[Weighted] =
    AdjacencyListUndirectedWeightedGraph(data)


  override def addEdge(node1: Int, node2: Int, weight: Int) =
     AdjacencyListUndirectedWeightedGraph(
        nodes.map {
          case node if node.id == node1 =>
            node.copy(siblings = node.siblings + Weighted(nodes.find(_.id == node2).get.id, weight))
          case node if node.id == node2 =>
            node.copy(siblings = node.siblings + Weighted(nodes.find(_.id == node1).get.id, weight))
          case other => other
        }
      )


  override def getWeight(node1: Int, node2: Int): Option[Int] =
    nodes.find(_.id == node1).flatMap(_.siblings.find(_.node == node2)).map(_.weight)

}


//object AdjacencyListUndirectedWeightedGraph {
//
//
//
//}
