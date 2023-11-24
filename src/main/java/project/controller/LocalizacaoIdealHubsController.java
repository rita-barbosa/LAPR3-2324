package project.controller;

import project.domain.Local;
import project.domain.RedeHub;
import project.structure.MapGraph;

import java.util.*;

public class LocalizacaoIdealHubsController {

    public Map<Local, Integer> getTopNInfluenceMap(MapGraph<Local, Integer> graph, Integer n){
        RedeHub redeHub = RedeHub.getInstance();
        Map<Local, Integer> influence = redeHub.calculateInfluence(graph);

        return redeHub.getTopNHubsSeparate(influence, n);
    }

    public Map<Local, Integer> getTopNProximityMap(MapGraph<Local, Integer> graph, Integer n){
        RedeHub redeHub = RedeHub.getInstance();
        Map<Local, Integer> proximity = redeHub.calculateProximity(graph);

        return redeHub.getTopNHubsSeparate(proximity, n);
    }

    public Map<Local, Integer> getTopNCentralityMap(MapGraph<Local, Integer> graph, Integer n){
        RedeHub redeHub = RedeHub.getInstance();
        Map<Local, Integer> centrality = redeHub.calculateCentrality(graph);

        return redeHub.getTopNHubsSeparate(centrality, n);
    }

    public Map<Local, List<Integer>> getTopNTotal(MapGraph<Local,Integer> graph, Integer n){
        RedeHub redeHub = RedeHub.getInstance();
        Map<Local, Integer> influence = redeHub.calculateInfluence(graph);
        Map<Local, Integer> proximity = redeHub.calculateProximity(graph);
        Map<Local, Integer> centrality = redeHub.calculateCentrality(graph);
        Map<Local, List<Integer>> finalMap = new HashMap<>();
        for (Local key : influence.keySet()) {
            List<Integer> values = new ArrayList<>();
            values.add(influence.get(key));
            values.add(proximity.get(key));
            values.add(centrality.get(key));
            finalMap.put(key, values);
        }

        return redeHub.getTopNMap(finalMap, n);
    }
}
