package project.domain;

import project.exception.ExcecaoFicheiro;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ImportarFicheiro {

    public static boolean importWateringPlan(String filepath) throws IOException {
        try {
            ExcecaoFicheiro.verificarFicheiro(filepath,".txt");
            ExcecaoFicheiro.validarPlanoRega(new File(filepath));
            ExcecaoFicheiro.verificarEstruturaFicheiro(new File(filepath));
        } catch (ExcecaoFicheiro e) {
            System.out.printf("%s\n\n", e.getMessage());
            return false;
        }
        File file = new File(filepath);
        Set<LocalTime> timeTurns = new HashSet<>();
        Set<Rega> wateringAreas = new LinkedHashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String[] line = reader.readLine().split(",");
        for (String time : line) {
            time = time.trim();
            timeTurns.add(LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
        }
        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            line = currentLine.split(",");
            wateringAreas.add(new Rega(line[0].trim(), Integer.parseInt(line[1].trim()), line[2].trim()));
        }
        SistemaDeRega.setPlanoDeRega(wateringAreas);
        SistemaDeRega.setInicioDoPlanoDeRega(LocalDate.now().plusDays(1));
        SistemaDeRega.setTempoInicialDeRega(timeTurns);
        reader.close();
        return true;
    }


    public static boolean importRedeDistribuicao(String locais, String distancias) {
        try {
            ExcecaoFicheiro.verificarFicheiro(locais,".csv");
            ExcecaoFicheiro.verificarFicheiro(distancias,".csv");
            importFicheiroLocais(locais);
            importFicheiroDistancias(distancias);
        } catch (ExcecaoFicheiro | IOException e) {
            return false;
        }

        return true;
    }

    private static void importFicheiroDistancias(String distancias) throws IOException {
        RedeHub redeHub = RedeHub.getInstance();
        //--------------------------------------
        BufferedReader reader = new BufferedReader(new FileReader(distancias));
        String currentLine;
        reader.readLine();
        String[] line;
        while ((currentLine = reader.readLine()) != null) {
            line = currentLine.split(",");
            redeHub.addRoute(new Hub(line[0]),new Hub(line[1]),Integer.parseInt(line[2]));
        }
    }

    private static void importFicheiroLocais(String locais) throws IOException {
        RedeHub redeHub = RedeHub.getInstance();
        //--------------------------------------
        BufferedReader reader = new BufferedReader(new FileReader(locais));
        String currentLine;
        reader.readLine();
        String[] line;
        while ((currentLine = reader.readLine()) != null) {
            line = currentLine.split(",");
            redeHub.addHub(line[0],Double.parseDouble(line[1]),Double.parseDouble(line[2]));
        }
    }
}
