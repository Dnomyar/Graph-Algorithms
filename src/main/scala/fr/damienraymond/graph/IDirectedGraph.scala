package fr.damienraymond.graph

/**
  * Created by damien on 11/01/2017.
  */
trait IDirectedGraph extends IGraph {

  val nbEdges: Int
  def isArc(from: Int, to: Int): Boolean
  def removeArc(from: Int, to: Int): IDirectedGraph
  def addArc(from: Int, to: Int): IDirectedGraph
  def getSuccessors(node: Int): Set[Int]
  def getPredecessors(node: Int): Set[Int]

}
