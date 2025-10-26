package org.example;

import java.util.*;

public class GraphModels {

    public static class InputGraph {
        public int id;
        public List<String> nodes;
        public List<InputEdge> edges;
    }

    public static class InputEdge {
        public String from;
        public String to;
        public double weight;
    }

    public static class Edge {
        public String u, v;
        public double w;
        public Edge(String u, String v, double w) {
            this.u = u;
            this.v = v;
            this.w = w;
        }
        @Override
        public String toString() { return u + " - " + v + " (" + w + ")"; }
    }

    public static class AlgoResult {
        public String algorithm;
        public List<Edge> mstEdges = new ArrayList<>();
        public double totalCost;
        public int verticesCount;
        public int edgesCount;
        public Map<String, Long> operations;
        public double timeMs;
    }

    public static class OpsCounter {
        public long comparisons = 0;
        public long pqOps = 0;
        public long unions = 0;
        public long finds = 0;
        public long edgeChecks = 0;

        public void reset() { comparisons = pqOps = unions = finds = edgeChecks = 0; }

        public Map<String, Long> toMap() {
            Map<String, Long> m = new LinkedHashMap<>();
            m.put("comparisons", comparisons);
            m.put("pq_operations", pqOps);
            m.put("unions", unions);
            m.put("finds", finds);
            m.put("edge_checks", edgeChecks);
            return m;
        }
    }
}
