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
        if (controller.checkIfPlanIsPresent()){
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
        }else{
            System.out.println("Neste momento não existe um Plano de Rega em vigor.\nPor favor, faça primeiro o importe de um ficheiro Plano de Rega.\n");
        }
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
                time = checkTimeValue(time);
                ExcecaoHora.verificarHora(time);
            }catch (ExcecaoHora e){
                time = null;
            }
        }
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
    }

        private static String checkTimeValue(String time) {
            String[] hourAndMinutes = time.split(":");
            String[] hour = hourAndMinutes[0].split("");
            int value = Integer.parseInt(hourAndMinutes[0].trim());
            if (value < 10 && value > 0 && hour.length == 1){
                time = "0" + hourAndMinutes[0].trim() + ":" + hourAndMinutes[1].trim();
            }
            return time.trim();
    }

}
