package fr.damienraymond.graph

import scala.collection.immutable.Seq

/**
  * Created by damien on 11/01/2017.
  */
class IncidentMatrixUndirectedGraph(graph: IncMatGraph) extends IUndirectedGraph {

  override lazy val nbEdges: Int = graph.mat.headOption.map(_.size).getOrElse(0)
  override lazy val nbNodes: Int = graph.mat.size

  override def isEdge(x: Int, y: Int): Boolean = {
    val lines: Seq[(List[Int], Int)] = graph.mat.zipWithIndex
    (lines.lift(x), lines.lift(y)) match {
      case (Some((lx, _)), Some((ly, _))) =>
        lx.zip(ly).exists{
          case (ex, ey) => ex == ey
        }
      case _ => false
    }
  }

  private def updateGraph(x: Int, y: Int, value: Boolean) = {
    IncidentMatrixUndirectedGraph(
      graph.mat.zipWithIndex.map {
        case (line, i) if i == x && line.length < y =>
          line.updated(y, value.toInt)
        case (line, i) if i == y && line.length < x =>
          line.updated(x, value.toInt)
        case (line, _) => line
      }
    )
  }

  override def removeEdge(x: Int, y: Int): IncidentMatrixUndirectedGraph =
    updateGraph(x, y, value = false)

  override def addEdge(x: Int, y: Int): IncidentMatrixUndirectedGraph =
    updateGraph(x, y, value = true)

  override def getNeighbours(x: Int): Set[Int] =
    graph.mat
      .lift(x)
      .map(_.zipWithIndex
            .filter(_._1 == 1) // only when the is a edge
            .map(_._2)) // get index
            .getOrElse(List.empty)
            .toSet


  override def toAdjacencyMatrix: AdjMatGraph = ???
}

object IncidentMatrixUndirectedGraph {


  def apply(mat: List[List[Int]]): IncidentMatrixUndirectedGraph = new IncidentMatrixUndirectedGraph(IncMatGraph(mat))
  
  def apply(graph: IUndirectedGraph): IncidentMatrixUndirectedGraph =
    IncidentMatrixUndirectedGraph(
      (0 until graph.nbEdges).toList.map{ i =>
        (0 until graph.nbEdges).toList.map{ j =>
          graph.isEdge(i, j).toInt
        }
      }
    )

}