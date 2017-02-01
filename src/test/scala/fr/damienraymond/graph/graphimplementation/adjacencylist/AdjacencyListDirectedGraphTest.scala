package fr.damienraymond.graph.graphimplementation.adjacencylist

import fr.damienraymond.graph.model.matgraph.AdjMatGraph
import fr.damienraymond.graph.model.{DirectedNode, UndirectedNode}
import org.scalatest.WordSpec

/**
  * @author Clément Garbay & Anaël Chardan
  */
class AdjacencyListDirectedGraphTest extends WordSpec {

  "A directed adjacency list graph with 4 nodes and 7 arcs" should {
    "have 4 nodes" in new ContextDirected {
      assert(graph.nbNodes == 4)
    }
    "have 7 arcs" in new ContextDirected {
      assert(graph.nbArcs == 7)
    }
    "return true" when {
      "isArc is called and arc exists" in new ContextDirected {
        assert(graph.isArc(1,3))
      }
    }
    "return false" when {
      "isArc is called and arc doesn't exist" in new ContextDirected {
        assert(!graph.isArc(2,3))
      }
    }
    "have 8 arcs" when {
      "addEdge is called with a new arc" in new ContextDirected {
        assert(graph.addArc(2,3).nbArcs == 8)
      }
    }
    "have 7 arcs" when {
      "addEdge is called with not existint nodes" in new ContextDirected {
        assert(graph.addArc(4,0).nbArcs == 7)
      }
    }
    "have 6 arcs" when {
      "removeArc is called" in new ContextDirected {
        assert(graph.removeArc(1,3).nbArcs == 6)
      }
    }
    "have 7 arcs" when {
      "removeArc is called on not existing arc" in new ContextDirected {
        assert(graph.removeArc(4,3).nbArcs == 7)
      }
    }
    "be a int list of 0,3" when {
      "getSuccessors is called with 1 in parameter" in new ContextDirected {
        assert(graph.getSuccessors(1) == Set(0,3))
      }
    }
    "be a int list of 1" when {
      "getSuccessors is called with 2 in parameter" in new ContextDirected {
        assert(graph.getSuccessors(2) == Set(1))
      }
    }
    "be a int list of 1,3" when {
      "getPredecessors is called with 0 in parameter" in new ContextDirected {
        assert(graph.getPredecessors(0) == Set(1,3))
      }
    }
    "be a int list of 0,2" when {
      "getPredecessors is called with 1 in parameter" in new ContextDirected {
        assert(graph.getPredecessors(1) == Set(0,2))
      }
    }
    "be the correct directed adjacency list graph inverse" when {
      "computeInverse is called" in new ContextDirected {
        assert(graph.inverse == graphInverse)
      }
    }
    "be the correct adjacency matrix representation (List of List)" when {
      "toAdjacencyMatrix is called" in new ContextDirected {
        assert(graph.toAdjacencyMatrix == AdjMatGraph(graphMatrix))
      }
    }
    "initialize an undirected adjacency list graph" when {
      "from this directed adjacency list graph" in new ContextDirected {
        assert(graph.undirectedGraph == graphUndirected)
      }
    }
    "initialize a directed adjacency list graph" when {
      "from adjacency matrix representation" in new ContextDirected {
        assert(AdjacencyListDirectedGraph(AdjMatGraph(graphMatrix)) == graph)
      }
    }
  }
}


trait ContextDirected {

  val graph = new AdjacencyListDirectedGraph(Set(
    DirectedNode(0, Set(1, 2)),
    DirectedNode(1, Set(0, 3)),
    DirectedNode(2, Set(1)),
    DirectedNode(3, Set(0, 2))
  ))

  val graphInverse = new AdjacencyListDirectedGraph(Set(
    DirectedNode(0, Set(1, 3)),
    DirectedNode(1, Set(0, 2)),
    DirectedNode(2, Set(0, 3)),
    DirectedNode(3, Set(1))
  ))

  val graphUndirected = new AdjacencyListUndirectedGraph(Set(
    UndirectedNode(0, Set(1, 2, 3)),
    UndirectedNode(1, Set(0, 2, 3)),
    UndirectedNode(2, Set(0, 1, 3)),
    UndirectedNode(3, Set(0, 1, 2))
  ))

  val graphMatrix = List(
    List(0, 1, 1, 0),
    List(1, 0, 0, 1),
    List(0, 1, 0, 0),
    List(1, 0, 1, 0)
  )

}