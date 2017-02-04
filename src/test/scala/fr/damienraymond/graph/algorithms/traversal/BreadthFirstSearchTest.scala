package fr.damienraymond.graph.algorithms.traversal

import fr.damienraymond.graph.graphimplementation.adjacencylist.AdjacencyListUndirectedGraph
import fr.damienraymond.graph.model.UndirectedNode
import org.scalatest.WordSpec

/**
  * Created by damien on 24/01/2017.
  */
class BreadthFirstSearchTest extends WordSpec {

  "A BreadthFirstSearch" should {
    "print node in the right order" in new BreadthFirstSearchContext {
      val res = breadthFirstSearch.visit(AdjacencyListUndirectedGraph(graph), 1)

      val expected = List(
        Set(1),
        Set(2,3),
        Set(7,4,6),
        Set(5)
      )

      assert(res == expected)
    }
  }

}

trait BreadthFirstSearchContext {

  val breadthFirstSearch = new BreadthFirstSearch

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

