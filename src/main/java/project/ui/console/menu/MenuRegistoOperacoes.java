package project.ui.console.menu;

import project.ui.console.utils.Utils;
import project.ui.console.operacoes.RegistarOperacaoAplicacaoFatorProducaoUI;
import project.ui.console.operacoes.RegistarOperacaoColheitaUI;
import project.ui.console.operacoes.RegistarOperacaoMondaUI;
import project.ui.console.operacoes.RegistarOperacaoSemeaduraUI;


import java.util.ArrayList;
import java.util.List;

public class MenuRegistoOperacoes implements Runnable {

    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<>();
        options.add(new MenuItem("Registar operação de semeadura.", new RegistarOperacaoSemeaduraUI()));
        options.add(new MenuItem("Registar operação de monda.", new RegistarOperacaoMondaUI()));
        options.add(new MenuItem("Registar operação de colheita.", new RegistarOperacaoColheitaUI()));
        options.add(new MenuItem("Registar operação de aplicação de fator de produção.", new RegistarOperacaoAplicacaoFatorProducaoUI()));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "Registo de Operações");
            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }

}
