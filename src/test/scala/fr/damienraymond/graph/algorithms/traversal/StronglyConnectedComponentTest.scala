package fr.damienraymond.graph.algorithms.traversal

import fr.damienraymond.graph.graphimplementation.adjacencylist.{AdjacencyListDirectedGraph, AdjacencyListUndirectedGraph}
import fr.damienraymond.graph.model.{DirectedNode, UndirectedNode}
import org.scalatest.WordSpec

/**
  * Created by damien on 05/02/2017.
  */
class StronglyConnectedComponentTest extends WordSpec {

  "A StronglyConnectedComponent" should {
    "be the good" in new StronglyConnectedComponentContext {

      val graph = Set(
        DirectedNode(0, Set(3, 2)),
        DirectedNode(1, Set(0)),
        DirectedNode(2, Set(3,4)),
        DirectedNode(3, Set(1,5)),
        DirectedNode(4, Set(5,6)),
        DirectedNode(5, Set(4)),
        DirectedNode(6, Set()),
        DirectedNode(7, Set(5))
      )

      val res = stronglyConnectedComponent.visit(AdjacencyListDirectedGraph(graph), 4)

      val expected = Set(Set(0, 1, 2, 3), Set(4, 5), Set(6), Set(7))

      assert(res.map(_._1) == expected)
    }
  }
}


trait StronglyConnectedComponentContext {

  val stronglyConnectedComponent = new StronglyConnectedComponent

}

