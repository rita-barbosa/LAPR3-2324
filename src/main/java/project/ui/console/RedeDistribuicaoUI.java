package project.ui.console;

import project.controller.RedeDistribuicaoController;

import java.util.ArrayList;
import java.util.List;

public class RedeDistribuicaoUI implements Runnable{
    private final RedeDistribuicaoController controller;

    public RedeDistribuicaoUI() {
        this.controller = new RedeDistribuicaoController();
    }

    @Override
    public void run() {
        List<String> options = new ArrayList<>();

//        if (controller.checkHasGraph()) {
//            options.add("Verificar rega em Tempo Real.");
//            options.add("Verificar rega em Tempo Simulado.");
//            options.add("Gerar registo do Plano de Rega para dia atual.");
//            options.add("Gerar registo do Plano de Rega para dia simulado.");
//            int option;
//            String result;
//            do {
//                option = Utils.showAndSelectIndex(options, "Controlador de Rega:");
//                switch (option) {
//                    case 0 -> {
//
//                    }
//                    case 1 -> {
//
//                    }
//                    case 2 -> {
//
//                    }
//                    case 3 -> {
//
//                    }
//                    default -> {
//                    }
//                }
//            } while (option != -1);
//        } else {
//            System.out.println("Neste momento não existem dados carregados.\nPor favor, faça primeiro o importe dos ficheiros de dados.\n");
//        }
    }

}
