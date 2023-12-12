package project.controller.rede;

import project.domain.RedeHub;
import project.structure.EstruturaDeEntregaDeDados;

public class PercursoMinimoController {

    public static EstruturaDeEntregaDeDados analyzeData(int autonomia){
        return RedeHub.analyzeData(autonomia);
    }

}
