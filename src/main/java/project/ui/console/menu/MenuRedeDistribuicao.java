package project.ui.console.menu;

import project.ui.console.ControladorRegaUI;
import project.ui.console.ImportarFicheiroUI;
import project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class MenuRedeDistribuicao implements Runnable {

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Importar ficheiro de dados", new ImportarFicheiroUI("Rede Distruição")));
        options.add(new MenuItem("Controlador de Rega.", new ControladorRegaUI()));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "Sistema de Rega");
            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
