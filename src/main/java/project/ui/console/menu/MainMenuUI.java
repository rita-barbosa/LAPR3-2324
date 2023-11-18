 package project.ui.console.menu;

 import project.ui.console.DatabaseConnectionTestUI;
 import project.ui.console.SelectStatementTestUI;
 import project.ui.console.utils.Utils;

 import java.util.ArrayList;
 import java.util.List;

 public class MainMenuUI implements Runnable {

    /**
     * Constructs a MainMenuUI object.
     */
    public MainMenuUI() {
    }

    /**
     * Runs the main menu user interface.
     */
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Testar conexão à Base de Dados", new DatabaseConnectionTestUI()));
        options.add(new MenuItem("Sistema de Rega", new MenuSistemaDeRega()));
        options.add(new MenuItem("Rede de Distruibuiçao", new MenuRedeDistribuicao()));
        options.add(new MenuItem("Testar SELECT", new SelectStatementTestUI()));
        options.add(new MenuItem("Registo de Operações", new MenuRegistoOperacoes()));
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nMain Menu");
            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}
