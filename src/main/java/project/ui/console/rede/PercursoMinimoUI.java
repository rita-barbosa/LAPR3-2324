package project.ui.console.rede;

//import static project.controller.PercursoMinimoController.getShortestPathForFurthestNodes;
import project.controller.PercursoMinimoController;
import project.structure.EstruturaDeEntregaDeDados;

import java.util.Scanner;

import static project.controller.PercursoMinimoController.analyzeData;



public class PercursoMinimoUI implements Runnable{
    /**
     * Runs this operation.
     */
    private final Scanner read = new Scanner(System.in);
    private final PercursoMinimoController controller;

    public PercursoMinimoUI(PercursoMinimoController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        int autonomia=getAutonomy();
        System.out.println("|-------------------------------------------------------|");
        System.out.println("| Rota Eficiente com Paragens Mínimas para Carregamento |");
        System.out.println("|-------------------------------------------------------|");
        System.out.println(String.format("|                       AUTONOMIA: %7d (m)          |", autonomia));
        System.out.println("|-------------------------------------------------------|");
        EstruturaDeEntregaDeDados estruturaDeEntregaDeDados = analyzeData(autonomia);
        if(estruturaDeEntregaDeDados.isFlag()) {
            System.out.println(String.format("| Local Inicial: %11s | Local Final: %11s |", estruturaDeEntregaDeDados.getPercurso().get(0).getNumId(), estruturaDeEntregaDeDados.getPercurso().get(estruturaDeEntregaDeDados.getPercurso().size() - 1).getNumId()));
            System.out.println("|-------------------------------------------------------|");
            System.out.println("|                        Percurso:                      |");
            System.out.println("|-------------------------------------------------------|");
            System.out.println(String.format("|               Distância Total Percorrida: %6d (m)  |", estruturaDeEntregaDeDados.getDistanciaTotal()));
            System.out.println("|-------------------------------------------------------|");
            System.out.println(String.format("| %27s                           |",estruturaDeEntregaDeDados.getPercurso().get(0).getNumId()));
            for (int i = 1; i < estruturaDeEntregaDeDados.getPercurso().size(); i++) {
                System.out.println(String.format("| %26s                            |", "V"));
                System.out.println(String.format("| %27s                           |",estruturaDeEntregaDeDados.getPercurso().get(i).getNumId()));
            }
            System.out.println("|-------------------------------------------------------|");
            System.out.println("|                     Carregamentos:                    |");
            System.out.println("|-------------------------------------------------------|");
            for (int i = 0; i < estruturaDeEntregaDeDados.getCarregamentos().size(); i++) {
                System.out.println(String.format("| %27s                           |",estruturaDeEntregaDeDados.getPercurso().get(estruturaDeEntregaDeDados.getCarregamentos().get(i))));
            }
            System.out.println("|-------------------------------------------------------|");
            System.out.println(String.format("|                Numero De Carregamentos: %3d           |", estruturaDeEntregaDeDados.getCarregamentos().size()));
            System.out.println("|-------------------------------------------------------|");
        }else{
            System.out.println("|             A Autonomia Não É O Suficiente            |");
            System.out.println("|-------------------------------------------------------|");
        }
        System.out.println("");
        System.out.println("");
    }

    public int getAutonomy(){
        int returna;
        System.out.println("|-------------------------------------------------------|");
        System.out.println("|          Qual A Autonomia Do Veiculo? (Metros)        |");
        System.out.println("|-------------------------------------------------------|");
        returna = read.nextInt();
        System.out.println("|-------------------------------------------------------|");
        System.out.println("");
        System.out.println("");
        return returna;
    }
}
