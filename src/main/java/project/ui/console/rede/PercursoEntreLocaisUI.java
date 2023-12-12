package project.ui.console.rede;

import project.controller.rede.PercursoEntreLocaisController;
import project.domain.Local;
import project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.LinkedList;

public class PercursoEntreLocaisUI implements Runnable {
    /*
     local de origem
     hub
     Kms de autonomia
     velocidade média
       */
    private PercursoEntreLocaisController controller;

    private int autonomia;
    private double veloMedia;
    private String localOrigem;
    private String hub;

    public PercursoEntreLocaisUI() {
        this.controller = new PercursoEntreLocaisController();
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        autonomia = Utils.readIntegerFromConsole("Indique a autonomia do veículo:");
        veloMedia = Utils.readDoubleFromConsole("Indique a velocidade média de deslocamente:");
        localOrigem = Utils.readLineFromConsole("Indique o local de origem do percurso:");
        hub = Utils.readLineFromConsole("Indique o hub que pretende alcançar:");

        ArrayList<LinkedList<Local>> a  = controller.getPathsBetweenLocations(localOrigem,hub,veloMedia,autonomia);
    }
}
