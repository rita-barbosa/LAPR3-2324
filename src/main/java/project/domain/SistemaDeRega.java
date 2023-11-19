package project.domain;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SistemaDeRega {
    private static final ControladorRega controladorRega = new ControladorRega();

    private static LocalDate inicioDoPlanoDeRega;

    private static List<Rega> planoDeRega;

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public SistemaDeRega() {
        planoDeRega = new ArrayList<>();
    }

    public static void setPlanoDeRega(List<Rega> planoDeRega) {
        SistemaDeRega.planoDeRega = planoDeRega;
        scheduleNextTask(0);
    }

    public static List<Rega> getPlanoDeRegas() {
        return planoDeRega;
    }

    public static void setInicioDoPlanoDeRega(LocalDate dataInicio) {
        inicioDoPlanoDeRega = dataInicio;
    }

    public static ControladorRega getControladorRega() {
        return controladorRega;
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
            for (Rega rega : planoDeRega) {
                if (rega.getData().equals(date)) {
                    String line = String.format("%s,%s,%s,%s,%s\n", dateString, rega.getIdSetor(), (rega.getHoraFim().getMinute() - rega.getHoraInicio().getMinute()), rega.getHoraInicio(), rega.getHoraFim());
                    fileWriter.write(line);
                }
            }
            fileWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private static void scheduleNextTask(int index) {
        if (index < planoDeRega.size()) {
            Rega atual = planoDeRega.get(index);
            LocalTime currentTime = LocalTime.now();
            LocalTime scheduledTime = atual.getHoraFim();


            long delay = calculateDelay(currentTime, scheduledTime);
            System.out.println("Scheduled: " + atual);

            scheduler.schedule(() -> {
                System.out.println("performing: " + atual);
                try {
                    sendToExternalDatabase(atual);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                scheduleNextTask(index + 1);
            }, delay, TimeUnit.MILLISECONDS);
        } else {

            scheduler.shutdown();
        }
    }

    private static void sendToExternalDatabase(Rega rega) throws IOException {
        //AQUI QUE VAI SER CHAMADO MÉTODO PARA INSERIR REGA NA BD
        System.out.println("Sending to database: " + rega);
    }

    private static long calculateDelay(LocalTime currentTime, LocalTime scheduledTime) {
        long currentMillis = currentTime.toNanoOfDay() / 1_000_000;
        long scheduledMillis = scheduledTime.toNanoOfDay() / 1_000_000;

        if (scheduledMillis < currentMillis) {
            scheduledMillis += TimeUnit.DAYS.toMillis(1);
        }

        return scheduledMillis - currentMillis;
    }

}