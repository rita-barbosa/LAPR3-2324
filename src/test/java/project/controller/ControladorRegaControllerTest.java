package project.controller;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import project.domain.ImportarFicheiro;
import project.domain.SistemaDeRega;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;


public class ControladorRegaControllerTest{

    //define o controller
    private static final ControladorRegaController controller = new ControladorRegaController();
    private static final ImportarFicheiroController controllerfICHEIRO = new ImportarFicheiroController();
    private static String result;


//    @Test
//    public void testImportWateringPlanSuccess() {
//        result = controllerfICHEIRO.importWateringPlan("src/test/java/project/testFiles/ficheiroCorreto.txt");
//        assertEquals("Success", result);
//    }


    @Test
    @BeforeAll
    public void setUp() throws IOException {
//        ImportarFicheiro.importWateringPlan("src/test/java/project/testFiles/ficheiroCorreto.txt");
        result = controllerfICHEIRO.importWateringPlan("src/test/java/project/testFiles/ficheiroCorreto.txt");
        assertEquals("Success", result);
        //define o plano de rega, atraves da classe ImportFicheiro
    }

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
        SistemaDeRega rega = new SistemaDeRega();

        LocalDate date = LocalDate.of(2025, 1, 26);
        LocalTime time = LocalTime.of(8, 35);
        String expected = "O dia indicado está fora do espaço de tempo ativo do plano de rega importado.";
        assertEquals(expected, controller.checkWateringInSimulatedTime(date, time));
    }

    @Test
    public void testCheckWateringInSimulatedTimeExists1() {
        SistemaDeRega rega = new SistemaDeRega();

        LocalDate date = LocalDate.now().plusDays(4);
        LocalTime time = LocalTime.of(8, 35);
        String expected = "Parcela a ser regada neste momento: D | Tempo restante: 14 minutos.\n"; //A ainda faltam 9 minutos  B ainda faltam 3 minutos  C faltam 20 minutos D faltam 20 minutos E ainda faltam 2 minutos F ainda faltam 5 minutos
        assertEquals(expected, controller.checkWateringInSimulatedTime(date, time));
    }

    @Test
    public void testCheckWateringInSimulatedTimeExists2() {
        LocalDate date = LocalDate.now().plusDays(4);
        LocalTime time = LocalTime.of(8, 40);
//        String expected = "a"; //A ainda faltam 4 minutos  C faltam 15 minutos D faltam 15 minutos
        String expected = "Parcela a ser regada neste momento: D | Tempo restante: 9 minutos.\n";
        assertEquals(expected, controller.checkWateringInSimulatedTime(date, time));
    }

    @Test
    public void testCheckWateringInSimulatedTimeExists3() {
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
    public void testCheckDateTrue(){
        //check
        LocalDate data = LocalDate.now().plusDays(12);
        Boolean expected = true;

        assertEquals(expected, controller.checkDate(data));
    }

    @Test
    public void testCheckDateFalse(){
        //check
        LocalDate data = LocalDate.now().plusDays(1000);
        Boolean expected = false;

        assertEquals(expected, controller.checkDate(data));
    }

    @Test
    public void testCheckIfPlanIsPresent(){
        Boolean expected = true;

        assertEquals(expected, controller.checkIfPlanIsPresent());
    }


}