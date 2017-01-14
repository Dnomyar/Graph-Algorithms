package fr.damienraymond.graph

import scala.collection.immutable.Seq

/**
  * Created by damien on 11/01/2017.
  */
class AdjacencyListUndirectedGraph(nodes: List[NodeUndirected]) extends IUndirectedGraph {

  override lazy val nbEdges: Int = nodes.map(_.siblings.size).sum / 2
  override lazy val nbNodes: Int = nodes.size


  override def isEdge(x: Int, y: Int): Boolean =
    nodes.exists(node => node.id == x && node.siblings.exists(_.id == y))

  override def removeEdge(x: Int, y: Int): AdjacencyListUndirectedGraph =
    new AdjacencyListUndirectedGraph(
      nodes.map {
        case node if node.id == x =>
          node.copy(siblings = node.siblings.filterNot(_.id == y))
        case node if node.id == y =>
          node.copy(siblings = node.siblings.filterNot(_.id == x))
        case node => node
      }
    )

  override def addEdge(x: Int, y: Int): AdjacencyListUndirectedGraph =
    new AdjacencyListUndirectedGraph(
      nodes.map {
        case node if node.id == x =>
          node.copy(siblings = nodes.find(_.id == y).get :: node.siblings)
        case node if node.id == y =>
          node.copy(siblings = nodes.find(_.id == x).get :: node.siblings)
        case node => node
      }
    )

  override def getNeighbours(x: Int): List[NodeUndirected] =
    nodes.find(_.id == x).map(_.siblings).getOrElse(List.empty)


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
  def apply(mat: MatGraph): AdjacencyListUndirectedGraph = {
    val nodes =
      mat.mat.zipWithIndex.collect{
        case (line, i) =>
          val succs =
            line.zipWithIndex.collect {
              case (el, j) if j > i && el == 1 => j
            }
          (NodeUndirected(i), succs)
      }

    println(nodes)

    new AdjacencyListUndirectedGraph(
      nodes.map{
        case (node, succs)  =>
          node.copy(siblings = nodes.map(_._1).filter(node => succs.contains(node.id)))
      }
    )
  }
}