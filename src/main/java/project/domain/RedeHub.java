package project.domain;

import project.structure.MapGraph;

public class RedeHub {
    private static final RedeHub instance = new RedeHub();

    final private MapGraph<Hub, Integer> redeDistribuicao;

    private RedeHub() {
        this.redeDistribuicao = new MapGraph<>(false);
    }
    public static RedeHub getInstance() {
        return instance;
    }

    public boolean addHub(String numId, Double lat, Double lon) {
        Hub vert = new Hub(numId, lat, lon);
        return redeDistribuicao.addVertex(vert);
    }

    public boolean addRoute(Hub orig, Hub dest, Integer distance) {
        return redeDistribuicao.addEdge(orig,dest,distance);
    }

    @Override
    public String toString() {
        return redeDistribuicao.toString();
    }
}
