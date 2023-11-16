package project.exception;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ExcecaoFicheiroTest {

    @Test (expected = ExcecaoFicheiro.class)
    public void verificarFicheiroNaoExiste() throws ExcecaoFicheiro {
        String ficheiro = "naoExiste.txt";
        ExcecaoFicheiro.verificarFicheiro(ficheiro,".txt");
    }
    @Test (expected = ExcecaoFicheiro.class)
    public void verificarFicheiroNaoTemExtensaoCorreta() throws ExcecaoFicheiro {
        String ficheiro = "naoExiste.csv";
        ExcecaoFicheiro.verificarFicheiro(ficheiro,".txt");
    }
    @Test (expected = ExcecaoFicheiro.class)
    public void verificarFicheiroHorasErradas() throws ExcecaoFicheiro {
        String ficheiro = "src/test/java/project/testFiles/ficheiroHoraErrada.txt";
        ExcecaoFicheiro.verificarEstruturaFicheiro(new File(ficheiro));
    }

    @Test (expected = ExcecaoFicheiro.class)
    public void verificarFicheiroLinhasErradas() throws ExcecaoFicheiro {
        String ficheiro = "src/test/java/project/testFiles/ficheiroLinhasErrada.txt";
        ExcecaoFicheiro.verificarEstruturaFicheiro(new File(ficheiro));
    }
    @Test
    public void verificarFicheiroCorreto() {
        String ficheiro = "src/test/java/project/testFiles/ficheiroCorreto.txt";
        try {
            ExcecaoFicheiro.verificarFicheiro(ficheiro,".txt");
            ExcecaoFicheiro.validarPlanoRega(new File(ficheiro));
        }catch (ExcecaoFicheiro e){
            fail(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}