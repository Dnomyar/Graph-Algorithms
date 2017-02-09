package fr.damienraymond.graph.algorithms.shortestpath

import fr.damienraymond.graph.graphimplementation.adjacencylist.AdjacencyListDirectedWeightedGraph
import fr.damienraymond.graph.model.{DirectedNode, Weighted, WeightedDirectedNode}
import org.scalatest.WordSpec

/**
  * Created by damien on 06/02/2017.
  */
class DijkstraTest extends WordSpec {

  "DijkstraTest" should {

    "find Dijkstra's shortestPath" in new DijkstraContext {
      val (dist, path) = dijkstra.shortestPath(graph, 0, 4)

      assert(dist == Some(5))
    }

  }
}

trait DijkstraContext {
  val dijkstra = new Dijkstra

  val nodes: Set[WeightedDirectedNode] = Set(
    WeightedDirectedNode(0, Set(Weighted(1, 1), Weighted(3, 5))),
    WeightedDirectedNode(1, Set(Weighted(2, 3))),
    WeightedDirectedNode(2, Set(Weighted(3, 0), Weighted(4, 2))),
    WeightedDirectedNode(3, Set(Weighted(1, 1), Weighted(4, 1))),
    WeightedDirectedNode(4, Set())
  )

  val graph = AdjacencyListDirectedWeightedGraph(nodes)
}
