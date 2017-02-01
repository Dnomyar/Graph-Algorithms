package fr.damienraymond.graph.model

trait Node[T] {
  val id: Int
  val successorsOrSiblings: Set[T]
  val successorsOrSiblingsNodes: Set[Int]
  def withNewSuccessorsOrSiblings(newSet: Set[T]): Node[T]

  def hasAsSuccessorsOrSiblings(node: Int): Boolean = successorsOrSiblingsNodes.contains(node)
}


trait AbstractDirectedNode[T] extends Node[T] {
  def withNewSuccessors(newSet: Set[T]): Node[T] = withNewSuccessorsOrSiblings(newSet)
  val successors: Set[T]
  override val successorsOrSiblings: Set[T] = successors
}

trait AbstractUndirectedNode[T] extends Node[T] {
  def withNewSiblings(newSet: Set[T]): Node[T] = withNewSuccessorsOrSiblings(newSet)
  val siblings: Set[T]
  override val successorsOrSiblings: Set[T] = siblings
}



case class DirectedNode(id: Int,
                        successors: Set[Int] = Set.empty)
  extends AbstractDirectedNode[Int] {

  override def withNewSuccessorsOrSiblings(newSet: Set[Int]): Node[Int] = copy(successors = newSet)

  override val successorsOrSiblingsNodes: Set[Int] = successors
}


case class UndirectedNode(id: Int,
                          siblings: Set[Int] = Set.empty)
  extends AbstractUndirectedNode[Int] {

  override def withNewSuccessorsOrSiblings(newSet: Set[Int]): Node[Int] = copy(siblings = newSet)

  override val successorsOrSiblingsNodes: Set[Int] = siblings
}


case class Weighted(node: Int, weight: Int)

case class WeightedDirectedNode(id: Int,
                                successors: Set[Weighted] = Set.empty)
  extends AbstractDirectedNode[Weighted] {

  override def withNewSuccessorsOrSiblings(newSet: Set[Weighted]): Node[Weighted] = copy(successors = newSet)

  override val successorsOrSiblingsNodes: Set[Int] = successors.map(_.node)
}


case class WeightedUndirectedNode(id: Int,
                                  siblings: Set[Weighted] = Set.empty)
  extends AbstractUndirectedNode[Weighted] {

  override def withNewSuccessorsOrSiblings(newSet: Set[Weighted]): Node[Weighted] = copy(siblings = newSet)

  override val successorsOrSiblingsNodes: Set[Int] = siblings.map(_.node)
}
