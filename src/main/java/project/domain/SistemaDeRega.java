package project.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SistemaDeRega {

    private static final ControladorRega controladorRega = new ControladorRega();
    private static PlanoRega planoDeRega;
    private static Set<LocalTime> tempoInicialDeRega;
    private static LocalDate inicioDoPlanoDeRega;

    public static void setPlanoDeRega(PlanoRega planoDeRega) {
        SistemaDeRega.planoDeRega = planoDeRega;
    }

    public static void setTempoInicialDeRega(Set<LocalTime> tempoInicialDeRega) {
        SistemaDeRega.tempoInicialDeRega = tempoInicialDeRega;
    }

    public static void setInicioDoPlanoDeRega(LocalDate inicioDoPlanoDeRega) {
        SistemaDeRega.inicioDoPlanoDeRega = inicioDoPlanoDeRega;
    }

    public boolean verificarRega() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static ControladorRega getControladorRega() {
        return controladorRega;
    }

    public static PlanoRega getPlanoDeRegas() {
        return planoDeRega;
    }

    public static Set<LocalTime> getTempoInicialDeRega() {
        return tempoInicialDeRega;
    }

    public static LocalDate getInicioDoPlanoDeRega() {
        return inicioDoPlanoDeRega;
    }

    public static boolean generateWateringDayRegister(LocalDate date) {
        if (date == null) {
            date = LocalDate.now();
        }
        String dateString = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).trim();
        String fileName = "WateringRegisters" + dateString + ".csv";

        File directory = new File("files\\WateringRegisters");
        File file = new File(directory, fileName);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return writeWateringRegisterFile(file, date);
    }

    private static boolean writeWateringRegisterFile(File file, LocalDate date) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("Dia,Sector,Duracao,Inicio,Final\n");
            String dateString = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).trim();
            for (Rega rega : SistemaDeRega.planoDeRega.getPlanoDeRega()) {
                if (rega.getData().equals(date)) {
                    String line = String.format("%s,%s,%s,%s,%s\n", dateString, rega.getIdSetor(), (rega.getHoraFim().getMinute()-rega.getHoraInicio().getMinute()), rega.getHoraInicio(), rega.getHoraFim());
                    fileWriter.write(line);
                }
            }
            fileWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}