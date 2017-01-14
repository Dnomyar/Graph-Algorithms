package fr.damienraymond.graph.model.matgraph

/**
  * Created by damien on 10/01/2017.
  *
  *
  * List of line :
  *   - column : edge / arc
  *   - line : node
  *
  */
case class IncMatGraph(mat: List[List[Int]]) extends AnyVal {
  override def toString: String = mat.map(_.mkString("  ")).mkString("\n")
}

object IncMatGraph {

  /**
    * Build a MatGraph (List[List[Int]] => squared matrix) from List[Int]
    */
  def fromList(list: List[Int]): IncMatGraph =
    this (
      {
        val size = Math.sqrt(list.size.toDouble).toInt // squared matrix => sqrt
        for (i <- 0 until size)
        yield list.slice(i * size, (i + 1) * size)
      }.toList
    )
}

