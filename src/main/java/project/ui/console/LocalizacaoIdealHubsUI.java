package project.ui.console;

import project.controller.LocalizacaoIdealHubsController;
import project.domain.Local;
import project.domain.RedeHub;
import project.structure.MapGraph;
import project.ui.console.utils.Utils;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LocalizacaoIdealHubsUI implements Runnable {

    private final LocalizacaoIdealHubsController controller;


    public LocalizacaoIdealHubsUI(){
        this.controller = new LocalizacaoIdealHubsController();
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        RedeHub redeHub = RedeHub.getInstance();
        MapGraph<Local, Integer> graph = redeHub.getRedeDistribuicao();

        String question = "Insira um valor N para o número de Hubs a serem selecionados:";
        int n;

        System.out.println("Choose an option:");
        System.out.println("1. Influence");
        System.out.println("2. Proximity");
        System.out.println("3. Centrality");
        System.out.println("4. TOP N Influence");
        System.out.println("5. TOP N Proximity");
        System.out.println("6. TOP N Centrality");
        System.out.println("7. FINAL");

        String option = scanner.nextLine();

        switch (option) {
            case "1":
                printOptimalHubs(redeHub.calculateInfluence(graph), option);
                break;
            case "2":
                printOptimalHubs(redeHub.calculateProximity(graph), option);
                break;
            case "3":
                printOptimalHubs(redeHub.calculateCentrality(graph), option);
                break;
            case "4":
                n = Utils.readIntegerFromConsole(question);
                printOptimalHubs(controller.getTopNInfluenceMap(graph, n), option);
                break;
            case "5":
                n = Utils.readIntegerFromConsole(question);
                printOptimalHubs(controller.getTopNProximityMap(graph, n), option);
                break;
            case "6":
                n = Utils.readIntegerFromConsole(question);
                printOptimalHubs(controller.getTopNCentralityMap(graph, n), option);
                break;
            case "7":
                n = Utils.readIntegerFromConsole(question);
                printTopNHubs(controller.getTopNTotal(graph, n), n);
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private void printTopNHubs(Map<Local, List<Integer>> hubs, Integer n){
        System.out.println("\n-----------------------------------------------------------------------------------------------------");
        System.out.println("|                                  Top " + n + " localidades para os Hubs:                                  |");
        System.out.println("-----------------------------------------------------------------------------------------------------");

        for (Map.Entry<Local, List<Integer>> entry : hubs.entrySet()) {
            Local hub = entry.getKey();
            List<Integer> values = entry.getValue();

            System.out.print("Localidade: " + hub.getNumId() + " | Horário: " + getHubOperatingHours(hub.getNumId()));
            System.out.print(" | Centrality: " + values.get(2));
            System.out.print(" | Influence: " + values.get(0));
            System.out.print(" | Proximity: " + values.get(1) + " m");
            System.out.println();
        }
    }

    private void printOptimalHubs(Map<Local, Integer> hubs, String option) {
        System.out.println("Optimal Hub Locations:");
        System.out.println("=====================");

        for (Map.Entry<Local, Integer> entry : hubs.entrySet()) {
            Local hub = entry.getKey();
            System.out.print("Localidade: " + hub.getNumId() + " | Horário: " + getHubOperatingHours(hub.getNumId()));

            if (option.equals("1") || option.equals("4")) {
                System.out.print(" | Influence: " + entry.getValue());
            }

            if (option.equals("2") || option.equals("5") ) {
                System.out.print(" | Proximity: " + entry.getValue());
            }

            if (option.equals("3") || option.equals("6") || option.equals("7")) {
                System.out.print(" | Centrality: " + entry.getValue());
            }
            System.out.println();
        }
    }

    /**
     * Gets the operating hours for a hub based on its identifier.
     *
     * @param numId the hub identifier
     * @return the operating hours as a String
     */
    private String getHubOperatingHours(String numId) {
        int num = Integer.parseInt(numId.substring(2));

        if (num >= 1 && num <= 105) {
            return "9h:00 – 14h:00";
        } else if (num >= 106 && num <= 215) {
            return "11h:00 – 16h:00";
        } else if (num >= 216 && num <= 323) {
            return "12h:00 – 17h:00";
        } else {
            return "Unknown operating hours";
        }
    }

}
