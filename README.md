# Graph

The aim of this project is to implement a kind a framework to manage graph data structure and some algorithms using Scala.

This project is based on "Graphs and algorithms" lesson at "École des Mines de Nantes".

This project will probably use this resource : [http://web.engr.oregonstate.edu/~erwig/papers/InductiveGraphs_JFP01.pdf](http://web.engr.oregonstate.edu/~erwig/papers/InductiveGraphs_JFP01.pdf)



## Graph generation implementation
I created a function that generates an adjacency matrix to represent the graph. This function takes the graph order (number of nodes), the number of edges (or arcs) and whether the graph is directed or not.

I tried to figure out the efficient algorithm to generate a directed graph.

The matrix is generated using this piece of code :
```
(0 until graphOrder).toList.map { i =>
  (0 until graphOrder).toList.map { j =>
    arcs.shouldIKeepTheArc((i, j))
      .toInt // trick : true => 1 ; false => 0
  }
}
```
I created a matrix using a double nested ranges (`0 until graphOrder`).

But, the question is : how to implement `shouldIKeepTheArc` ?

### 2 algorithms : top-bottom and bottom-top
These two algorithms have to generate an exact number of arcs. I mean they have to answer yes to the question `shouldIKeepTheArc` a given number of times.

The graph is a simple one (must have no loop)

#### Top-bottom
The idea of this algorithm is to generate arcs of the graph.

This algorithm is efficient when there is only "few" arcs to generate because the more arcs we need more the probability to get a arc that does not already exists falls.

#### Bottom-top
The idea of this algorithm is to generate arcs "to remove" from the graph.

This algorithm is efficient when there is only "few" arcs to remove for the same reason.


### Where is the threshold ?
For now I have set the threshold at `numberOfArcs < (graphOrder * graphOrder) / 2`.

This logically send more time when `numberOfArcs` is not far than `(graphOrder * graphOrder) / 2`.


| Number of nodes | Number of arcs | Time (ms) |
|-----------------|----------------|-----------|
| 1000            | 1000 * 0       | 127,15    |
| 1000            | 1000 * 100     | 749,41    |
| 1000            | 1000 * 200     | 746,81    |
| 1000            | 1000 * 300     | 1108,87   |
| 1000            | 1000 * 400     | 1473,48   |
| 1000            | 1000 * 500     | 1688,84   |
| 1000            | 1000 * 600     | 1274,71   |
| 1000            | 1000 * 700     | 971,43    |
| 1000            | 1000 * 800     | 667,84    |
| 1000            | 1000 * 900     | 357,07    |

I think that there are better algorithm and better threshold.