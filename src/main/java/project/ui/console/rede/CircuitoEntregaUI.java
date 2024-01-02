package project.ui.console.rede;

import project.controller.rede.CircuitoEntregaController;
import project.domain.Local;
import project.domain.RedeHub;
import project.structure.MapGraph;
import project.ui.console.utils.Utils;

import java.util.*;

public class CircuitoEntregaUI implements Runnable {

    private final CircuitoEntregaController controller = new CircuitoEntregaController();

    private int autonomia;
    private double velocidadeMedia;
    private String localOrigem;
    private int n;
    private int tempoRecarga;
    private int tempoDescarga;

    @Override
    public void run(){
        try {
            RedeHub rede = RedeHub.getInstance();
            MapGraph<Local, Integer> graph = rede.getRedeDistribuicao();

            boolean validLocal = false;
            while (!validLocal){
                localOrigem = Utils.readLineFromConsole("Indique o local de origem do percurso: (EX: CTXX)");
                for (Local vertex : graph.vertices()) {
                    if (vertex.hasNumId(localOrigem)) {
                        validLocal = true;
                        break;
                    }
                }

                if (!validLocal){
                    System.out.println("O Local introduzido não é válido!");
                }
            }

            n = Utils.readIntegerFromConsole("Indique o N desejado:");
            autonomia = Utils.readIntegerFromConsole("Indique a autonomia do veículo (km):") * 1000;
            velocidadeMedia = Utils.readDoubleFromConsole("Indique a velocidade média de deslocamente (km/h):");

            tempoRecarga = Utils.readIntegerFromConsole("Qual O Tempo De Recarga Do Veiculo? (Min):");
            tempoDescarga = Utils.readIntegerFromConsole("Qual O Tempo De Descarga De Cabazes Do Veiculo? (Min):");


            List<Local> locaisCarregamento = new ArrayList<>();
            LinkedList<Integer> distancias = new LinkedList<>();

            List<Local> result = controller.nearestResult(localOrigem, n, autonomia, locaisCarregamento, distancias);


            if (result.size() <= 1){
                printWrongCase();
            } else {
                LinkedList<Integer> temposPercurso = controller.getTempoTotalPercurso(distancias, velocidadeMedia, locaisCarregamento, tempoRecarga, tempoDescarga, n);

                Map<Local, Integer> numeroColaboradores = controller.calculateNumberCollaborators(n, localOrigem);

                int totalColaboradores = controller.getTotalCollaborators(numeroColaboradores);

                printPath(result, distancias, totalColaboradores, locaisCarregamento, temposPercurso, numeroColaboradores);
            }

        } catch (Exception e) {
            System.out.println("ERRO: Não foi possível executar a funcionalidade.");
        }
    }

    private void printPath(List<Local> locals, LinkedList<Integer> integers, Integer totalColaboradores, List<Local> locaisCarregamento, List<Integer> temposPercurso, Map<Local, Integer> hubsMap) {
        System.out.println("\n|-----------------------------------------------------------------------------------------------------|");
        System.out.println("|                                               CIRCUITO:                                             |");
        System.out.println("|-----------------------------------------------------------------------------------------------------|");
        System.out.printf("| LOCAL DE ORIGEM: %-83s|\n", locals.get(0).getNumId());
        System.out.printf("| DISTÂNCIA TOTAL: %-83s|\n", integers.getLast() + " m");
        System.out.println("|-----------------------------------------------------------------------------------------------------|");
        System.out.printf("|%37s  <>  %-37s| %-19s|\n", "DE", "PARA", "DISTÂNCIAS (m)");
        if (locals.size() > 1) {
            for (int i = 0; i < locals.size() - 1; i++) {
                String format2 = "|%37s  <>  %-37s| %-19d|\n";
                System.out.printf(format2, locals.get(i), locals.get(i + 1), integers.get(i));
            }
        }
        System.out.println("|-----------------------------------------------------------------------------------------------------|");
        System.out.println("|                                    HUBS QUE CONTRIBUEM PARA OS N:                                   |");
        System.out.println("|-----------------------------------------------------------------------------------------------------|");
        for (Map.Entry<Local, Integer> entry : hubsMap.entrySet()) {
            System.out.printf("| %-13s -> %-4d colaboradores                                                                 |\n", entry.getKey(), entry.getValue());
        }
        System.out.printf("| NÚMERO COLABORADORES: %-78d|\n", totalColaboradores);
        System.out.println("|-----------------------------------------------------------------------------------------------------|");
        System.out.println("|                                        LOCAIS DE CARREGAMENTO:                                      |");
        System.out.println("|-----------------------------------------------------------------------------------------------------|");
        int numeroCarregamentos = 0;
        for (Local l : locaisCarregamento) {
            System.out.printf("| %-73s                           |\n", l.getNumId());
            numeroCarregamentos++;
        }
        System.out.printf("| NUMERO CARREGAMENTOS: %-78d|\n", numeroCarregamentos);
        System.out.println("|-----------------------------------------------------------------------------------------------------|");
        System.out.println("|                                             TEMPO GASTO:                                            |");
        System.out.println("|-----------------------------------------------------------------------------------------------------|");
        System.out.printf("| TEMPO TOTAL: %-87s|\n", temposPercurso.get(3) + " min");
        System.out.printf("| TEMPO CARREGAMENTO VEÍCULO: %-72s|\n", temposPercurso.get(1) + " min");
        System.out.printf("| TEMPO PERCURSO: %-84s|\n", temposPercurso.get(0) + " min");
        System.out.printf("| TEMPO DESCARGA CESTOS: %-77s|\n", temposPercurso.get(2) + " min");
        System.out.println("|-----------------------------------------------------------------------------------------------------|\n");
    }

    private void printWrongCase(){
        System.out.println("\n|-----------------------------------------------------------------------------------------------------|");
        System.out.println("|                                                                                                     |");
        System.out.println("|                               NÃO É POSSÍVEL ENCONTRAR UM CIRCUITO                                  |");
        System.out.println("|                                   PARA OS PARÂMETROS DEFINIDOS                                      |");
        System.out.println("|                                                                                                     |");
        System.out.println("|-----------------------------------------------------------------------------------------------------|\n");
    }

}
