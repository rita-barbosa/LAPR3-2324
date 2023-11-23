package project.controller;

import org.codehaus.plexus.util.dag.Vertex;
import project.domain.Local;
import project.domain.RedeHub;
import project.structure.Algorithms;
import project.structure.EstruturaDeEntregaDeDados;

import java.lang.reflect.Array;
import java.util.*;

import static project.structure.Algorithms.shortestPathDijkstra;
import static project.structure.Algorithms.shortestPaths;

public class PercursoMinimoController {

    public static EstruturaDeEntregaDeDados analyzeData(int autonomia){
        return RedeHub.analyzeData(autonomia);
    }

}
