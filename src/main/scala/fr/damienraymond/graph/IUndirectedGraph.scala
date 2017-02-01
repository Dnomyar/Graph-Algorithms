package fr.damienraymond.graph

/**
  * Created by damien on 11/01/2017.
  */
trait IUndirectedGraph[T] extends IGraph[T, IUndirectedGraph[T]] {

//  override type Graph = IUndirectedGraph[T]

  val nbEdges: Int = nbLinks

  def isEdge(x: Int, y: Int): Boolean = isLink(x, y)

  def removeEdge(x: Int, y: Int): IUndirectedGraph[T] = removeLink(x, y)

  def getNeighbours(x: Int): Set[T] = getLinked(x)

//  def addEdge(x: Int, y: Int): IUndirectedGraph[T]
}
