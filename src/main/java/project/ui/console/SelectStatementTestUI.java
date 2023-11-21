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
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println(String.format("| %4s | %22s | %10s | %10s | %7s | %20s | %20s | %20s | %12s | %10s |",
                "ID", "Designação da Operação", "Data", "Quantidade", "Unidade", "Nome Parcela", "Nome Comum", "Variedade", "Data Inicial", "Data Final"));
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (OperacaoCultura operacao : lista){
            System.out.println(operacao);
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}
