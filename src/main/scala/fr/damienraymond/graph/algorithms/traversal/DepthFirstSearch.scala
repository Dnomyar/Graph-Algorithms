package fr.damienraymond.graph.algorithms.traversal

import fr.damienraymond.graph.{IGraph, IUndirectedGraph}

/**
  * Created by damien on 22/01/2017.
  */
class DepthFirstSearch {


  /**
    * Common DFS
    * @param graph
    * @param startNode
    * @param depthFirstSearchState
    * @tparam T
    * @tparam R
    * @return
    */
  def visit[T, R <: IGraph[T, R]](graph: IGraph[T, R],
            startNode: Int,
            depthFirstSearchState: DepthFirstSearchState = DefaultDepthFirstSearchState.empty
           ): (Set[Int], DepthFirstSearchState) =

    visitReq(graph, startNode, Set(startNode), depthFirstSearchState)


  /**
    * Ordered DFS visit of the graph
    * @param graph
    * @param visitNodeOrder
    * @param depthFirstSearchState
    * @tparam T
    * @tparam R
    * @return
    */
  def visitBy[T, R <: IGraph[T, R]](graph: IGraph[T, R],
                                    visitNodeOrder: List[Int],
                                    depthFirstSearchState: DepthFirstSearchState = DefaultDepthFirstSearchState.empty
           ): (Set[Int], DepthFirstSearchState) =

    visitNodeOrder.headOption match {
      case Some(node) =>
        visitReq(graph, node, Set(node), depthFirstSearchState, node => - visitNodeOrder.indexOf(node))
      case None => (Set.empty, DefaultDepthFirstSearchState.empty)
    }




  private def visitReq[T, R <: IGraph[T, R]](graph: IGraph[T, R],
                    startNode: Int,
                    visitedNodes: Set[Int],
                    depthFirstSearchState: DepthFirstSearchState,
                    sort: Int => Int = identity): (Set[Int], DepthFirstSearchState) = {


    val depthFirstSearchStateAfterStart = depthFirstSearchState.start(startNode)

    val (res, depthFirstSearchStateEnd) =
      graph.getLinkedNodes(startNode)
        .diff(depthFirstSearchStateAfterStart.visitedNodes)
        .toList
        .sortBy(sort)
        .foldLeft(visitedNodes, depthFirstSearchStateAfterStart){
          case ((acc, depthFirstSearchStateAcc), neighbour) =>
            if(acc contains neighbour)
              (acc, depthFirstSearchStateAcc)
            else
              visitReq(graph, neighbour, acc + neighbour, depthFirstSearchStateAcc)
        }

    (res, depthFirstSearchStateEnd.end(startNode))
  }


}
