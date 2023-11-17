package project.ui.console.menu;

import project.ui.console.*;
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
        options.add(new MenuItem("Importar ficheiro do Plano de Rega.", new ImportarFicheiroUI("Rede Distruição")));
        options.add(new MenuItem("Localização ideal de N hubs.", new LocalizacaoIdealHubsUI()));
        options.add(new MenuItem("Percurso mínimo possível.", new PercursoMinimoUI()));
        options.add(new MenuItem("Rede de ligação mínima.", new RedeLigacaoMinimaUI()));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "Sistema de Rega");
            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
