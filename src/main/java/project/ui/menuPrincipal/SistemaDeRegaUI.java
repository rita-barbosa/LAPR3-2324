package project.ui.menuPrincipal;

import project.ui.ControladorRegaUI;
import project.ui.importarFicheiroUI;
import project.ui.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class SistemaDeRegaUI implements Runnable{

    /**
     * Contrói uma SistemaDeRegaUI.
     */
    public SistemaDeRegaUI() {
    }

    /**
     * Corre a interface do sistema de rega ao dispôr as opções das funcionalidades.
     */
    public void run() {
        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Importar ficheiro do Plano de Rega.", new importarFicheiroUI("Plano de Rega")));
        options.add(new MenuItem("Verificar ação de rega no tempo.", new ControladorRegaUI()));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "Sistema de Rega:");
            if ((option >= 0) && (option < options.size())) {
               options.get(option).run();
            }
        } while (option != -1);
    }
}
