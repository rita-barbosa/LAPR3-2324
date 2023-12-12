package project.domain;

import project.structure.Algorithms;
import project.structure.Edge;
import project.structure.EstruturaDeEntregaDeDados;
import project.structure.MapGraph;

import java.util.*;

import static project.structure.Algorithms.shortestPaths;

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

        return influence;
    }

    public Map<Local, Integer> calculateProximity(MapGraph<Local, Integer> graph) {
        Map<Local, Integer> proximity = new HashMap<>();

        for (Local vertex : graph.vertices()) {
            Integer proximityValue = calculateVertexProximity(graph, vertex);
            proximity.put(vertex, proximityValue);
        }

        return proximity;
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

        return centrality;
    }

    public Map<Local, List<Integer>> getTopNMap(Map<Local, List<Integer>> map, Integer n) {
        List<Map.Entry<Local, List<Integer>>> entries = new ArrayList<>(map.entrySet());
        entries.sort((entry1, entry2) -> {
            List<Integer> values1 = entry1.getValue();
            List<Integer> values2 = entry2.getValue();

            int compareCentrality = Integer.compare(values2.get(2), values1.get(2));
            if (compareCentrality != 0) {
                return compareCentrality;
            }

            int compareInfluence = Integer.compare(values2.get(0), values1.get(0));
            if (compareInfluence != 0) {
                return compareInfluence;
            }

            return Integer.compare(values1.get(1), values2.get(1));
        });

        Map<Local, List<Integer>> sortedFinalMap = new LinkedHashMap<>();
        for (Map.Entry<Local, List<Integer>> entry : entries) {
            sortedFinalMap.put(entry.getKey(), entry.getValue());
        }

        return getTopNHubs(sortedFinalMap, n);
    }

    public Map<Local, List<Integer>> getTopNHubs(Map<Local, List<Integer>> map, Integer n) {
        Map<Local, List<Integer>> topNHubsMap = new LinkedHashMap<>();

        int count = 0;
        for (Map.Entry<Local, List<Integer>> entry : map.entrySet()) {
            if (count < n) {
                topNHubsMap.put(entry.getKey(), entry.getValue());
                count++;
            } else {
                break;
            }
        }

        return topNHubsMap;
    }

    public static EstruturaDeEntregaDeDados analyzeData(int autonomia) {
        ArrayList<Integer> indexDeCarregamentos = new ArrayList<>();
        LinkedList<Local> percurso = getShortestPathForFurthestNodes();
        int distanciaPercorrida = 0, bateria = autonomia;
        boolean flag = true;
        for (int i = 0; i < percurso.size() - 1; i++) {
            int distanciaEntrePontos = instance.getRedeDistribuicao().edge(percurso.get(i), percurso.get(i + 1)).getWeight();
            distanciaPercorrida += distanciaEntrePontos;
            if (distanciaEntrePontos > bateria) {
                if (distanciaEntrePontos <= autonomia) {
                    indexDeCarregamentos.add(i);
                    bateria = autonomia;
                } else {
                    flag = false;
                }
            } else {
                bateria -= distanciaEntrePontos;
            }
        }
        return new EstruturaDeEntregaDeDados(distanciaPercorrida, percurso, indexDeCarregamentos, flag);
    }

    public static LinkedList<Local> getShortestPathForFurthestNodes() {
        int maxDist = 0;
        LinkedList<Local> tempPath = new LinkedList<>();
        for (Local local : instance.getRedeDistribuicao().vertices) {
            ArrayList<LinkedList<Local>> path = new ArrayList<>();
            ArrayList<Integer> dist = new ArrayList<>();

            shortestPaths(instance.getRedeDistribuicao(), local, Comparator.naturalOrder(), Integer::sum, 0, path, dist);
            if (dist.get(getBiggestDist(dist)) > maxDist) {
                maxDist = dist.get(getBiggestDist(dist));
                tempPath = path.get(getBiggestDist(dist));
            }
        }
        return tempPath;
    }

    public static int getBiggestDist(ArrayList<Integer> dist) {
        int temp = 0, index = 0;
        for (int i = 0; i < dist.size(); i++) {
            if (dist.get(i) != null && temp < dist.get(i)) {
                temp = dist.get(i);
                index = i;
            }
        }
        return index;
    }

    public ArrayList<LinkedList<Local>> getPathsBetweenLocations(Local org, Local hub, int autonomia) {
        return Algorithms.allPaths(redeDistribuicao, org, hub);
    }

    public Integer calculateDistances(ArrayList<Integer> dists, ArrayList<LinkedList<Local>> paths) {
        int defaultValue = -1;
        int distTotal = 0;
        int d;
        ArrayList<Integer> distanciasPercurso = new ArrayList<>();


        /// COLOCAR ARRAYLIST<ARRAYLISTS> (+-) QUE GUARDA AS LISTAS DAS DISTANCIAS PARA CADA PERCURSO ONDE NA POSIÇÃO 0 HÁ SEMPRE A DISTANCIA TOTAL


        for (LinkedList<Local> list : paths) {
            for (int i = 0; i < list.size(); i++) {
                Edge<Local, Integer> e = redeDistribuicao.edge(list.get(i), list.get(i + 1));
                if (e != null) {
                    d = e.getWeight();
                    distTotal += d;
                    distanciasPercurso.add(d);
                } else {
                    distanciasPercurso.add(defaultValue);
                }
            }

        }
        return distTotal;
    }

    public Double calculateTotalTime(Integer distTotal, double veloMedia) {
        return (distTotal / veloMedia);
    }

    public void filterPaths(ArrayList<LinkedList<Local>> paths, ArrayList<Integer>  d) {

    }
}
