package fr.damienraymond.graph

import scala.collection.immutable.Seq

/**
  * Created by damien on 11/01/2017.
  */
class AdjacencyListUndirectedGraph(nodes: List[NodeUndirected]) extends IUndirectedGraph {

  override lazy val nbEdges: Int = nodes.map(_.siblings.size).sum / 2
  override lazy val nbNodes: Int = nodes.size

  override def isEdge(x: Int, y: Int): Boolean =
    nodes.exists(node => node.id == x && node.siblings.contains(y))

  override def removeEdge(x: Int, y: Int): AdjacencyListUndirectedGraph =
    new AdjacencyListUndirectedGraph(
      nodes.mapIfDefined {
        case node if node.id == x =>
          node.copy(siblings = node.siblings.filterNot(_ == y))
        case node if node.id == y =>
          node.copy(siblings = node.siblings.filterNot(_ == x))
      }
    )

  override def addEdge(x: Int, y: Int): AdjacencyListUndirectedGraph =
    new AdjacencyListUndirectedGraph(
      nodes.mapIfDefined {
        case node if node.id == x =>
          node.copy(siblings = node.siblings + nodes.find(_.id == y).get.id)
        case node if node.id == y =>
          node.copy(siblings = node.siblings + nodes.find(_.id == x).get.id)
      }
    )

  override def getNeighbours(x: Int): Set[Int] =
    nodes.find(_.id == x).map(_.siblings).getOrElse(List.empty).toSet


  override def toAdjacencyMatrix: AdjMatGraph =
    AdjMatGraph(
        (0 until nbNodes).map { i =>
          (0 until nbNodes).map { j =>
            (isEdge(i, j) || isEdge(j, i)).toInt
          }.toList
        }.toList
      )
}

object AdjacencyListUndirectedGraph {

  def apply(graph: IUndirectedGraph): AdjacencyListUndirectedGraph =
    new AdjacencyListUndirectedGraph({
      for {
        i <- 0 until graph.nbEdges
        siblings = graph.getNeighbours(i)
      } yield NodeUndirected(i, siblings)
    }.toList)
  
  def apply(mat: AdjMatGraph): AdjacencyListUndirectedGraph = {
    val nodes =
      mat.mat.zipWithIndex.collect{
        case (line, i) =>
          val succs =
            line.zipWithIndex.collect {
              case (el, j) if j > i && el == 1 => j
            }
          NodeUndirected(i, succs.toSet)
      }

    println(nodes)

    new AdjacencyListUndirectedGraph(nodes)
  }


}