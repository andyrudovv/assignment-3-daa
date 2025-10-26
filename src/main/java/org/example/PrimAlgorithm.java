package org.example;

import java.util.*;
import org.example.GraphModels.*;

public class PrimAlgorithm {

    public static AlgoResult run(GraphModels.InputGraph g, OpsCounter ops) {
        ops.reset();
        long start = System.nanoTime();

        AlgoResult res = new AlgoResult();
        res.algorithm = "Prim";
        res.verticesCount = g.nodes.size();
        res.edgesCount = g.edges.size();

        // Build adjacency list
        Map<String, List<Edge>> adj = new HashMap<>();
        for (String v : g.nodes) adj.put(v, new ArrayList<>());

        for (InputEdge e : g.edges) {
            adj.get(e.from).add(new Edge(e.from, e.to, e.weight));
            adj.get(e.to).add(new Edge(e.to, e.from, e.weight));
        }

        String startNode = g.nodes.get(0);
        Set<String> visited = new HashSet<>();
        visited.add(startNode);

        class PQEdge {
            String from, to;
            double weight;
            PQEdge(String f, String t, double w) { from = f; to = t; weight = w; }
        }

        Comparator<PQEdge> cmp = (a, b) -> {
            ops.comparisons++;
            return Double.compare(a.weight, b.weight);
        };

        PriorityQueue<PQEdge> pq = new PriorityQueue<>(cmp);
        for (Edge e : adj.get(startNode)) {
            pq.add(new PQEdge(e.u, e.v, e.w));
            ops.pqOps++;
        }

        while (!pq.isEmpty() && res.mstEdges.size() < g.nodes.size() - 1) {
            PQEdge cur = pq.poll();
            ops.pqOps++;
            if (visited.contains(cur.to)) continue;

            res.mstEdges.add(new Edge(cur.from, cur.to, cur.weight));
            res.totalCost += cur.weight;
            visited.add(cur.to);

            for (Edge next : adj.get(cur.to)) {
                ops.edgeChecks++;
                if (!visited.contains(next.v)) {
                    pq.add(new PQEdge(next.u, next.v, next.w));
                    ops.pqOps++;
                }
            }
        }

        res.timeMs = (System.nanoTime() - start) / 1_000_000.0;
        res.operations = ops.toMap();
        return res;
    }
}
