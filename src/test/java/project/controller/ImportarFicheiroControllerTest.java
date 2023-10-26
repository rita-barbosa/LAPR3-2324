package project.controller;

import org.junit.Test;
import project.exception.ExcecaoFicheiro;

import java.io.IOException;

import static org.junit.Assert.*;

public class ImportarFicheiroControllerTest {

    private static final ImportarFicheiroController controller = new ImportarFicheiroController();
    private static String result;


    @Test
    public void testImportWateringPlanSuccess() {
        result = controller.importWateringPlan("src/test/java/project/testFiles/ficheiroCorreto.txt");
        assertEquals("Success", result);
    }

    @Test
    public void testImportWateringPlanFailure() {
        result = controller.importWateringPlan("src/test/java/project/testFiles/ficheiroHoraErrada.txt");
        assertNotEquals("Success", result);
    }

}