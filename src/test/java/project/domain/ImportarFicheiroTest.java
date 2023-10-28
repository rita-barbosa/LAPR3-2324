package project.domain;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ImportarFicheiroTest {

private static boolean result;

    @Test
    public void testImportWateringPlanSuccess() {
        try {
            String rightFile = "src/test/java/project/testFiles/ficheiroCorreto.txt";
            result = ImportarFicheiro.importWateringPlan(rightFile);
            assertTrue(result);
        }catch (IOException e){
            System.out.println("testImportWateringPlanSuccess");
        }
    }

    @Test
    public void testImportWateringPlanFailureWrongLines() {
        String exceptionMessage = """
                ERRO: Conteúdo ficheiro não corresponde ao esperado.
                As linhas com os setores a serem regados deve ter o seguinte formato:
                 <parcela: [A-Z], duração, regularidade: [T,I,P,3]>, por exemplo A,14,T.""";
        try {
            String wrongLinesFile = "src/test/java/project/testFiles/ficheiroLinhasErrada.txt";
            result = ImportarFicheiro.importWateringPlan(wrongLinesFile);
            assertFalse(result);
        }catch (IOException e){
            assertEquals(exceptionMessage, e.getMessage());
        }
    }

    @Test
    public void testImportWateringPlanFailureWrongHours() {
        String exceptionMessage = "ERRO: Conteúdo ficheiro não corresponde ao esperado.\n" +
                "A primeira linha deve ter as horas de rega com o seguinte formato: hh:mm, hh:mm, etc.";
        try {
            String wrongHoursFile = "src/test/java/project/testFiles/ficheiroHoraErrada.txt";
            result = ImportarFicheiro.importWateringPlan(wrongHoursFile);
            assertFalse(result);
        }catch (IOException e){
            assertEquals(exceptionMessage, e.getMessage());
        }
    }
}