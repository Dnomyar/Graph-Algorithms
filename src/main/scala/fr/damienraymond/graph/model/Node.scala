package fr.damienraymond.graph.model

trait Node {
  val id: Int
//  val successorsOrSiblings: List[Node]
}

case class DirectedNode(id: Int, successors: Set[Int] = Set.empty) extends Node
case class UndirectedNode(id: Int, siblings: Set[Int] = Set.empty) extends Node