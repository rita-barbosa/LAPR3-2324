package project.controller;

import project.domain.SistemaDeRega;

import java.time.LocalDate;
import java.time.LocalTime;

public class ControladorRegaController {
    public ControladorRegaController() {
    }

    public String checkWateringInRealTime(){
       return SistemaDeRega.getControladorRega().checkIsWateringNoData(/*SistemaDeRega.getPlanoDeRegas()*/);
    }

    public String checkWateringInSimulatedTime(LocalDate day, LocalTime time) {
        return SistemaDeRega.getControladorRega().checkIsWateringHour(time, day);
    }
}
