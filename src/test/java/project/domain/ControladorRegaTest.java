package project.domain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import project.domain.ImportarFicheiro;
import project.domain.SistemaDeRega;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControladorRegaTest {

    @Before
    public void setUp() throws Exception {
        ImportarFicheiro.importWateringPlan("src/test/java/project/testFiles/ficheiroCorreto.txt");
    }

    @Test
    public void checkIsWateringHour() {
    }

    @Test
    public void checkIsWateringDayTest() {

        LocalDate dataInicio = LocalDate.of(2023, 10, 20);
        SistemaDeRega.setInicioDoPlanoDeRega(dataInicio);

        LocalDate dataImpar = LocalDate.of(2023, 10, 21);
        LocalDate dataPar = LocalDate.of(2023, 10, 22);
        LocalDate dataDiv3 = LocalDate.of(2023, 10, 23);

        Map<String, Integer> mapImpar = new LinkedHashMap<>();
        mapImpar.put("A", 14);
        mapImpar.put("C", 2);
        mapImpar.put("E", 7);

        Map<String, Integer> mapPar = new LinkedHashMap<>();
        mapPar.put("A", 14);
        mapPar.put("B", 8);
        mapPar.put("E", 7);

        Map<String, Integer> mapDiv3 = new LinkedHashMap<>();
        mapDiv3.put("A", 14);
        mapDiv3.put("D", 25);
        mapDiv3.put("E", 7);

        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringDay(dataImpar), mapImpar);
        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringDay(dataPar), mapPar);
        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringDay(dataDiv3), mapDiv3);
    }

    @Test
    public void checkIsWateringNoData() {
    }
}