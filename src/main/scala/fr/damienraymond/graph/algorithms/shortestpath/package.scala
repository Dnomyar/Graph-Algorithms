package fr.damienraymond.graph.algorithms


package object shortestpath {


  type Distance = (Int, Option[Int])
  type Distances = List[Distance]

  def min(dist1: Distance, dist2: Distance): Distance =
    (dist1, dist2) match {
      case ((_, Some(d1)), (_, Some(d2))) if d1 <= d2 => dist1
      case ((_, Some(d1)), (_, Some(d2))) if d1 > d2 => dist2
      case ((_, None), (_, Some(_))) => dist2
      case ((_, Some(_)), (_, None)) => dist1
      case ((_, None), (_, None)) => dist1
    }


}