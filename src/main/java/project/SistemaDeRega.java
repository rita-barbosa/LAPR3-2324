package project;

import jdk.jshell.spi.ExecutionControl;

public class SistemaDeRega extends Edificio{

    private Controlador controlador;

    public SistemaDeRega(int edificioID, String designacao, double area, TipoDeEdificio tipoDeEdificio, Controlador controlador) {
        super(edificioID, designacao, area, tipoDeEdificio);
        this.controlador = controlador;
    }

    public boolean verificarRega(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
