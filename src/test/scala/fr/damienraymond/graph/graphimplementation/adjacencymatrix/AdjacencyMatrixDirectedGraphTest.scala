package fr.damienraymond.graph.graphimplementation.adjacencymatrix

import fr.damienraymond.graph.graphimplementation.adjacencylist
import fr.damienraymond.graph.graphimplementation.adjacencymatrix.{AdjacencyMatrixDirectedGraph, AdjacencyMatrixUndirectedGraph}
import fr.damienraymond.graph.model.matgraph.AdjMatGraph
import org.scalatest.WordSpec

/**
  * @author Clément Garbay & Anaël Chardan
  */
class AdjacencyMatrixDirectedGraphTest extends WordSpec {

  "A directed adjacency list graph with 4 nodes and 7 arcs" should {
    "have 4 nodes" in new ContextDirected {
      assert(graph.nbNodes == 4)
    }
    "have 6 arcs" in new ContextDirected {
      assert(graph.nbArcs == 6)
    }
    "return true" when {
      "isArc is called and arc exists" in new ContextDirected {
        assert(graph.isArc(1, 3))
      }
    }
    "return false" when {
      "isArc is called and arc doesn't exist" in new ContextDirected {
        assert(!graph.isArc(2, 3))
      }
    }
    "have 7 arcs" when {
      "addEdge is called with a new arc" in new ContextDirected {
        assert(graph.addArc(2, 3).nbArcs == 7)
      }
    }
    "have 6 arcs" when {
      "addEdge is called with not existing nodes" in new ContextDirected {
        assert(graph.addArc(5, 0).nbArcs == 6)
      }
    }
    "have 5 arcs" when {
      "removeArc is called" in new ContextDirected {
        assert(graph.removeArc(1, 3).nbArcs == 5)
      }
    }
    "have 6 arcs" when {
      "removeArc is called on not existing arc" in new ContextDirected {
        assert(graph.removeArc(4, 3).nbArcs == 6)
      }
    }
    "be a int list of 1, 2" when {
      "getSuccessors is called with 1 in parameter" in new ContextDirected {
        assert(graph.getSuccessors(0) == Set(1, 2))
      }
    }
    "be a int list of 3" when {
      "getSuccessors is called with 1 in parameter" in new ContextDirected {
        assert(graph.getSuccessors(1) == Set(3))
      }
    }
    "be a int list of 0" when {
      "getSuccessors is called with 2 in parameter" in new ContextDirected {
        assert(graph.getSuccessors(2) == Set(1))
      }
    }
    "be a int list of 1,3" when {
      "getPredecessors is called with 0 in parameter" in new ContextDirected {
        assert(graph.getPredecessors(0) == Set(3))
      }
    }
    "be a int list of 0,2" when {
      "getPredecessors is called with 1 in parameter" in new ContextDirected {
        assert(graph.getPredecessors(1) == Set(0, 2))
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
//    "initialize a directed adjacency list graph" when {
//      "from adjacency matrix representation" in new ContextDirected {
//        assert(AdjacencyListDirectedGraph(graphMatrix) == graph)
//      }
//    }
  }
}


trait ContextDirected {

  val graphMatrix = List(
    List(0, 1, 1, 0),
    List(0, 0, 0, 1),
    List(0, 1, 0, 0),
    List(1, 0, 1, 0)
  )

  val graphMatrixInverse = List(
    List(0, 0, 0, 1),
    List(1, 0, 1, 0),
    List(1, 0, 0, 1),
    List(0, 1, 0, 0)
  )

  val graphMatrixUndirected = List(
    List(0, 1, 1, 1),
    List(1, 0, 1, 1),
    List(1, 1, 0, 1),
    List(1, 1, 1, 0)
  )

  val graph = AdjacencyMatrixDirectedGraph(graphMatrix)
  val graphInverse = AdjacencyMatrixDirectedGraph(graphMatrixInverse)
  val graphUndirected = AdjacencyMatrixUndirectedGraph(graphMatrixUndirected)
}