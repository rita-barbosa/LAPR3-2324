package project.ui;

import project.controller.ImportarFicheiroController;

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
        switch (fileTypeName){
            case "Plano de Rega":
                try{
                    successfulImport = controller.importWateringPlan(filepath);
                }catch (Exception e){
                    System.out.printf("%s\n\n", e.getMessage());
                }
                break;
            case "Any Other File":
                /////
                break;
            default:
                break;
        }
        checkIfImportWasASuccess(successfulImport, fileTypeName);
    }

    private void checkIfImportWasASuccess(boolean successfulImport, String fileTypeName) {
        if (successfulImport){
            System.out.printf("O ficheiro %s foi importado com sucesso.\n\n", fileTypeName);
        }else{
            System.out.printf("O ficheiro %s não foi importado.\n\n", fileTypeName);
        }
    }

    private String getfilepath() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insira o caminho para o ficheiro:");
        return scanner.nextLine();
    }

}
