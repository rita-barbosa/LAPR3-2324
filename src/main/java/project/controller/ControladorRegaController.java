package project.controller;

import project.domain.SistemaDeRega;

public class ControladorRegaController {
    public ControladorRegaController() {
    }

    public String checkWateringInRealTime(){
       return SistemaDeRega.getControladorRega().checkIsWateringNoData(/*SistemaDeRega.getPlanoDeRegas()*/);
    }

    public String checkWateringInSimulatedTime() {
        return SistemaDeRega.getControladorRega().checkIsWateringNoData(/*SistemaDeRega.getPlanoDeRegas()*/);
    }
}
