package project.controller;

import project.domain.ImportarFicheiro;
import project.exception.ExcecaoFicheiro;

import java.io.IOException;

public class ImportarFicheiroController {

    public void importWateringPlan(String filepath) throws ExcecaoFicheiro, IOException {
        ImportarFicheiro.importWateringPlan(filepath);
    }
}
