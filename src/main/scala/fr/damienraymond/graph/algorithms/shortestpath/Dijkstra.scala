package fr.damienraymond.graph
package algorithms.shortestpath

import fr.damienraymond.graph.{IGraph, IWeightedGraph}

/**
  * Created by damien on 09/02/2017.
  */
class Dijkstra {


  /**
    *
    * @param graph
    * @param startNode
    * @tparam T
    * @tparam R
    * @return (distance, path)
    */
  def shortestPath[T, R <: IGraph[T, R]](graph: IGraph[T, R] with IWeightedGraph, startNode: Int, endNode: Int): (Option[Int], List[Int]) = {

    def dijkstra(startNode: Int,
                 visitedNodes: Set[Int],
                 dist: List[Option[Int]],
                 pred: List[Option[Int]]): (List[Option[Int]], List[Option[Int]]) = {

      val node2Visit =
        graph.getLinkedNodes(startNode)

      val (newDist, newPred) =
        node2Visit
          .map(node => (node, graph.getWeight(startNode, node)))
          .collect {
            case (node, Some(d)) => (node, d)
          }
          .foldLeft((dist, pred)) {
            case ((accDist, accPred), (node, distance)) =>
              val newDist = min((node, dist(node)), (startNode, dist(startNode).map(_ + distance)))

              (accDist.updated(node, newDist._2), accPred.updated(node, Some(startNode)))
          }


      if(visitedNodes.size < graph.nbNodes){

        val nextNode =
          node2Visit
            .diff(visitedNodes)
            .map(node => (node, graph.getWeight(startNode, node)))
            .collect {
              case (node, Some(d)) => (node, d)
            }
            .minBy(_._2)
            ._1

        dijkstra(nextNode, visitedNodes + nextNode, newDist, newPred)
      } else (newDist, newPred)
    }


    val dist = (0 until graph.nbNodes).toList.map{
      case node if node == startNode => Some(0)
      case _ => None
    }

    val pred = (0 until graph.nbNodes).toList.map{
      case node if node == startNode => Some(startNode)
      case _ => None
    }


    val (newDist, newPred) = dijkstra(startNode, Set(startNode), dist, pred)


    println(s"newDist: $newDist")
    println(s"newPred: $newPred")
    (newDist(endNode), List.empty)

  }

}
