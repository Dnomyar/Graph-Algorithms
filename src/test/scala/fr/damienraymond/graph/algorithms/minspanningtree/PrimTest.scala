package fr.damienraymond.graph.algorithms.minspanningtree

import fr.damienraymond.graph.graphimplementation.adjacencylist.AdjacencyListUndirectedWeightedGraph
import fr.damienraymond.graph.model.{UndirectedNode, Weighted, WeightedDirectedNode, WeightedUndirectedNode}
import org.scalatest.WordSpec

/**
  * Created by damien on 27/01/2017.
  */
class PrimTest extends WordSpec {

  "Prim algorithm" should {
    "find the minimum spanning tree" in new PrimContext {
      val prim = new Prim
      val res = prim.minimalSpanningTree(graph, Some(1))

      val expected = Set(
        WeightedDirectedNode(1, Set(Weighted(2, 6), Weighted(6, 6), Weighted(3, 8))),
        WeightedDirectedNode(6, Set(Weighted(5, 3), Weighted(7, 4))),
        WeightedDirectedNode(7, Set(Weighted(4, 2)))
      )

//      assert(res == expected)
    }
  }

}

trait PrimContext {

  val nodes = Set(
    WeightedUndirectedNode(1, Set(Weighted(2, 6), Weighted(3, 8), Weighted(4, 13),  Weighted(5, 10))),
    WeightedUndirectedNode(2, Set(Weighted(1, 6))),
    WeightedUndirectedNode(3, Set(Weighted(1, 8), Weighted(7, 10))),
    WeightedUndirectedNode(4, Set(Weighted(1, 13), Weighted(6, 20), Weighted(7, 2))),
    WeightedUndirectedNode(5, Set(Weighted(1, 10), Weighted(6, 3))),
    WeightedUndirectedNode(6, Set(Weighted(1, 6), Weighted(4, 20), Weighted(5, 3), Weighted(7, 4))),
    WeightedUndirectedNode(7, Set(Weighted(3, 10), Weighted(4, 2), Weighted(6, 4)))
  )

  val graph = AdjacencyListUndirectedWeightedGraph(nodes)

}
