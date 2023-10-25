package project.controller;

import project.domain.ImportarFicheiro;
import project.exception.ExcecaoFicheiro;

import java.io.IOException;

public class ImportarFicheiroController {

    public String importWateringPlan(String filepath) {
      String message = "";
      try {
          message = ImportarFicheiro.importWateringPlan(filepath);
      }catch (IOException e){
          message = "notSuccess";
      }
      return (message.equals("Success")) ? "Success" : message ;
    }
}
