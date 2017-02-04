package fr.damienraymond.graph.algorithms.traversal

import fr.damienraymond.graph.IUndirectedGraph

/**
  * Created by damien on 22/01/2017.
  */
class DepthFirstSearch {

  def visit(graph: IUndirectedGraph[Int],
            startNode: Int,
            depthFirstSearchState: DepthFirstSearchState = DefaultDepthFirstSearchState.empty
           ): (Set[Int], DepthFirstSearchState) = {

    def visit(graph: IUndirectedGraph[Int],
              startNode: Int,
              visitedNodes: Set[Int],
              depthFirstSearchState: DepthFirstSearchState): (Set[Int], DepthFirstSearchState) = {


      val depthFirstSearchStateAfterStart = depthFirstSearchState.start(startNode)

      val (res, depthFirstSearchStateEnd) =
        graph.getNeighbours(startNode)
          .foldLeft(visitedNodes, depthFirstSearchStateAfterStart){
            case ((acc, depthFirstSearchStateAcc), neighbour) =>
              if(acc contains neighbour)
                (acc, depthFirstSearchStateAcc)
              else
                visit(graph, neighbour, acc + neighbour, depthFirstSearchStateAcc)
          }

      (res, depthFirstSearchStateEnd.end(startNode))
    }

    visit(graph, startNode, Set(startNode), depthFirstSearchState)
  }

}
