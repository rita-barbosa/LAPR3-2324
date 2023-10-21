package project;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class SistemaDeRega{

    private final ControladorRega controladorRega;
    private static Set<Rega> planoDeRega;
    private static Set<LocalTime> tempoInicialDeRega;
    private static LocalDate inicioDoPlanoDeRega;

    public SistemaDeRega( ControladorRega controlador) {
        this.controladorRega = controlador;
    }

    public boolean verificarRega(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ControladorRega getControladorRega() {
        return controladorRega;
    }

    public static Set<Rega> getPlanoDeRegas() {
        return planoDeRega;
    }

    public static Set<LocalTime> getTempoInicialDeRega(){
        return tempoInicialDeRega;
    }
    public static LocalDate getInicioDoPlanoDeRega() {
        return inicioDoPlanoDeRega;
    }
}
