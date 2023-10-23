package project.domain;

import project.exception.ExcecaoFicheiro;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ImportarFicheiro {

    public static boolean importWateringPlan(String filepath) throws IOException {
       try {
           ExcecaoFicheiro.verificarFicheiro(filepath);
       }catch (ExcecaoFicheiro e){
           return false;
        }
        File file = new File(filepath);
        Set<LocalTime> timeTurns = new HashSet<>();
        Set<Rega> wateringAreas = new LinkedHashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String[] turns = reader.readLine().split(",");
        for (String time : turns){
            time = checkTimeValue(time);
            timeTurns.add(LocalTime.parse(time.trim(), DateTimeFormatter.ofPattern("HH:mm")));
        }
        while (reader.readLine() != null){
            turns = reader.readLine().split(",");
            wateringAreas.add(new Rega(turns[0].trim(), Integer.parseInt(turns[1].trim()), turns[2].trim()));
        }
        SistemaDeRega.setPlanoDeRega(wateringAreas);
        SistemaDeRega.setInicioDoPlanoDeRega(LocalDate.now());
        SistemaDeRega.setTempoInicialDeRega(timeTurns);
        reader.close();
        return true;
    }

    private static String checkTimeValue(String time) {
        String[] hourAndMinutes = time.split(":");
            int value = Integer.parseInt(hourAndMinutes[0].trim());
            if (value < 10 && value > 0){
                time = "0" + hourAndMinutes[0].trim() + ":" + hourAndMinutes[1].trim();
            }
            return time;
    }

}
