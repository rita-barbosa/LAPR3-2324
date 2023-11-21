package project.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import project.domain.ImportarFicheiro;
import project.domain.SistemaDeRega;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ControladorRegaControllerTest {
    private static final ControladorRegaController controller = new ControladorRegaController();
    private static final LocalDate dataInicio = LocalDate.of(2023, 10, 20);

    @Before
    public void setUp() {
        ImportarFicheiro.importWateringPlan("src/test/java/project/testFiles/ficheiroCorreto.txt");
        SistemaDeRega.setInicioDoPlanoDeRega(dataInicio);
    }

    @Test
    public void testCheckWateringInSimulatedTimeNotExists() {
        LocalDate date = dataInicio.plusDays(10000);
        LocalTime time = LocalTime.of(8, 35);
        String expected = "ERRO: O dia indicado está fora do espaço de tempo ativo do plano de rega importado.";
        assertEquals(expected, controller.checkWateringInSimulatedTime(date, time));
    }

    @Test
    public void testCheckWateringInSimulatedTimeExists1() {
        LocalDate date1 = dataInicio.plusDays(3);
        LocalTime time1 = LocalTime.of(8, 35);
        String expected1 = "Parcela a ser regada neste momento: D | Tempo restante: 14 minutos.\n";

        assertEquals(expected1, controller.checkWateringInSimulatedTime(date1, time1));

        LocalDate date2 = dataInicio.plusDays(5);
        LocalTime time2 = LocalTime.of(8, 55);
        String expected2 = "Parcela a ser regada neste momento: E | Tempo restante: 1 minutos.\n";
        assertEquals(expected2, controller.checkWateringInSimulatedTime(date2, time2));
    }

    @Test
    public void testCheckWateringInSimulatedTimeExists2() {
        LocalDate date1 = dataInicio.plusDays(8);
        LocalTime time1 = LocalTime.of(17, 5);
        String expected1 = "Parcela a ser regada neste momento: A | Tempo restante: 9 minutos.\n";
        assertEquals(expected1, controller.checkWateringInSimulatedTime(date1, time1));

        LocalDate date2 = dataInicio.plusDays(10);
        LocalTime time2 = LocalTime.of(17, 18);
        String expected2 = "Parcela a ser regada neste momento: B | Tempo restante: 4 minutos.\n";
        assertEquals(expected2, controller.checkWateringInSimulatedTime(date2, time2));
    }


    @Test
    public void testCheckDateTrue() {
        LocalDate data = dataInicio.plusDays(12);
        Boolean expected = true;
        assertEquals(expected, controller.checkDate(data));
    }

    @Test
    public void testCheckDateFalse() {
        LocalDate data = dataInicio.plusDays(100);
        Boolean expected = false;
        assertEquals(expected, controller.checkDate(data));
    }

    @Test
    public void testCheckIfPlanIsPresent() {
        Boolean expected = true;
        assertEquals(expected, controller.checkIfPlanIsPresent());
    }

//    @Disabled
//    public void testExportWateringPlan() {
//        assertTrue(controller.exportWateringPlan());
//    }

//    @Disabled
//    public void testExportWateringPlanForSimulatedTime() {
//        assertTrue(controller.exportWateringPlanForSimulatedTime(LocalDate.of(2023, 10, 24)));
//    }

}