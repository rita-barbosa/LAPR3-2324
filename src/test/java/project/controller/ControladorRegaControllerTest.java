package project.controller;

import org.junit.Before;
import org.junit.Test;
import project.domain.ImportarFicheiro;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ControladorRegaControllerTest{
    private static final ControladorRegaController controller = new ControladorRegaController();

    @Before
    public void setUp() throws Exception {
        ImportarFicheiro.importWateringPlan("src/test/java/project/testFiles/ficheiroCorreto.txt");
    }

    @Test
    public void testCheckWateringInSimulatedTimeNotExists() {
        LocalDate date = LocalDate.now().plusDays(10000);
        LocalTime time = LocalTime.of(8, 35);
        String expected = "O dia indicado está fora do espaço de tempo ativo do plano de rega importado.";
        assertEquals(expected, controller.checkWateringInSimulatedTime(date, time));
    }

    @Test
    public void testCheckWateringInSimulatedTimeExists1()  {
        LocalDate date1 = LocalDate.now().plusDays(3);
        LocalTime time1 = LocalTime.of(8, 35);
        String expected1 = "Parcela a ser regada neste momento: D | Tempo restante: 14 minutos.\n";

        assertEquals(expected1, controller.checkWateringInSimulatedTime(date1, time1));

        LocalDate date2 = LocalDate.now().plusDays(5);
        LocalTime time2 = LocalTime.of(8, 55);
        String expected2 = "Parcela a ser regada neste momento: E | Tempo restante: 1 minutos.\n";
        assertEquals(expected2, controller.checkWateringInSimulatedTime(date2, time2));
    }

    @Test
    public void testCheckWateringInSimulatedTimeExists2()  {
        LocalDate date1 = LocalDate.now().plusDays(8);
        LocalTime time1 = LocalTime.of(17, 5);
        String expected1 = "Parcela a ser regada neste momento: A | Tempo restante: 9 minutos.\n";
        assertEquals(expected1, controller.checkWateringInSimulatedTime(date1, time1));

        LocalDate date2 = LocalDate.now().plusDays(10);
        LocalTime time2 = LocalTime.of(17, 18);
        String expected2 = "Parcela a ser regada neste momento: B | Tempo restante: 4 minutos.\n";
        assertEquals(expected2, controller.checkWateringInSimulatedTime(date2, time2));
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

    @Test
    public void testExportWateringPlan() {
        assertTrue(controller.exportWateringPlan());
    }
}