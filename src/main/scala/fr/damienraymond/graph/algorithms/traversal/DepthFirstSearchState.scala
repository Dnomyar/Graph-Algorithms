package fr.damienraymond.graph.algorithms.traversal

/**
  * Created by damien on 26/01/2017.
  */
trait DepthFirstSearchState {
  val starts: Map[Int, Int]
  val ends: Map[Int, Int]

  def start(node: Int): DepthFirstSearchState
  def end(node: Int): DepthFirstSearchState
}

// TODO : State monad ?
case class DefaultDepthFirstSearchState(i: Int,
                                        starts: Map[Int, Int],
                                        ends: Map[Int, Int]) extends DepthFirstSearchState{

  override def start(node: Int): DefaultDepthFirstSearchState =
    DefaultDepthFirstSearchState(i + 1, starts + (node -> (i + 1)), ends)

  override def end(node: Int): DefaultDepthFirstSearchState =
    DefaultDepthFirstSearchState(i + 1, starts, ends + (node -> (i + 1)))
}

object DefaultDepthFirstSearchState {
  def empty = DefaultDepthFirstSearchState(0, Map.empty, Map.empty)
}
