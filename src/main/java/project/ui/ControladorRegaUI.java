package project.ui;

import project.controller.ControladorRegaController;
import project.exception.ExcecaoData;
import project.exception.ExcecaoHora;
import project.ui.utils.Utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ControladorRegaUI implements Runnable {

    private final ControladorRegaController controller;

    public ControladorRegaUI() {
        this.controller = new ControladorRegaController();
    }

    @Override
    public void run() {
        List<String> options = new ArrayList<>();
        options.add("Verificar rega em Tempo Real.");
        options.add("Verificar rega em Tempo Simulado.");
        int option;
        String result = null;
        do {
            option = Utils.showAndSelectIndex(options, "Controlador de Rega:");
            if (option == 0){
               result = controller.checkWateringInRealTime();
                showOutput(result);
            } else if (option == 1) {
                LocalTime time = getTime();
                LocalDate day = getDay();
                result = controller.checkWateringInSimulatedTime(day, time);
                showOutput(result);
            }
        } while (option != -1);
        showOutput(result);
    }


    private void showOutput(String result) {
            System.out.println();
            System.out.println(result);
            System.out.println();
    }

    private LocalDate getDay() {
        Scanner scanner = new Scanner(System.in);
        String data = null;
        while (data == null){
            try {
                System.out.println("Defina um dia (formato dd/mm/yyyy):");
                data = scanner.nextLine();
                ExcecaoData.verificarData(data);
            }catch (ExcecaoData e){
                data = null;
            }
        }
        return LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    private LocalTime getTime() {
        Scanner scanner = new Scanner(System.in);
        String time = null;
        while (time == null){
            try {
                System.out.println("Defina uma hora (formato hh:mm):");
                time = scanner.nextLine();
                ExcecaoHora.verificarHora(time);
            }catch (ExcecaoHora e){
                time = null;
            }
        }
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
    }

}
