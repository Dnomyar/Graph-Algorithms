package fr.damienraymond.graph


trait Node {
  val id: Int
}

case class NodeDirected(id: Int, successors: List[NodeDirected] = List.empty) extends Node
case class NodeUndirected(id: Int, siblings: List[NodeUndirected] = List.empty) extends Node