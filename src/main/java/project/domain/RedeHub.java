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


    //método para calcular a influência
    public static Map<Local, Integer> calculateInfluence(MapGraph<Local, Integer> graph) {
        Map<Local, Integer> influence = new LinkedHashMap<>(); //colocar como treeMap para depois fazer a ordenação decrescente
        for (Local vertex : graph.vertices()) {
            influence.put(vertex, graph.outDegree(vertex));
        }

        List<Map.Entry<Local, Integer>> sortedInfluencies = new ArrayList<>(influence.entrySet());
        sortedInfluencies.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        Map<Local, Integer> sortedInfluence = new LinkedHashMap<>(); // Para ser mantida a ordem dos elementos

        // Colocar os valores no map
        for (Map.Entry<Local, Integer> entry : sortedInfluencies) {
            sortedInfluence.put(entry.getKey(), entry.getValue());
        }

        return sortedInfluence;
    }

    public static Map<Local, Integer> calculateProximity(MapGraph<Local, Integer> graph) {
        Map<Local, Integer> proximity = new HashMap<>();

        for (Local vertex : graph.vertices()) {
            int somaDistancias = 0;
            for (Local destination : graph.vertices()) {
                if (!vertex.equals(destination)) {
                    int distancia = calculateDistance(graph, vertex, destination);
                    somaDistancias += distancia;
                }
            }
            proximity.put(vertex, somaDistancias);
        }

        //transformar em lista e ordenar se for preciso!!!! --> não tem nos critérios de aceitação
        return proximity;
    }

    private static int calculateDistance(MapGraph<Local, Integer> graph, Local vertex, Local destination) {
        LinkedList<Local> shortestPath = new LinkedList<>();
        Integer distancia = Algorithms.shortestPath(graph, vertex, destination, Integer::compareTo, Integer::sum, 0, shortestPath);

        return distancia != null ? distancia : Integer.MAX_VALUE;
    }

    public static Map<Local, Integer> calculateCentrality(MapGraph<Local, Integer> graph) {
        Map<Local, Integer> centrality = Algorithms.betweennessCentrality(graph);

        // Converte a lista para depois ordenar
        List<Map.Entry<Local, Integer>> sortedCentralities = new ArrayList<>(centrality.entrySet());
        sortedCentralities.sort((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()));

        Map<Local, Integer> sortedCentrality = new LinkedHashMap<>(); // Para ser mantida a ordem dos elementos

        // Colocar os valores no map
        for (Map.Entry<Local, Integer> entry : sortedCentralities) {
            sortedCentrality.put(entry.getKey(), entry.getValue());
        }

        return sortedCentrality;
    }
}
