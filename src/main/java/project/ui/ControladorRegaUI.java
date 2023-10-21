package project.ui;

import project.controller.ControladorRegaController;
import project.ui.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class ControladorRegaUI implements Runnable {

    private final ControladorRegaController controller;

    private String time;

    private String day;

    public ControladorRegaUI() {
        this.controller = new ControladorRegaController();
    }

//    private ControladorRegaController getController() {
//        return controller;
//    }

    @Override
    public void run() {
        List<String> options = new ArrayList<>();
        options.add("Verificar rega em Tempo Real.");
        options.add("Verificar rega em Tempo Simulado.");
        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "Controlador de Rega:");
            if (option == 1){
                controller.checkWateringInRealTime();
            } else if (option == 2) {
                time = getTime();
                day = getDay();
                //  controller.checkWateringInSimulatedTime();
            }
        } while (option != -1);
    }

    private String getDay() {
        System.out.println();
        return null;
    }

    private String getTime() {
        return null;
    }


}
