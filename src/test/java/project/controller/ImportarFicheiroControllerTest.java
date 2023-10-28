package project.controller;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImportarFicheiroControllerTest {

    private static final ImportarFicheiroController controller = new ImportarFicheiroController();
    private static boolean result;


    @Test
    public void testImportWateringPlanSuccess() {
        result = controller.importWateringPlan("src/test/java/project/testFiles/ficheiroCorreto.txt");
        assertTrue(result);
    }

    @Test
    public void testImportWateringPlanFailure() {
        result = controller.importWateringPlan("src/test/java/project/testFiles/ficheiroHoraErrada.txt");
        assertFalse(result);
    }

}