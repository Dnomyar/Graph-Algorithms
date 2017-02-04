package fr.damienraymond.graph
package algorithms.minspanningtree

//import scalaz._
import scalaz.std.AllInstances._
import fr.damienraymond.graph.IUndirectedGraph
import fr.damienraymond.graph.graphimplementation.IWeightedGraph
import fr.damienraymond.graph.model.{Node, Weighted, WeightedDirectedNode}


/**
  * Created by damien on 27/01/2017.
  */
class Prim {

  def minimalSpanningTree[T, R <: IGraph[T, R]](graph: IGraph[T, R] with IWeightedGraph, startNode: Option[Int] = None): Set[WeightedDirectedNode] = {


    def prim(nodes: Set[Int],
             treeBuilt: Set[WeightedDirectedNode] = Set.empty): Set[WeightedDirectedNode] = {

      nodes.flatMap(node => graph.getLinkedNodes(node).map((node, _))) // all linked node of nodes
        .flatMap(link => graph.getWeight(link._1, link._2).map((link, _)))
        .filterNot(neighbour => nodes.contains(neighbour._1._2))
        .minBySafe(_._2) match {
        case Some(((currentNode, targetNode), weight)) =>

          val acc =
            treeBuilt
              .filterNot(_.id == currentNode)
              .+{
                treeBuilt
                  .find(_.id == currentNode)
                  .map(n => n.copy(successors = n.successors + Weighted(targetNode, weight)))
                  .getOrElse(WeightedDirectedNode(currentNode, Set(Weighted(targetNode, weight))))
              }

          prim(nodes + targetNode, acc)
        case None => treeBuilt
      }
    }

    if(graph.allNodes.isEmpty)
      Set.empty
    else {
      prim(Set(startNode.getOrElse(graph.allNodes.head)))
    }
  }

}
