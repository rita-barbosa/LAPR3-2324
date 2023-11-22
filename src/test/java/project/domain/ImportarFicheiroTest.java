package project.domain;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ImportarFicheiroTest {

private static boolean result;

    @Test
    public void testImportWateringPlanSuccess() {
        String rightFile = "src/test/java/project/testFiles/ficheiroCorreto.txt";
        result = ImportarFicheiro.importWateringPlan(rightFile);
        assertTrue(result);
    }

    @Test
    public void testImportWateringPlanFailureWrongLines() {
        String exceptionMessage = """
                ERRO: Conteúdo ficheiro não corresponde ao esperado.
                As linhas com os setores a serem regados deve ter o seguinte formato:
                 <parcela: [A-Z], duração, regularidade: [T,I,P,3]>, por exemplo A,14,T.""";
        String wrongLinesFile = "src/test/java/project/testFiles/ficheiroLinhasErrada.txt";
        result = ImportarFicheiro.importWateringPlan(wrongLinesFile);
        assertFalse(result);
    }

    @Test
    public void testImportWateringPlanFailureWrongHours() {
        String exceptionMessage = "ERRO: Conteúdo ficheiro não corresponde ao esperado.\n" +
                "A primeira linha deve ter as horas de rega com o seguinte formato: hh:mm, hh:mm, etc.";
        String wrongHoursFile = "src/test/java/project/testFiles/ficheiroHoraErrada.txt";
        result = ImportarFicheiro.importWateringPlan(wrongHoursFile);
        assertFalse(result);
    }
}