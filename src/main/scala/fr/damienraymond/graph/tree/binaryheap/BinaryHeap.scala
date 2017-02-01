package fr.damienraymond.graph
package tree.binaryheap

import scalaz.std.AllInstances._

/**
  * A BinaryHeap is a binary tree
  * Each child of a node is either higher or equals than the node
  */
case class BinaryHeap(heap: List[Int]) {


  /**
    * Add a element to the heap
    * The element is positioned at the right place in the tree. It respects the invariant : each child of a node is either higher or equals than the node
    *
    * Complexity : log(heap.size)
    *
    * @param node value to insert
    * @return the updated binary heap
    */
  def add(node: Int): BinaryHeap = {

    def percolateUp(childIndex: Int, heap: List[Int]): List[Int] = {
      val fatherIndex = childIndex / 2 - 1

      if(heap(fatherIndex) > heap(childIndex))
        swap(heap, childIndex, fatherIndex)
      else
        heap
    }

    BinaryHeap(percolateUp(heap.length, heap :+ node))
  }

  private def swap(h: List[Int], idx1: Int, idx2: Int): List[Int] =
    h.updated(idx2, h(idx1)).updated(idx1, h(idx2))


  /**
    * Remove smaller element of the heap and percolate down
    * The smallest element is the head of the list
    *
    * Complexity : log(heap.size)
    *
    * @return the updated binary heap
    */
  def removeSmaller(): BinaryHeap = {

    def percolateDown(idx: Int, heap: List[Int]): List[Int] = {
      def getMinChildrenLowerThan(idx: Int, comparedTo: Int): Option[Int] =
        List((heap.lift(idx * 2 + 1), idx * 2 + 1) , (heap.lift(idx * 2 + 1), idx * 2 + 1))
            .filter(_._1.isDefined).map(e => (e._1.get, e._2))
            .filter(_._1 < comparedTo)
            .minBySafe(_._1)
            .map(_._2)


      getMinChildrenLowerThan(idx, heap(idx)) match {
        case Some(childIdx) =>
          percolateDown(childIdx, swap(heap, idx, childIdx))
        case None =>
          heap
      }

    }

    BinaryHeap(percolateDown(0, swap(heap, 0, heap.size - 1).init))
  }
}