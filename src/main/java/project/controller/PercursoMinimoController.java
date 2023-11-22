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
        ArrayList<Integer> indexDeCarregamentos = new ArrayList<>();
        LinkedList<Local> percurso = getShortestPathForFurthestNodes();
        int distanciaPercorrida = 0, bateria=autonomia;
        boolean flag = true;
        for (int i = 0; i < percurso.size()-1; i++) {
            int distanciaEntrePontos = RedeHub.getInstance().getRedeDistribuicao().edge(percurso.get(i),percurso.get(i+1)).getWeight();
            distanciaPercorrida += distanciaEntrePontos;
            if(distanciaEntrePontos > bateria){
                if (distanciaEntrePontos <= autonomia){
                    indexDeCarregamentos.add(i);
                    bateria = autonomia;
                }else{
                    flag = false;
                }
            }else{
                bateria -= distanciaEntrePontos;
            }
        }
        return new EstruturaDeEntregaDeDados(distanciaPercorrida,percurso,indexDeCarregamentos,flag);
    }

    public static LinkedList<Local> getShortestPathForFurthestNodes() {
        int maxDist = 0;
        LinkedList<Local> tempPath = new LinkedList<>();
        for (Local local : RedeHub.getInstance().getRedeDistribuicao().vertices) {
            ArrayList<LinkedList<Local>> path = new ArrayList<>();
            ArrayList<Integer> dist = new ArrayList<>();

            shortestPaths(RedeHub.getInstance().getRedeDistribuicao(),local,Comparator.naturalOrder(), Integer::sum, 0,path,dist);
            if(dist.get(getBiggestDist(dist)) > maxDist){
                maxDist = dist.get(getBiggestDist(dist));
                tempPath = path.get(getBiggestDist(dist));
            }
        }
        return tempPath;
    }

    public static int getBiggestDist(ArrayList<Integer> dist){
        int temp = 0, index=0;
        for (int i = 0; i < dist.size(); i++) {
            if (dist.get(i) != null && temp < dist.get(i)){
                temp = dist.get(i);
                index=i;
            }
        }
        return index;
    }

}
