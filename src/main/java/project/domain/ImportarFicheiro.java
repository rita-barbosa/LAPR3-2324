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
           System.out.printf("%s\n\n", e.getMessage());
           return false;
        }
        File file = new File(filepath);
        Set<LocalTime> timeTurns = new HashSet<>();
        Set<Rega> wateringAreas = new LinkedHashSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String[] line = reader.readLine().split(",");
        for (String time : line){
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



}
