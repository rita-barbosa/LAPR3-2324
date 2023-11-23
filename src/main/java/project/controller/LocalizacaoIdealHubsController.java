package project.controller;

import project.domain.Local;
import project.domain.RedeHub;
import project.structure.MapGraph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LocalizacaoIdealHubsController {

    public Map<Local, Integer> getTopNInfluenceMap(MapGraph<Local, Integer> graph, Integer n){
        RedeHub redeHub = RedeHub.getInstance();
        Map<Local, Integer> influence = redeHub.calculateInfluence(graph);

        return redeHub.getTopNHubs(influence, n);
    }

    public Map<Local, Integer> getTopNProximityMap(MapGraph<Local, Integer> graph, Integer n){
        RedeHub redeHub = RedeHub.getInstance();
        Map<Local, Integer> proximity = redeHub.calculateProximity(graph);

        return redeHub.getTopNHubs(proximity, n);
    }

    public Map<Local, Integer> getTopNCentralityMap(MapGraph<Local, Integer> graph, Integer n){
        RedeHub redeHub = RedeHub.getInstance();
        Map<Local, Integer> centrality = redeHub.calculateCentrality(graph);

        return redeHub.getTopNHubs(centrality, n);
    }

    public Map<Local, List<Integer>> getTopNTotal(MapGraph<Local,Integer> graph, Integer n){
        RedeHub redeHub = RedeHub.getInstance();
        Map<Local, Integer> influence = redeHub.calculateInfluence(graph);
        Map<Local, Integer> proximity = redeHub.calculateProximity(graph);
        Map<Local, Integer> centrality = redeHub.calculateCentrality(graph);
        Map<Local, List<Integer>> finalMap = new LinkedHashMap<>();
        // Preencher o finalMap com os valores de influence, proximity e centrality
        for (Local key : influence.keySet()) {
            List<Integer> values = new ArrayList<>();
            values.add(influence.get(key)); // Adiciona o valor da influence
            values.add(proximity.get(key)); // Adiciona o valor da proximity
            values.add(centrality.get(key)); // Adiciona o valor da centrality
            finalMap.put(key, values); // Coloca a lista de valores no finalMap
        }

        // Ordenar o finalMap de acordo com os critérios especificados
        List<Map.Entry<Local, List<Integer>>> entries = new ArrayList<>(finalMap.entrySet());
        entries.sort((entry1, entry2) -> {
            List<Integer> values1 = entry1.getValue();
            List<Integer> values2 = entry2.getValue();

            // Ordenar por centrality (decrescente)
            int compareCentrality = Integer.compare(values2.get(2), values1.get(2));
            if (compareCentrality != 0) {
                return compareCentrality;
            }

            // Se centrality for igual, ordenar por influence (decrescente)
            int compareInfluence = Integer.compare(values2.get(0), values1.get(0));
            if (compareInfluence != 0) {
                return compareInfluence;
            }

            // Se influence também for igual, ordenar por proximity (crescente)
            return Integer.compare(values1.get(1), values2.get(1));
        });

        // Criar um novo LinkedHashMap ordenado
        Map<Local, List<Integer>> sortedFinalMap = new LinkedHashMap<>();
        for (Map.Entry<Local, List<Integer>> entry : entries) {
            sortedFinalMap.put(entry.getKey(), entry.getValue());
        }

        return sortedFinalMap;
    }

//    public Map<Local, Integer> getTopNHubs(Map<Local, Integer> map, Integer n){
//        return RedeHub.getTopNHubs(map, n);
//    }




}
