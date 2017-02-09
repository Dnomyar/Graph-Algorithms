package fr.damienraymond.graph.algorithms.traversal

import fr.damienraymond.graph.{IGraph, IUndirectedGraph}

/**
  * Created by damien on 05/02/2017.
  */
class StronglyConnectedComponent {

  val depthFirstSearch = new DepthFirstSearch


  def visit[T, R <: IGraph[T, R]](graph: IGraph[T, R],
            startNode: Int) = {



    def stronglyConnectedComponent(visitedNodes: Set[Int], depthFirstSearchState: DepthFirstSearchState, startNode: Int): (Set[Int], DepthFirstSearchState) = {
      val visitResult = depthFirstSearch.visit(graph, startNode, depthFirstSearchState)

      val newVisitedNodes = visitedNodes ++ visitResult._1
      val diff = graph.allNodes.diff(newVisitedNodes)

      if (diff.isEmpty)
        visitResult
      else
        stronglyConnectedComponent(newVisitedNodes, visitResult._2, diff.head)
    }

    val resFirstVisit = stronglyConnectedComponent(Set.empty, DefaultDepthFirstSearchState.empty, startNode)

    println(resFirstVisit._1)

    val inverse = graph.inverse

    val order =
      resFirstVisit._2.ends
        .toList
        .sortBy(t => - t._2)
        .map(_._1)

    def stronglyConnectedComponentInverse(visitNodeOrder: List[Int], depthFirstSearchState: DepthFirstSearchState): Set[(Set[Int], DepthFirstSearchState)] =
      if(visitNodeOrder.isEmpty){
        Set.empty
      }else{
        val visitResult = depthFirstSearch.visitBy(inverse, visitNodeOrder, depthFirstSearchState)
        val newOrder = visitNodeOrder.diff(visitResult._1.toList)
        stronglyConnectedComponentInverse(newOrder, visitResult._2) + visitResult
      }


    val res = stronglyConnectedComponentInverse(order, DefaultDepthFirstSearchState.empty)

    res.foreach(e => println(e._1))

    res
  }

//
//  def stronglyConnectedComponent[T, R <: IGraph[T, R]](graph: IGraph[T, R], visitNodeOrder: List[Int])(f: (IGraph[T, R], List[Int])): Set[(Set[Int], DepthFirstSearchState)] =
//    if(visitNodeOrder.isEmpty){
//      Set.empty
//    }else{
//      val visitResult = depthFirstSearch.visitBy(graph, visitNodeOrder)
//      val newOrder = visitNodeOrder.diff(visitResult._1.toList)
//      stronglyConnectedComponent(graph, newOrder) + visitResult
//    }

}
