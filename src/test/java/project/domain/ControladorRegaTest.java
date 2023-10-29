package project.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import project.domain.ImportarFicheiro;
import project.domain.SistemaDeRega;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControladorRegaTest {

    @Before
    public void setUp() throws Exception {
        ImportarFicheiro.importWateringPlan("src/test/java/project/testFiles/ficheiroCorretoV2.txt");
        LocalDate dataInicio = LocalDate.of(2023, 10, 20);
        SistemaDeRega.setInicioDoPlanoDeRega(dataInicio);
    }

    @Test
    public void checkIsWateringHour() {

        LocalDate dataImpar = LocalDate.of(2023, 10, 21);
        LocalDate dataPar = LocalDate.of(2023, 10, 22);
        LocalDate dataDiv3 = LocalDate.of(2023, 10, 23);

        LocalTime tempo815 = LocalTime.of(8,15);
        LocalTime tempo1715 = LocalTime.of(17,15);
        LocalTime tempoFora = LocalTime.of(1, 0);

        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringHour(tempo815, dataImpar),"Parcela a ser regada neste momento: B | Tempo restante: 7 minutos.\n" );
        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringHour(tempo1715, dataImpar),"Parcela a ser regada neste momento: B | Tempo restante: 7 minutos.\n");

        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringHour(tempo815, dataPar),"Parcela a ser regada neste momento: C | Tempo restante: 1 minutos.\n" );
        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringHour(tempo1715, dataPar),"Parcela a ser regada neste momento: C | Tempo restante: 1 minutos.\n");

        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringHour(tempo815, dataDiv3),"Parcela a ser regada neste momento: B | Tempo restante: 7 minutos.\n" );
        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringHour(tempo1715, dataDiv3),"Parcela a ser regada neste momento: B | Tempo restante: 7 minutos.\n");

        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringHour(tempoFora, dataImpar),"Não há parcelas a serem regadas agora.");

    }

    @Test
    public void checkIsWateringDayTest() {

        LocalDate dataImpar = LocalDate.of(2023, 10, 21);
        LocalDate dataPar = LocalDate.of(2023, 10, 22);
        LocalDate dataDiv3 = LocalDate.of(2023, 10, 20);

        Map<String, Integer> mapImpar = new LinkedHashMap<>();
        mapImpar.put("A", 14);
        mapImpar.put("B", 8);
        mapImpar.put("E", 7);

        Map<String, Integer> mapPar = new LinkedHashMap<>();
        mapPar.put("A", 14);
        mapPar.put("C", 2);
        mapPar.put("E", 7);

        Map<String, Integer> mapDiv3 = new LinkedHashMap<>();
        mapDiv3.put("A", 14);
        mapDiv3.put("C", 2);
        mapDiv3.put("D", 25);
        mapDiv3.put("E", 7);

        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringDay(dataImpar), mapImpar);
        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringDay(dataPar), mapPar);
        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringDay(dataDiv3), mapDiv3);
    }


}