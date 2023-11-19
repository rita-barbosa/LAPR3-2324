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
        LocalDate dataInicio = LocalDate.of(2023, 11, 19);
        SistemaDeRega.setInicioDoPlanoDeRega(dataInicio);
    }

    @Test
    public void checkIsWateringHour() {

        LocalDate dataImpar = LocalDate.of(2023, 11, 23);
        LocalDate dataPar = LocalDate.of(2023, 11, 22);
        LocalDate dataDiv3 = LocalDate.of(2023, 11, 21);

        LocalTime tempo815 = LocalTime.of(8,15);
        LocalTime tempo1715 = LocalTime.of(17,15);
        LocalTime tempoFora = LocalTime.of(1, 0);

//        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringHour(tempo815, dataImpar),"Parcela a ser regada neste momento: B | Tempo restante: 7 minutos.\n" );
     Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringHour(tempo1715, dataImpar),"Parcela a ser regada neste momento: B | Tempo restante: 7 minutos.\n");

//        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringHour(tempo815, dataPar),"Parcela a ser regada neste momento: C | Tempo restante: 1 minutos.\n" );
        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringHour(tempo1715, dataPar),"Parcela a ser regada neste momento: C | Tempo restante: 1 minutos.\n");

//        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringHour(tempo815, dataDiv3),"Parcela a ser regada neste momento: B | Tempo restante: 7 minutos.\n" );
        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringHour(tempo1715, dataDiv3),"Parcela a ser regada neste momento: B | Tempo restante: 7 minutos.\n");

        Assertions.assertEquals(SistemaDeRega.getControladorRega().checkIsWateringHour(tempoFora, dataImpar),"Não há parcelas a serem regadas agora.");

    }



}