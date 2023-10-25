package project.ui;

import project.controller.ImportarFicheiroController;
import project.exception.ExcecaoFicheiro;

import java.io.*;
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
        String filepath = getfilepath();
        String wasItSuccess = "";
        switch (fileTypeName){
            case "Plano de Rega":
                wasItSuccess = controller.importWateringPlan(filepath);
                successfulImport = wasItSuccess.equals("Success");
                break;
            case "Ficheiro Legacy":
                /////
                break;
            default:
                break;
        }
        checkIfImportWasASuccess(successfulImport, fileTypeName, wasItSuccess);
    }

    private void checkIfImportWasASuccess(boolean successfulImport, String fileTypeName, String exceptionMessage) {
        if (successfulImport){
            System.out.printf("O ficheiro %s foi importado com sucesso.\n\n", fileTypeName);
        }else{
            if (!exceptionMessage.isEmpty() || !exceptionMessage.isBlank()){
                System.out.println(exceptionMessage);
            }
            System.out.printf("O ficheiro %s não foi importado.\n\n", fileTypeName);
        }
    }

    private String getfilepath() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insira o caminho para o ficheiro:");
        return scanner.nextLine();
    }

}
