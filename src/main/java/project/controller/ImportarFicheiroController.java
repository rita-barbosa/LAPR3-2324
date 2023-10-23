package project.controller;

import project.domain.ImportarFicheiro;
import project.exception.ExcecaoFicheiro;

import java.io.IOException;

public class ImportarFicheiroController {

    public boolean importWateringPlan(String filepath) throws ExcecaoFicheiro, IOException {
      return ImportarFicheiro.importWateringPlan(filepath);
    }
}
