package org.example;

import java.util.*;
import org.example.GraphModels.*;

public class KruskalAlgorithm {

    static class UnionFind {
        private final Map<String, String> parent = new HashMap<>();
        private final Map<String, Integer> rank = new HashMap<>();
        private final OpsCounter ops;

        UnionFind(Collection<String> nodes, OpsCounter ops) {
            this.ops = ops;
            for (String v : nodes) {
                parent.put(v, v);
                rank.put(v, 0);
            }
        }

        String find(String x) {
            // Defensive check to avoid NullPointerException
            if (!parent.containsKey(x)) {
                parent.put(x, x);
                rank.put(x, 0);
            }

            ops.finds++;
            if (!parent.get(x).equals(x))
                parent.put(x, find(parent.get(x)));
            return parent.get(x);
        }

        boolean union(String a, String b) {
            ops.unions++;
            String rootA = find(a);
            String rootB = find(b);
            if (rootA.equals(rootB)) return false;

            int rankA = rank.getOrDefault(rootA, 0);
            int rankB = rank.getOrDefault(rootB, 0);

            if (rankA < rankB) parent.put(rootA, rootB);
            else if (rankA > rankB) parent.put(rootB, rootA);
            else {
                parent.put(rootB, rootA);
                rank.put(rootA, rankA + 1);
            }
            return true;
        }
    }

    public static AlgoResult run(GraphModels.InputGraph g, OpsCounter ops) {
        ops.reset();
        long start = System.nanoTime();

        AlgoResult res = new AlgoResult();
        res.algorithm = "Kruskal";
        res.verticesCount = g.nodes.size();
        res.edgesCount = g.edges.size();

        // Build and sort edges
        List<Edge> edges = new ArrayList<>();
        for (InputEdge ie : g.edges)
            edges.add(new Edge(ie.from, ie.to, ie.weight));

        edges.sort((e1, e2) -> {
            ops.comparisons++;
            return Double.compare(e1.w, e2.w);
        });

        UnionFind uf = new UnionFind(g.nodes, ops);
        for (Edge e : edges) {
            ops.edgeChecks++;
            if (uf.union(e.u, e.v)) {
                res.mstEdges.add(e);
                res.totalCost += e.w;
                if (res.mstEdges.size() == g.nodes.size() - 1) break;
            }
        }

        res.timeMs = (System.nanoTime() - start) / 1_000_000.0;
        res.operations = ops.toMap();
        return res;
    }
}
