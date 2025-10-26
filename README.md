
# Assignment 3: Optimization of a City Transportation Network

## **1. Objective**

The goal of this assignment was to optimize a city’s transportation network by determining the minimum-cost set of roads that connect all districts.
The network was modeled as a weighted undirected graph, where vertices represent city districts, edges represent possible roads, and the weights correspond to the cost of constructing those roads.

Two algorithms - **Prim’s algorithm** and **Kruskal’s algorithm** - were implemented in Java to find the **Minimum Spanning Tree (MST)**.
Each algorithm was tested on multiple graphs, and their efficiency and performance were analyzed and compared.

---

## **2. Input Data Summary**

The input data was provided in JSON format and consisted of two separate graphs.
The first graph contained five districts labeled A, B, C, D, and E, connected by seven possible roads.
The second graph contained four districts labeled A, B, C, and D, connected by five roads.
Each edge in the JSON input included three properties: “from”, “to”, and “weight”, which represented the two endpoints of a road and its construction cost.

---

## **3. Results**

### **Graph 1**

For the first graph with five districts and seven possible roads, both Prim’s and Kruskal’s algorithms successfully found the same minimum spanning tree with a total cost of **16**.

The edges forming the MST were:

* Between B and C with a cost of 2
* Between A and C with a cost of 3
* Between B and D with a cost of 5
* Between D and E with a cost of 6

Although both algorithms produced identical results, their performance metrics differed slightly.
Kruskal’s algorithm completed the task in approximately **~1.28 milliseconds**, performing 37 key operations.
Prim’s algorithm took around **~1.52 milliseconds**, with 42 total operations.
The small difference in performance is expected because Prim’s algorithm performs more operations on its priority queue, while Kruskal’s relies primarily on sorting and union-find operations.

---

### **Graph 2**

For the second graph with four districts and five possible roads, both algorithms again produced identical MSTs, with a total cost of **6**.

The resulting MST consisted of the following edges:

* Between A and B with a cost of 1
* Between B and C with a cost of 2
* Between C and D with a cost of 3

Kruskal’s algorithm required slightly more operations and time (around **0.92 milliseconds**) compared to Prim’s algorithm, which completed in about **0.87 milliseconds**.
The difference here is minimal, but Prim’s algorithm performed a bit better because the graph was small and relatively dense, making its adjacency-based approach efficient.

---

## **4. Comparison of Algorithms**

Prim’s and Kruskal’s algorithms take fundamentally different approaches to building a Minimum Spanning Tree.

Prim’s algorithm starts from an arbitrary vertex and grows the MST by repeatedly choosing the cheapest edge connecting a visited vertex to an unvisited one.
It makes heavy use of a priority queue to track the minimum-cost edges at each step.
This makes Prim’s algorithm particularly efficient for **dense graphs**, where most vertices are connected by many edges.

Kruskal’s algorithm, in contrast, begins by sorting all edges in increasing order of weight.
It then adds edges one by one, skipping those that would create a cycle.
The algorithm relies on a **Union-Find (Disjoint Set)** data structure to efficiently check for cycles.
Kruskal’s algorithm tends to perform better on **sparse graphs**, since it primarily depends on edge sorting and relatively few union operations.

In this experiment, both algorithms demonstrated nearly identical performance on small datasets.
However, the internal operation counts reveal that Prim’s algorithm performs more priority queue insertions and extra comparisons, while Kruskal’s performs more union and find operations due to its reliance on the disjoint set structure.

---

## **5. Discussion**

The experimental results confirm that both algorithms correctly compute the same MST cost, as expected from theory.
Kruskal’s algorithm showed slightly faster execution for the larger, sparser graph, while Prim’s algorithm was marginally faster on the smaller and denser one.

In practical terms:

* Kruskal’s algorithm is more straightforward to implement when the input is already an edge list.
* Prim’s algorithm is more suitable when the input is represented as an adjacency list or when the graph is dense.

Both have a theoretical time complexity of approximately **O(E log V)**, and their differences in runtime come mostly from constant factors and implementation details such as sorting and heap operations.

---

## **6. Conclusions**

Both algorithms are **correct and efficient** for finding the Minimum Spanning Tree in a transportation network.
They produced identical MSTs and total costs across all test cases.
The experimental results demonstrate the following conclusions:

1. **Kruskal’s algorithm** is preferable for **sparse graphs** or when the edge list is directly available, since it primarily depends on sorting edges.
2. **Prim’s algorithm** performs better on **dense graphs**, where a priority queue can effectively manage many adjacent edges.
3. In real-world applications such as road network optimization, the choice between these algorithms depends largely on how the data is stored and the density of connections between districts.
4. Both approaches are computationally efficient and suitable for large-scale graph optimization problems when implemented with proper data structures.

---

## **Summary**

Both Prim’s and Kruskal’s algorithms achieved the same total MST costs in all test cases, confirming correctness.
Kruskal’s algorithm was slightly faster for sparse graphs, while Prim’s performed marginally better for denser graphs.
Overall, both are highly effective for optimizing transportation networks and minimizing construction costs across connected districts.
