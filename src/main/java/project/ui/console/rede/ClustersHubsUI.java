package project.ui.console.rede;

import project.controller.rede.ClustersHubsController;
import project.domain.Local;
import project.domain.RedeHub;
import project.structure.MapGraph;
import project.ui.console.utils.Utils;


public class ClustersHubsUI implements Runnable {
    private final ClustersHubsController controller = new ClustersHubsController();


    @Override
    public void run() {
       int numClusters = Utils.readIntegerFromConsole("Qual o número de clusters que deseja?");

        RedeHub redeHub = RedeHub.getInstance();
        MapGraph<Local, Integer> graph = redeHub.getRedeDistribuicao();



    }



}
