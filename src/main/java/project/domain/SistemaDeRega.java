package project.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class SistemaDeRega{

    private static final ControladorRega controladorRega = new ControladorRega();
    private static Set<Rega> planoDeRega;
    private static Set<LocalTime> tempoInicialDeRega;
    private static LocalDate inicioDoPlanoDeRega;

    public static void setPlanoDeRega(Set<Rega> planoDeRega) {
        SistemaDeRega.planoDeRega = planoDeRega;
    }

    public static void setTempoInicialDeRega(Set<LocalTime> tempoInicialDeRega) {
        SistemaDeRega.tempoInicialDeRega = tempoInicialDeRega;
    }

    public static void setInicioDoPlanoDeRega(LocalDate inicioDoPlanoDeRega) {
        SistemaDeRega.inicioDoPlanoDeRega = inicioDoPlanoDeRega;
    }

    public boolean verificarRega(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static ControladorRega getControladorRega() {
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
