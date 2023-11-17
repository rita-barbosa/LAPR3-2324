package project.ui.console;

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
        switch (fileTypeName){
            case "Plano de Rega":
                try{
                    System.out.println("Insira o caminho para o ficheiro:");
                    String filepath = getfilepath();

                    successfulImport = controller.importWateringPlan(filepath);
                }catch (Exception e){
                    System.out.printf("%s\n\n", e.getMessage());
                }
                break;
            case "Rede Distruição":
                try{
                    System.out.println("Insira o caminho para o ficheiro dos locais:");
                    String locaisFilePath = getfilepath();
                    System.out.println("Insira o caminho para o ficheiro das distâncias:");
                    String distanciaFilePath = getfilepath();

                    successfulImport = controller.importRedeDistribuicao(locaisFilePath,distanciaFilePath);
                }catch (Exception e){
                    System.out.printf("%s\n\n", e.getMessage());
                }
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
        return scanner.nextLine();
    }

}
