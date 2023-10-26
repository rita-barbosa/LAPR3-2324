package project.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

public class SistemaDeRega{

    private static final ControladorRega controladorRega = new ControladorRega();
    private static Set<Rega> planoDeRega;
    private static Set<LocalTime> tempoInicialDeRega;
    private static LocalDate inicioDoPlanoDeRega;

    public static void setPlanoDeRega(Set<Rega> planoDeRega) {
        SistemaDeRega.planoDeRega = planoDeRega;
    }

    public static void setTempoInicialDeRega(Set<LocalTime> tempoInicialDeRega) {
        SistemaDeRega.tempoInicialDeRega = tempoInicialDeRega;
    }

    public static void setInicioDoPlanoDeRega(LocalDate inicioDoPlanoDeRega) {
        SistemaDeRega.inicioDoPlanoDeRega = inicioDoPlanoDeRega.plusDays(1);
    }

    public boolean verificarRega(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static ControladorRega getControladorRega() {
        return controladorRega;
    }

    public static Set<Rega> getPlanoDeRegas() {
        return planoDeRega;
    }

    public static Set<LocalTime> getTempoInicialDeRega(){
        return tempoInicialDeRega;
    }
    public static LocalDate getInicioDoPlanoDeRega() {
        return inicioDoPlanoDeRega;
    }

    public static boolean generateWateringDayRegister(LocalDate date)  {
        if (date == null){
            date = LocalDate.now();
        }
        String directoryPath = "files/WateringRegisters";
        String dateString = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).trim();
        String fileName = "WateringRegisters" + dateString + ".csv";

        File file = new File(directoryPath, fileName);
        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return writeWateringRegisterFile(file, date);
    }

    private static boolean writeWateringRegisterFile(File file, LocalDate date) {
        try {
            Map<String, Integer> map = controladorRega.checkIsWateringDay(date);;
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("Dia,Sector,Duracao,Inicio,Final\n");
            String dateString = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).trim();
            for (LocalTime turn : tempoInicialDeRega){
                LocalTime timeBeginning = turn;
                for (String rega : map.keySet()) {
                    String beginning = timeBeginning.format(DateTimeFormatter.ofPattern("HH:mm")).trim();
                    String ending = timeBeginning.plusMinutes(map.get(rega)).format(DateTimeFormatter.ofPattern("HH:mm")).trim();
                    String line = String.format("%s,%s,%s,%s,%s\n", dateString, rega, map.get(rega), beginning, ending);
                    fileWriter.write(line);
                    timeBeginning = LocalTime.parse(ending);
                }
            }
            fileWriter.close();
            return true;
        }catch (IOException e){
            return false;
        }
    }

}