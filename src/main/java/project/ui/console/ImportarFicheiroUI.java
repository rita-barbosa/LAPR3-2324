package project.ui.console;

import project.controller.ImportarFicheiroController;
import project.domain.Horario;
import project.domain.ImportarFicheiro;
import project.domain.Local;
import project.domain.RedeHub;
import project.exception.ExcecaoFicheiro;
import project.structure.MapGraph;

import java.util.*;

public class ImportarFicheiroUI implements Runnable {

    private final ImportarFicheiroController controller;
    private final String fileTypeName;
    private boolean successfulImport = false;

    public ImportarFicheiroUI(String fileType) {
        this.controller = new ImportarFicheiroController();
        fileTypeName = fileType;
    }

    @Override
    public void run() {
        switch (fileTypeName) {
            case "Plano de Rega":
                try {
                    System.out.println("Insira o caminho para o ficheiro:");
                    String filepath = getfilepath();

                    successfulImport = controller.importWateringPlan(filepath);
                } catch (Exception e) {
                    System.out.printf("%s\n\n", e.getMessage());
                }
                break;
            case "Rede Distruição":
                try {
                    System.out.println("Insira o caminho para o ficheiro dos locais:");
                    String locaisFilePath = getfilepath();
                    ExcecaoFicheiro.verificarFicheiro(locaisFilePath, ".csv");

                    System.out.println("Insira o caminho para o ficheiro das distâncias:");
                    String distanciaFilePath = getfilepath();
                    ExcecaoFicheiro.verificarFicheiro(distanciaFilePath, ".csv");

                    successfulImport = controller.importRedeDistribuicao(locaisFilePath, distanciaFilePath);
                    RedeHub rede = RedeHub.getInstance();
                    System.out.println(rede.toString());
                } catch (Exception e) {
                    System.out.printf("%s\n\n", e.getMessage());
                }
                break;
            case "Novos Horários":
                try {
                    System.out.println("Insira o caminho para o ficheiro dos horários:");
                    String horariosFilePath = getfilepath();
                    ExcecaoFicheiro.verificarFicheiro(horariosFilePath, ".csv");
                    Map<String, Horario> novosHorarios = ImportarFicheiro.importarFicheiroHorarios(horariosFilePath);
                    RedeHub rede = RedeHub.getInstance();
                    MapGraph<Local, Integer> graph = rede.getRedeDistribuicao();
                    for (String hubId : novosHorarios.keySet()) {
                        boolean hubExiste = false;
                        for (Local vertex : graph.vertices()) {
                            if (vertex.getNumId().equals(hubId)) {
                                Horario novoHorario = novosHorarios.get(hubId);
                                vertex.setHorario(novoHorario);
                                System.out.println("Horários redefinidos para o hub " + hubId);
                                hubExiste = true;
                                break;
                            }
                        }
                        if (!hubExiste) {
                            System.out.println("Hub " + hubId + " não encontrado!");
                        }
                    }
                    System.out.println();
                    successfulImport = true;

                    //Apenas para verificação se os horários mudaram ---> RETIRAR
                    System.out.println("Novos horários dos hubs:");
                    for (Local entry : graph.vertices) {
                        System.out.println("Hub: " + entry.getNumId() + " - Horário: " + entry.getHorario() + " Hub: " + entry.isHub());
                    }
                } catch (Exception e) {
                    System.out.printf("%s\n\n", e.getMessage());
                }
                break;
            default:
                break;
        }
        checkIfImportWasASuccess(successfulImport, fileTypeName);
    }

    private void checkIfImportWasASuccess(boolean successfulImport, String fileTypeName) {
        if (successfulImport) {
            System.out.printf("O ficheiro %s foi importado com sucesso.\n\n", fileTypeName);
        } else {
            System.out.printf("O ficheiro %s não foi importado.\n\n", fileTypeName);
        }
    }

    private String getfilepath() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

}
