package project.controller.rede;

import project.domain.Local;
import project.domain.RedeHub;
import project.structure.MapGraph;

import java.util.ArrayList;
import java.util.LinkedList;

public class PercursoEntreLocaisController {

    private RedeHub rede;
    private MapGraph<Local, Integer> graph;

    public PercursoEntreLocaisController() {
        this.rede = RedeHub.getInstance();
        this.graph = rede.getRedeDistribuicao();
    }

    public ArrayList<LinkedList<Local>> getPathsBetweenLocations(String localOrigem, String localHub, double veloMedia, int autonomia) {
        Local org = getVertex(localOrigem);
        Local hub = getVertex(localHub);

        ArrayList<LinkedList<Local>> paths = rede.getPathsBetweenLocations(org, hub, autonomia);


       ArrayList<Integer> dists = new ArrayList<>();
        Integer distTotal = rede.calculateDistances(dists, paths);
        rede.filterPaths(paths, dists);
        Double tempoTotal = rede.calculateTotalTime(distTotal, veloMedia);

        return null;
    }


    private Local getVertex(String localOrigem) { /////ADAPTAR >>>>>>>>>>>>>>>>>>>>
        return graph.vertex(p -> p.getNumId().equals(localOrigem));
    }
}
