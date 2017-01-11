package fr.damienraymond.graph

/**
  * Created by damien on 11/01/2017.
  */
trait IDirectedGraph extends IGraph {

  def getNbArcs: Int
  def isArc(from: Int, to: Int): Boolean
  def removeArc(from: Int, to: Int): Unit
  def addArc(from: Int, to: Int): Unit
  def getSuccessors(node: Int): List[Int]
  def getPredecessors(node: Int): List[Int]

}
