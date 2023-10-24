package project.controller;

import org.junit.Test;
import project.exception.ExcecaoFicheiro;

import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ImportarFicheiroControllerTest {

    private static final ImportarFicheiroController controller = new ImportarFicheiroController();

    @Test
    public void testImportWateringPlanSuccess() {
        try {
            String rightFile = "src/test/java/project/testFiles/ficheiroCorreto.txt";
            assertTrue(controller.importWateringPlan(rightFile));
        }catch (ExcecaoFicheiro | IOException e){
            System.out.println("testImportWateringPlanSuccess");
        }
    }

    @Test
    public void testImportWateringPlanFailureWrongLines() {
        try {
            String wrongLinesFile = "src/test/java/project/testFiles/ficheiroLinhasErrada.txt";
            assertFalse(controller.importWateringPlan(wrongLinesFile));
        }catch (ExcecaoFicheiro | IOException e){
            System.out.println("testImportWateringPlanFailureWrongLines");
        }
    }

    @Test
    public void testImportWateringPlanFailureWrongHours() {
        try {
            String wrongHoursFile = "src/test/java/project/testFiles/ficheiroHoraErrada.txt";
            assertFalse(controller.importWateringPlan(wrongHoursFile));
        }catch (ExcecaoFicheiro | IOException e){
            System.out.println("testImportWateringPlanFailureWrongHours");
        }
    }
}