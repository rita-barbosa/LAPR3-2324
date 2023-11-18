package project.ui.console;

import project.controller.SelectStatementTestController;
import project.domain.Operacao;
import project.domain.OperacaoCultura;

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
            List<OperacaoCultura> listaOperacoes = controller.getSemeaduraOperationList();
            System.out.println("\nStatement compiled successfully.\n");
            printOperationList(listaOperacoes);
        } catch (SQLException e ) {
            System.out.println("\nStatement not compiled!\n" + e.getMessage());
        }
    }

    private void printOperationList(List<OperacaoCultura> lista){
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        System.out.println("|  ID  | Designação da Operação |    Data    | Quantidade | Unidade | ID Parcela | ID Cultura | Data Inicial | Data Final |");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
        for (OperacaoCultura operacao : lista){
            System.out.println(operacao);
        }
        System.out.println("---------------------------------------------------------------------------------------------------------------------------");
    }
}
