package fr.damienraymond.graph.algorithms.shortestpath

import fr.damienraymond.graph.graphimplementation.adjacencylist.AdjacencyListDirectedWeightedGraph
import fr.damienraymond.graph.model.{DirectedNode, Weighted, WeightedDirectedNode}
import org.scalatest.WordSpec

/**
  * Created by damien on 06/02/2017.
  */
class BellmanTest extends WordSpec {

  "BellmanTest" should {

    "find Bellman's shortestPath" in new BellmanContext {
      val res = bellman.shortestPath(graph, 0).toSet
      val expected = Set(
        (0, Some(0)),
        (1, Some(5)),
        (2, Some(5)),
        (3, Some(7)),
        (4, Some(9)),
        (5, Some(8))
      )

      assert(res == expected)
    }

  }
}

trait BellmanContext {
  val bellman = new Bellman

  val nodes: Set[WeightedDirectedNode] = Set(
    WeightedDirectedNode(0, Set(Weighted(5, 8), Weighted(1, 10))),
    WeightedDirectedNode(1, Set(Weighted(3, 2))),
    WeightedDirectedNode(2, Set(Weighted(1, 1))),
    WeightedDirectedNode(3, Set(Weighted(2, -2))),
    WeightedDirectedNode(4, Set(Weighted(1, -4))),
    WeightedDirectedNode(5, Set(Weighted(4, 1)))
  )

  val graph = AdjacencyListDirectedWeightedGraph(nodes)
}
