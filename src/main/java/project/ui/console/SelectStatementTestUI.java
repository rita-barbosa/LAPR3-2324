package project.ui.console;

import project.controller.SelectStatementTestController;
import project.domain.Operacao;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class SelectStatementTestUI implements Runnable {

    private SelectStatementTestController controller;

    public SelectStatementTestUI() {
        controller = new SelectStatementTestController();
    }

    @Override
    public void run() {
        try {
            List<Operacao> listaOperacoes = Collections.singletonList((Operacao) controller.getSemeaduraOperationList());
            System.out.println("\nStatement compiled successfully.\n");
            printOperationList(listaOperacoes);
        } catch (SQLException e ) {
            System.out.println("\nStatement not compiled!\n" + e.getMessage());
        }
    }

    private void printOperationList(List<Operacao> lista){
        System.out.println("---------------------------------------------------------------------");
        System.out.println("|  ID  | Designação da Operação |    Data    | Quantidade | Unidade |");
        System.out.println("---------------------------------------------------------------------");
        for (Operacao operacao : lista){
            System.out.println(operacao);
        }
        System.out.println("---------------------------------------------------------------------");
    }
}
