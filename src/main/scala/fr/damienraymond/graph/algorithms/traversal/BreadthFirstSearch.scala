package fr.damienraymond.graph.algorithms.traversal

import fr.damienraymond.graph.{IDirectedGraph, IUndirectedGraph}
import fr.damienraymond.graph.graphimplementation.adjacencylist.AdjacencyListUndirectedGraph
import fr.damienraymond.graph.model.UndirectedNode

import scala.collection.immutable.Seq

/**
  * Breadth-first search is an algorithm that visits neighbor vertices before visiting the child vertices
  *
  * Created by damien on 22/01/2017.
  */
class BreadthFirstSearch {


  def visit(graph: IUndirectedGraph[Int], startNode: Int): List[Set[Int]] = {

    def visitSet(graph: IUndirectedGraph[Int], startNodes: Set[Int], visitedNodes: Set[Int], nodesToVisit: List[Set[Int]]): (Set[Int], List[Set[Int]]) = {
        val siblings =
          startNodes.flatMap(graph.getNeighbours).diff(visitedNodes)

        if (siblings.isEmpty) (visitedNodes, nodesToVisit)
        else visitSet(graph, siblings, siblings ++ visitedNodes, nodesToVisit :+ siblings)
    }

    val (visitedNodes, res) = visitSet(graph, Set(startNode), Set(startNode), List(Set(startNode)))

    if (visitedNodes.size < graph.nbNodes)
      visit(graph, (visitedNodes diff graph.allNodes).head)
    else res
  }





}
