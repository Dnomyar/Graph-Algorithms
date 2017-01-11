package fr.damienraymond.graph

/**
  * Created by damien on 11/01/2017.
  */
trait IUndirectedGraph extends IGraph {

  val nbEdges: Int
  def isEdge(x: Int, y: Int): Boolean
  def removeEdge(x: Int, y: Int): IUndirectedGraph
  def addEdge(x: Int, y: Int): IUndirectedGraph
  def getNeighbours(x: Int): List[Node]

}
