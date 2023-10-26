package project.domain;

import org.junit.Test;

import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class ImportarFicheiroTest {

private static String result;

    @Test
    public void testImportWateringPlanSuccess() {
        try {
            String rightFile = "src/test/java/project/testFiles/ficheiroCorreto.txt";
            result = ImportarFicheiro.importWateringPlan(rightFile);
            assertEquals("Success", result);
        }catch (IOException e){
            System.out.println("testImportWateringPlanSuccess");
        }
    }

    @Test
    public void testImportWateringPlanFailureWrongLines() {
        try {
            String wrongLinesFile = "src/test/java/project/testFiles/ficheiroLinhasErrada.txt";
            result = ImportarFicheiro.importWateringPlan(wrongLinesFile);
            String exceptionMessage = "ERRO: Conteúdo ficheiro não corresponde ao esperado.\n" +
                    "As linhas com os setores a serem regados deve ter o seguinte formato:\n" +
                    " <parcela: [A-Z], duração, regularidade: [T,I,P,3]>, por exemplo A,14,T.";
            assertEquals(exceptionMessage, result);
        }catch (IOException e){
            System.out.println("testImportWateringPlanFailureWrongLines");
        }
    }

    @Test
    public void testImportWateringPlanFailureWrongHours() {
        try {
            String wrongHoursFile = "src/test/java/project/testFiles/ficheiroHoraErrada.txt";
            result = ImportarFicheiro.importWateringPlan(wrongHoursFile);
            String exceptionMessage = "ERRO: Conteúdo ficheiro não corresponde ao esperado.\n" +
                    "A primeira linha deve ter as horas de rega com o seguinte formato: hh:mm, hh:mm, etc.";
            assertEquals(exceptionMessage, result);
        }catch (IOException e){
            System.out.println("testImportWateringPlanFailureWrongHours");
        }
    }
}