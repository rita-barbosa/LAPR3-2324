package project.ui;

import project.controller.ImportarFicheiroController;
import project.exception.ExcecaoFicheiro;

import java.io.*;
import java.util.*;

public class ImportarFicheiroUI implements Runnable {

    private final ImportarFicheiroController controller;

    private final String fileTypeName;

    public ImportarFicheiroUI(String fileType) {
        this.controller = new ImportarFicheiroController();
        fileTypeName = fileType;
    }

    @Override
    public void run() {
        String filepath = getfilepath();
        switch (fileTypeName){
            case "Plano de Rega":
                try {
                   controller.importWateringPlan(filepath);
                } catch (IOException | ExcecaoFicheiro e) {
                    throw new RuntimeException(e);
                }
                break;
            case "Ficheiro Legacy":
                /////
                break;
            default:
                break;
        }
    }

    private String getfilepath() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insira o caminho para o ficheiro:");
        return scanner.nextLine();
    }

}
