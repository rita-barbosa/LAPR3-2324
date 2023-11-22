package project.ui.console;

//import static project.controller.PercursoMinimoController.getShortestPathForFurthestNodes;
import project.controller.PercursoMinimoController;
import project.structure.EstruturaDeEntregaDeDados;

import java.util.Scanner;

import static project.controller.PercursoMinimoController.analyzeData;
import static project.controller.PercursoMinimoController.getShortestPathForFurthestNodes;


public class PercursoMinimoUI implements Runnable{
    /**
     * Runs this operation.
     */

    private final PercursoMinimoController controller;

    public PercursoMinimoUI(PercursoMinimoController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| Rota Eficiente com Paragens Mínimas para Carregamento |");
        System.out.println("|-------------------------------------------------------|");
        System.out.print("|                       AUTONOMIA: ");
        int autonomia = read.nextInt();
        System.out.println("                     |");
        System.out.println("|-------------------------------------------------------|");
        EstruturaDeEntregaDeDados estruturaDeEntregaDeDados = analyzeData(autonomia);
        if(estruturaDeEntregaDeDados.isFlag()) {
            System.out.println(String.format("| Local Inicial: %11s | Local Final: %11s |", estruturaDeEntregaDeDados.getPercurso().get(0), estruturaDeEntregaDeDados.getPercurso().get(estruturaDeEntregaDeDados.getPercurso().size() - 1)));
            System.out.println("|-------------------------------------------------------|");
            System.out.println("|                        Percurso:                      |");
            System.out.println("|-------------------------------------------------------|");
            System.out.println(String.format("|               Distância Total Percorrida: %6d      |", estruturaDeEntregaDeDados.getDistanciaTotal()));
            System.out.println("|-------------------------------------------------------|");
            System.out.println("| ");
            for (int i = 0; i < estruturaDeEntregaDeDados.getPercurso().size(); i++) {
                System.out.println(estruturaDeEntregaDeDados.getPercurso().get(i) + " ");
                if (i % 11 == 0) {
                    System.out.println("|");
                    System.out.println("");
                    System.out.println("|");
                }
            }
            System.out.println("|-------------------------------------------------------|");
            System.out.println("|                     Carregamentos:                    |");
            System.out.println("|-------------------------------------------------------|");
            System.out.println("| ");
            for (int i = 0; i < estruturaDeEntregaDeDados.getCarregamentos().size(); i++) {
                System.out.println(estruturaDeEntregaDeDados.getPercurso().get(estruturaDeEntregaDeDados.getCarregamentos().get(i)) + " ");
                if (i % 11 == 0) {
                    System.out.println("|");
                    System.out.println();
                    System.out.println("|");
                }
            }
            System.out.println("|-------------------------------------------------------|");
            System.out.println(String.format("|                Numero De Carregamentos: %3d           |", estruturaDeEntregaDeDados.getCarregamentos().size()));
            System.out.println("|-------------------------------------------------------|");
        }else{
            System.out.println("|             A Autonomia Não É O Suficiente            |");
            System.out.println("|-------------------------------------------------------|");
        }
    }

    public Scanner read = new Scanner(System.in);
}
