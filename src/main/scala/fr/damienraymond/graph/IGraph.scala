package fr.damienraymond.graph

import fr.damienraymond.graph.model.matgraph.AdjMatGraph

/**
  * Created by damien on 11/01/2017.
  */
trait IGraph {

  val nbNodes: Int
  def toAdjacencyMatrix: AdjMatGraph

}
