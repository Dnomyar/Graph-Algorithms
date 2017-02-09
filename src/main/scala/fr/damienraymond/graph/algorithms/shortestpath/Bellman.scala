package fr.damienraymond.graph.algorithms.shortestpath

import fr.damienraymond.graph.IGraph
import fr.damienraymond.graph.graphimplementation.IWeightedGraph

/**
  * Created by damien on 05/02/2017.
  */
class Bellman {


  def shortestPath[T, R <: IGraph[T, R]](graph: IGraph[T, R] with IWeightedGraph, startNode: Int): List[(Int, Option[Int])] = {

    val nodes = graph.allNodes.toList.sorted

    // None if infinite
    val distances: Distances =
      nodes.map{
        case node if node == startNode => (node, Some(0))
        case node => (node, None)
      }


    def updateDistances(prevDistance: Distances, distances: Distances, nodeIdxX: Int, nodeIdxY: Int): Distances = {

      val prevDistanceOfX: Distance = prevDistance(nodeIdxX)
      val prevDistanceOfY: Distance = distances(nodeIdxY)

      val weight = graph.getWeight(nodeIdxX, nodeIdxY).get // link always exists

      val prevDistanceOfXPlusWeight: Distance =
        prevDistanceOfX._2 match {
          case Some(prevDist) => prevDistanceOfY.copy(_2 = Option(prevDist + weight))
          case None => prevDistanceOfY.copy(_2 = None)
        }

      distances.updated(nodeIdxY, min(prevDistanceOfY, prevDistanceOfXPlusWeight))
    }


    nodes
      .foldLeft(distances) { (distanceAccNodes, _) =>
        graph.allLinks
          .filterNot(_._2 == startNode) // do not change start node in the distance array
          .foldLeft(distanceAccNodes) { (distanceAccLinks, link) =>
            updateDistances(distanceAccNodes, distanceAccLinks, link._1, link._2)
          }
      }
  }

}
