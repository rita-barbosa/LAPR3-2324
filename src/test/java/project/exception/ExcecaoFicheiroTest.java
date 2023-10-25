package project.exception;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ExcecaoFicheiroTest {

    @Test (expected = ExcecaoFicheiro.class)
    public void verificarFicheiroNaoExiste() throws ExcecaoFicheiro, IOException {
        String ficheiro = "naoExiste.txt";
        ExcecaoFicheiro.verificarFicheiro(ficheiro);
    }
    @Test (expected = ExcecaoFicheiro.class)
    public void verificarFicheiroNaoTemExtensaoCorreta() throws ExcecaoFicheiro, IOException {
        String ficheiro = "naoExiste.csv";
        ExcecaoFicheiro.verificarFicheiro(ficheiro);
    }
    @Test (expected = ExcecaoFicheiro.class)
    public void verificarFicheiroHorasErradas() throws ExcecaoFicheiro, IOException {
        String ficheiro = "src/test/java/project/testFiles/ficheiroHoraErrada.txt";
        ExcecaoFicheiro.verificarFicheiro(ficheiro);
    }

    @Test (expected = ExcecaoFicheiro.class)
    public void verificarFicheiroLinhasErradas() throws ExcecaoFicheiro, IOException {
        String ficheiro = "src/test/java/project/testFiles/ficheiroLinhasErrada.txt";
        ExcecaoFicheiro.verificarFicheiro(ficheiro);
    }
    @Test
    public void verificarFicheiroCorreto() throws IOException {
        String ficheiro = "src/test/java/project/testFiles/ficheiroCorreto.txt";
        try {
            ExcecaoFicheiro.verificarFicheiro(ficheiro);
        }catch (ExcecaoFicheiro e){
            fail(e.getMessage());
        }
    }
}