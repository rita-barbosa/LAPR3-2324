package project.controller;

import org.codehaus.plexus.util.dag.Vertex;
import project.domain.Local;
import project.domain.RedeHub;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class PercursoMinimoController {

//    public static String getShortestPathForFurthestNodes() {
//        int maxDist = 0;
//        Local tempLocalInicio = null,tempLocalFim = null;
//
//        for (Local local : RedeHub.getInstance().getRedeDistribuicao().vertices) {
//            boolean[] visited = new boolean[RedeHub.getInstance().getRedeDistribuicao().numVertices()];
//            Local[] path = new Local[RedeHub.getInstance().getRedeDistribuicao().numVertices()];
//            Integer[] dist = new Integer[RedeHub.getInstance().getRedeDistribuicao().numVertices()];
//            shortestPathDijkstra(RedeHub.getInstance().getRedeDistribuicao(), local, Comparator.naturalOrder(),Integer::sum,0,visited,path,dist);
//            if (maxDist < dist[getBiggestDist(dist)]){
//                maxDist = dist[getBiggestDist(dist)];
//                tempLocalInicio = local;
//                tempLocalFim = path[getBiggestDist(dist)];
//                Local[] tempPath = path;
//            }
//        }
//    }
//
//    public static int getBiggestDist(Integer[] dist){
//        int temp = 0;
//        for (int i = 0; i < dist.length; i++) {
//            if (temp < dist[i]){
//                temp = i;
//            }
//        }
//        return temp;
//    }

}
