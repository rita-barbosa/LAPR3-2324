package project.domain;

import project.exception.ExcecaoFicheiro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class ImportarFicheiro {

    public static void importWateringPlan(String filepath) throws IOException, ExcecaoFicheiro {
        ExcecaoFicheiro.verificarFicheiro(filepath);
        File file = new File(filepath);
        Set<LocalTime> timeTurns = null;
        Set<Rega> wateringAreas = null;
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String[] turns = reader.readLine().split(",");
        for (String times : turns){
            timeTurns.add(LocalTime.parse(times, DateTimeFormatter.ofPattern("HH:mm")));
        }
        while (reader.readLine() != null){
            turns = reader.readLine().split(",");
            wateringAreas.add(new Rega(turns[0].trim(), Integer.parseInt(turns[1].trim()), turns[2].trim()));
        }
        SistemaDeRega.setPlanoDeRega(wateringAreas);
        SistemaDeRega.setInicioDoPlanoDeRega(LocalDate.now());
        SistemaDeRega.setTempoInicialDeRega(timeTurns);
        reader.close();
    }

}
