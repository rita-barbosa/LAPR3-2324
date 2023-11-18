package project.domain;

import project.structure.MapGraph;

public class RedeHub {
    private static final RedeHub instance = new RedeHub();

    final private MapGraph<Local, Integer> redeDistribuicao;

    private RedeHub() {
        this.redeDistribuicao = new MapGraph<>(false);
    }
    public static RedeHub getInstance() {
        return instance;
    }

    public boolean addHub(String numId, Double lat, Double lon) {
        Local vert = new Local(numId, lat, lon);
        return redeDistribuicao.addVertex(vert);
    }

    public boolean addRoute(Local orig, Local dest, Integer distance) {
        return redeDistribuicao.addEdge(orig,dest,distance);
    }

    @Override
    public String toString() {
        return redeDistribuicao.toString();
    }

    public MapGraph<Local, Integer> getRedeDistribuicao() {
        return redeDistribuicao;
    }
}
