package project.controller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import project.domain.ImportarFicheiro;
import project.domain.SistemaDeRega;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;


public class ControladorRegaControllerTest{
    private static final ControladorRegaController controller = new ControladorRegaController();

    @Test
    public void testCheckWateringInRealTime() {
        //check
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.of(8, 35);
        String expected = "a";
        String result;
    }

    @Test
    public void testCheckWateringInSimulatedTimeNotExists() {
        LocalDate date = LocalDate.of(2025, 1, 26);
        LocalTime time = LocalTime.of(8, 35);
        String expected = "O dia indicado está fora do espaço de tempo ativo do plano de rega importado.";
        assertEquals(expected, controller.checkWateringInSimulatedTime(date, time));
    }

    @Test
    public void testCheckWateringInSimulatedTimeExists1()  {
        LocalDate date = LocalDate.now().plusDays(4);
        LocalTime time = LocalTime.of(8, 35);
        String expected = "Parcela a ser regada neste momento: D | Tempo restante: 14 minutos.\n";
        assertEquals(expected, controller.checkWateringInSimulatedTime(date, time));
    }

    @Test
    public void testCheckWateringInSimulatedTimeExists2() {
        LocalDate date = LocalDate.now().plusDays(4);
        LocalTime time = LocalTime.of(8, 40);
        String expected = "Parcela a ser regada neste momento: D | Tempo restante: 9 minutos.\n";
        assertEquals(expected, controller.checkWateringInSimulatedTime(date, time));
    }

    @Test
    public void testCheckWateringInSimulatedTimeExists3()  {
        LocalDate date = LocalDate.now().plusDays(8);
        LocalTime time = LocalTime.of(17, 5);
        String expected = "Parcela a ser regada neste momento: A | Tempo restante: 9 minutos.\n";
        assertEquals(expected, controller.checkWateringInSimulatedTime(date, time));
    }

    @Test
    public void testCheckWateringInSimulatedTimeExists4() {
        LocalDate date = LocalDate.now().plusDays(10);
        LocalTime time = LocalTime.of(17, 18);
        String expected = "Parcela a ser regada neste momento: B | Tempo restante: 4 minutos.\n";
        assertEquals(expected, controller.checkWateringInSimulatedTime(date, time));
    }


    @Test
    public void testCheckDateTrue() {
        LocalDate data = LocalDate.now().plusDays(12);
        Boolean expected = true;
        assertEquals(expected, controller.checkDate(data));
    }

    @Test
    public void testCheckDateFalse() {
        LocalDate data = LocalDate.now().plusDays(1000);
        Boolean expected = false;
        assertEquals(expected, controller.checkDate(data));
    }

    @Test
    public void testCheckIfPlanIsPresent() {
        Boolean expected = true;
        assertEquals(expected, controller.checkIfPlanIsPresent());
    }

    @Before
    public void setUp() throws Exception {
        ImportarFicheiro.importWateringPlan("src/test/java/project/testFiles/ficheiroCorreto.txt");
    }
}