package project;

import jdk.jshell.spi.ExecutionControl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ControladorRega {

    private final Integer NUM_DIAS = 30;

    private final String TODOS_DIAS = "T";

    private final String DIAS_PARES = "P";

    private final String DIAS_IMPARES = "I";
    private final String TRES_DIAS = "3";

    public Map<String,Integer> checkIsWatering(LocalDate data, LocalTime tempo, Set<PlanoDeRega> planoRega){
        // Retorna -1 se nao esta a regar, senao retorna numero de tempo para terminar rega + setor que rega


        return new HashMap<>();
    }

    public Map<String,Integer> checkIsWatering( Set<PlanoDeRega> planoRega){
        // Retorna -1 se nao esta a regar, senao retorna numero de tempo para terminar rega + setor que rega


        return new HashMap<>();
    }

    private Boolean checkDate(LocalDate data){
        return false;
    }
    private Boolean checkHour(LocalTime tempo){
        return false;
    }
}
