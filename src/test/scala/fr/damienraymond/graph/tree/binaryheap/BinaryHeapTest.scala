package fr.damienraymond.graph.tree.binaryheap

import org.scalatest.WordSpec

/**
  * Created by damien on 27/01/2017.
  */
class BinaryHeapTest extends WordSpec {


  "A BinaryHeap" should {
    "not percolate up 1" when {
      "add a new element" in {
        val heap = BinaryHeap(List(1,2,13,3,4,19))
        val expected = List(1,2,13,3,4,19,21)

        assert(heap.add(21).heap == expected)
      }
    }
    "not percolate up 2" when {
      "add a new element" in {
        val heap = BinaryHeap(List(1,2,13,3,4))
        val expected = List(1,2,13,3,4,19)

        assert(heap.add(19).heap == expected)
      }
    }
    "percolate up" when {
      "add a new element" in {
        val heap = BinaryHeap(List(1,2,13,3,4,19))
        val expected = List(1,2,5,3,4,19,13)

        assert(heap.add(5).heap == expected)
      }
    }

    "percolate down" when {
      "remove smaller" in {
        val heap = BinaryHeap(List(1,2,13,3,4,19))
        val expected = List(2,3,13,19,4)

        assert(heap.removeSmaller.heap == expected)
      }
    }


  }

}

