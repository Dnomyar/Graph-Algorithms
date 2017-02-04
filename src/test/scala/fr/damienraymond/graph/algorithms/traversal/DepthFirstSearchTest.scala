package fr.damienraymond.graph.algorithms.traversal

import fr.damienraymond.graph.graphimplementation.adjacencylist.AdjacencyListUndirectedGraph
import fr.damienraymond.graph.model.UndirectedNode
import org.scalatest.WordSpec

/**
  * Created by damien on 24/01/2017.
  */
class DepthFirstSearchTest extends WordSpec {

  "A DepthFirstSearch" should {
    "print node in the right order" in new DepthFirstSearchContext {
      val res = breadthFirstSearch.visit(AdjacencyListUndirectedGraph(graph), 1)

      val expected = Set(1, 2, 3, 4, 5, 6, 7)
      val expectedStarts = Map(
          1 -> 1,
          2 -> 2,
          3 -> 3,
          4 -> 6,
          5 -> 5,
          6 -> 4,
          7 -> 7
      )

      val expectedEnds = Map(
          1 -> 14,
          2 -> 13,
          3 -> 12,
          4 -> 9,
          5 -> 10,
          6 -> 11,
          7 -> 8
      )


      assert(res._1 == expected)
      assert(res._2.starts == expectedStarts)
      assert(res._2.ends == expectedEnds)

    }
  }

}

trait DepthFirstSearchContext {

  val breadthFirstSearch = new DepthFirstSearch

  val graph = Set(
    UndirectedNode(1, Set(2, 3)),
    UndirectedNode(2, Set(1, 3, 4, 7)),
    UndirectedNode(3, Set(1, 2, 6)),
    UndirectedNode(4, Set(2, 7, 5)),
    UndirectedNode(5, Set(4, 6)),
    UndirectedNode(6, Set(3, 5, 7)),
    UndirectedNode(7, Set(2, 4, 6))
  )

}

