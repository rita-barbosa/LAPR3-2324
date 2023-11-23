package project.controller;

import project.domain.Local;
import project.structure.*;

import java.util.Comparator;

public class RedeLigacaoMinimaController {
    public MapGraph<Local, Integer> kruskallAlgorithm(MapGraph<Local, Integer> redeDistribuicao) {
        return MST.getMstWithKruskallAlgorithm(redeDistribuicao);
    }

    public <V extends CommonGraph> int getTotalWeightOfMinimumSpanningTree(V mst) {
        return MST.totalWeightMinimumSpanningTree(mst);
    }
}
