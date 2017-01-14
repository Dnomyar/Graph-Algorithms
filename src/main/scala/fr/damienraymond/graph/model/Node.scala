package fr.damienraymond.graph.model

trait Node {
  val id: Int
//  val successorsOrSiblings: List[Node]
}

case class NodeDirected(id: Int, successors: Set[Int] = Set.empty) extends Node
case class NodeUndirected(id: Int, siblings: Set[Int] = Set.empty) extends Node