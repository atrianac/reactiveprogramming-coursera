package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._
import Math._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  property("min1") = forAll { a: A =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("min2") = forAll { (a: A, b: A) =>
    val h1 = insert(a, empty)
    val h2 = insert(b, h1)

    findMin(h2) == min(a, b)
  }

  property("del1") = forAll { a: A =>
    val h = insert(a, empty)
    val h1 = deleteMin(h)

    isEmpty(h1)
  }

  property("del2") = forAll { (a: Int, b: Int) =>
    val h1 = insert(a, empty)
    val h2 = insert(b, h1)
    val h3 = deleteMin(h2)

    findMin(h3) == Math.max(a, b)
  }

  property("meld1") = forAll { (a: H, b: H) =>
    val h = meld(a, b)
    findMin(h) == min(findMin(a), findMin(b))
  }

  property("meld2") = forAll { (h1: H, h2: H) =>
    def heapEqual(h1: H, h2: H): Boolean =
      if (isEmpty(h1) && isEmpty(h2)) true
      else {
        val m1 = findMin(h1)
        val m2 = findMin(h2)
        m1 == m2 && heapEqual(deleteMin(h1), deleteMin(h2))
      }
    heapEqual(meld(h1, h2),
      meld(deleteMin(h1), insert(findMin(h1), h2)))
  }
  
  property("meld3") = forAll { a: H =>
    val h = meld(a, empty)
    findMin(h) == findMin(a)
  }

  property("meld4") = forAll { a: Int =>
    val h1 = insert(a, empty)
    val h2 = meld(h1, empty)
    val h3 = deleteMin(h2)

    isEmpty(h3)
  }

  property("meld5") = forAll { (a: H, b: H) =>
    val h1 = meld(a, b)
    val h2 = deleteMin(h1)

    val h3 = if (findMin(a) < findMin(b)) meld(deleteMin(a), b)
    else meld(a, deleteMin(b))

    findMin(h2) == findMin(h3)
  }

  lazy val genHeap: Gen[H] = for {
    x <- arbitrary[A]
    m <- oneOf(const(empty), genHeap)
  } yield insert(x, m)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

}
