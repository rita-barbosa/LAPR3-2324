package project.domain;

import project.structure.Algorithms;
import project.structure.MapGraph;

import java.util.*;

public class RedeHub {
    private static final RedeHub instance = new RedeHub();

    final private MapGraph<Local, Integer> redeDistribuicao;

    private RedeHub() {
        this.redeDistribuicao = new MapGraph<>(false);
    }

    public static RedeHub getInstance() {
        return instance;
    }

    public void addHub(String numId, Double lat, Double lon) {
        Local vert = new Local(numId, lat, lon);
        redeDistribuicao.addVertex(vert);
    }

    public void addRoute(Local orig, Local dest, Integer distance) {
        redeDistribuicao.addEdge(orig, dest, distance);
    }

    @Override
    public String toString() {
        return redeDistribuicao.toString();
    }

    public MapGraph<Local, Integer> getRedeDistribuicao() {
        return redeDistribuicao;
    }


    public Map<Local, Integer> calculateInfluence(MapGraph<Local, Integer> graph) {
        Map<Local, Integer> influence = new HashMap<>();
        for (Local vertex : graph.vertices()) {
            influence.put(vertex, graph.outDegree(vertex));
        }

        List<Map.Entry<Local, Integer>> sortedInfluencies = new ArrayList<>(influence.entrySet());
        sortedInfluencies.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        Map<Local, Integer> sortedInfluence = new LinkedHashMap<>();

        // Colocar os valores no map
        for (Map.Entry<Local, Integer> entry : sortedInfluencies) {
            sortedInfluence.put(entry.getKey(), entry.getValue());
        }

        return sortedInfluence;
    }

    public Map<Local, Integer> calculateProximity(MapGraph<Local, Integer> graph) {
        Map<Local, Integer> proximity = new HashMap<>();

        for (Local vertex : graph.vertices()) {
            Integer proximityValue = calculateVertexProximity(graph, vertex);
            proximity.put(vertex, proximityValue);
        }

        List<Map.Entry<Local, Integer>> sortedProximities = new ArrayList<>(proximity.entrySet());
        sortedProximities.sort(Comparator.comparingInt(Map.Entry::getValue));

        Map<Local, Integer> sortedProximityMap = new LinkedHashMap<>();


        for (Map.Entry<Local, Integer> entry : sortedProximities) {
            sortedProximityMap.put(entry.getKey(), entry.getValue());
        }

        return sortedProximityMap;
    }

    private Integer calculateVertexProximity(MapGraph<Local, Integer> graph, Local vertex) {
        ArrayList<Integer> dists = new ArrayList<>();
        Algorithms.shortestPaths(graph, vertex, Comparator.naturalOrder(), Integer::sum, 0, new ArrayList<>(), dists);

        int proximitySum = 0;
        for (Integer dist : dists) {
            if (dist != null) {
                proximitySum += dist;
            }
        }

        return proximitySum;
    }

    public Map<Local, Integer> calculateCentrality(MapGraph<Local, Integer> graph) {
        Map<Local, Integer> centrality = Algorithms.betweennessCentrality(graph);

        List<Map.Entry<Local, Integer>> sortedCentralities = new ArrayList<>(centrality.entrySet());
        sortedCentralities.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        Map<Local, Integer> sortedCentrality = new LinkedHashMap<>();

        for (Map.Entry<Local, Integer> entry : sortedCentralities) {
            sortedCentrality.put(entry.getKey(), entry.getValue());
        }

        return sortedCentrality;
    }

    public Map<Local, Integer> getTopNHubs(Map<Local,Integer> map, Integer n){
        Map<Local, Integer> topNHubsMap = new LinkedHashMap<>();

        int count = 0;
        for (Map.Entry<Local, Integer> entry : map.entrySet()) {
            if (count < n) {
                topNHubsMap.put(entry.getKey(), entry.getValue());
                count++;
            } else {
                break;
            }
        }

        return topNHubsMap;
    }
}
