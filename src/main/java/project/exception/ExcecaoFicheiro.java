package project.exception;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ExcecaoFicheiro extends Exception {

    public ExcecaoFicheiro(String s) {
        super(s);
    }

    public static void verificarFicheiro(String caminho) throws ExcecaoFicheiro {
        File ficheiro = new File(caminho);

        if (!ficheiro.exists()) {
            throw new ExcecaoFicheiro("ERRO: Ficheiro não encontrado no caminho especificado.");
        }

        if (!ficheiro.isFile()) {
            throw new ExcecaoFicheiro("ERRO: Caminho especificado não é um ficheiro.");
        }

        if (!ficheiro.canRead()) {
            throw new ExcecaoFicheiro("ERRO: Não é possível ler o ficherio no caminho especificado devido a permissões insuficientes.");
        }

        if (!caminho.toLowerCase().endsWith(".txt")) {
            throw new ExcecaoFicheiro("ERRO: Ficheiro não possui a extensão '.txt'.");
        }
        verificarEstruturaFicheiro(ficheiro);
    }

    private static void verificarEstruturaFicheiro(File ficheiro) throws ExcecaoFicheiro {
        try (BufferedReader br = new BufferedReader(new FileReader(ficheiro))) {
            String linhaHoras;
            String linha;
            linhaHoras = br.readLine();
            validarLinhaHoras(linhaHoras);
            while ((linha = br.readLine()) != null) {
                validarLinha(linha);
            }
        } catch (IOException e) {
            throw new ExcecaoFicheiro("ERRO: Problemas ao fazer verificação da estrutura do ficheiro.");
        }
    }

    private static void validarLinhaHoras(String linha) throws ExcecaoFicheiro {
       String[] arr = linha.trim().split(",");

        for (int i = 0; i < arr.length; i++) {
            String hora = arr[i].trim();
            try{
                ExcecaoHora.verificarHora(hora);
            } catch (ExcecaoHora e) {
                throw new ExcecaoFicheiro("ERRO: Conteúdo ficheiro não corresponde ao esperado.\nA primeira linha deve ter as horas de rega com o seguinte formato: hh:mm, hh:mm, etc.");
            }
        }
    }

    private static void validarLinha(String linha) throws ExcecaoFicheiro {
        if(!linha.matches("^[A-Za-z],\\d{1,2},[TIP3]$")){
            throw new ExcecaoFicheiro("ERRO: Conteúdo ficheiro não corresponde ao esperado.\nAs linhas com os setores a serem regados deve ter o seguinte formato: <parcela: [A-Z], duração, regularidade: [T,I,P,3]>, por exemplo A,14,T.");
        }
    }
}