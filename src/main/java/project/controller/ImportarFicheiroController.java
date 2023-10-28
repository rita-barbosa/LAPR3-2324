package project.controller;

import project.domain.ImportarFicheiro;

public class ImportarFicheiroController {

    public boolean importWateringPlan(String filepath) {
        try {
            return ImportarFicheiro.importWateringPlan(filepath);
        }catch (Exception e){
            return false;
        }

    }
}
