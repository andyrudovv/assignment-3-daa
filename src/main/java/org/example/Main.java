package org.example;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.example.GraphModels.*;

public class Main {

    public static void main(String[] args) throws Exception {
        String inputPath = args.length > 0 ? args[0] : "src/main/resources/ass_3_input.json";
        String outputPath = "ass_3_output.json";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = Files.readString(Paths.get(inputPath));
        JsonObject root = gson.fromJson(json, JsonObject.class);

        List<InputGraph> graphs = new ArrayList<>();
        for (JsonElement el : root.getAsJsonArray("graphs")) {
            graphs.add(gson.fromJson(el, InputGraph.class));
        }

        OpsCounter ops = new OpsCounter();
        List<Map<String, Object>> formattedResults = new ArrayList<>();

        for (InputGraph g : graphs) {
            System.out.println("\nGraph ID: " + g.id);
            System.out.println("Nodes: " + g.nodes.size() + ", Edges: " + g.edges.size());

            AlgoResult kr = KruskalAlgorithm.run(g, ops);
            AlgoResult pr = PrimAlgorithm.run(g, ops);

            System.out.printf("Kruskal: cost=%.2f, time=%.3f ms\n", kr.totalCost, kr.timeMs);
            System.out.printf("Prim:    cost=%.2f, time=%.3f ms\n", pr.totalCost, pr.timeMs);

            if (Math.abs(kr.totalCost - pr.totalCost) < 1e-6)
                System.out.println("✅ Both algorithms produced identical MST cost.");
            else
                System.out.println("⚠️ Costs differ! Check implementations.");

            // Format result for this graph
            Map<String, Object> graphResult = new LinkedHashMap<>();
            graphResult.put("graph_id", g.id);

            Map<String, Object> inputStats = new LinkedHashMap<>();
            inputStats.put("vertices", g.nodes.size());
            inputStats.put("edges", g.edges.size());
            graphResult.put("input_stats", inputStats);

            // Format Prim & Kruskal results
            graphResult.put("prim", formatAlgoResult(pr));
            graphResult.put("kruskal", formatAlgoResult(kr));

            formattedResults.add(graphResult);
        }

        Map<String, Object> output = new LinkedHashMap<>();
        output.put("results", formattedResults);

        Files.writeString(Paths.get(outputPath), gson.toJson(output));
        System.out.println("\n✅ Output written to " + outputPath);
    }

    private static Map<String, Object> formatAlgoResult(AlgoResult result) {
        Map<String, Object> algoMap = new LinkedHashMap<>();

        // Format edges as {"from", "to", "weight"}
        List<Map<String, Object>> edgeList = new ArrayList<>();
        for (Edge e : result.mstEdges) {
            Map<String, Object> edge = new LinkedHashMap<>();
            edge.put("from", e.u);
            edge.put("to", e.v);
            edge.put("weight", e.w);
            edgeList.add(edge);
        }

        algoMap.put("mst_edges", edgeList);
        algoMap.put("total_cost", result.totalCost);
        algoMap.put("operations_count", result.operations.values().stream().mapToLong(Long::longValue).sum());
        algoMap.put("execution_time_ms", result.timeMs);

        return algoMap;
    }
}
