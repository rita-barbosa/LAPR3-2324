package project;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class PlanoDeRega {

    private static int numDias = 30;


    public void importarFicheiro(String filepath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String[] turns = null;
        if (reader.readLine() != null){
            turns = reader.readLine().split(",");
        }
        while(reader.readLine() != null){
            String[] line = reader.readLine().split(",");
            if (turns != null){
                for (int i = 0; i < turns.length; i++)
                    agendarOperacao(converterTempo(turns[i].trim()), line[0].trim(), Integer.parseInt(line[1].trim()), line[2].trim());
            }
            }

    }


    private Map<Integer, Integer> converterTempo(String timeStr){
        Map<Integer, Integer> time = new HashMap<>();
        String[] line = timeStr.split(":");
        time.put(Integer.parseInt(line[0].trim()), Integer.parseInt(line[1].trim()));
        return time;
    }


    public void agendarOperacao(Map<Integer, Integer> integerIntegerMap, String trim, int i, String s){
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Agende as tarefas de rega para iniciar nos horários desejados
        scheduler.scheduleAtFixedRate(() -> {
            // Implemente a lógica de rega aqui
            // Você pode verificar as horas atuais e decidir quais parcelas regar
        }, 0, 1, TimeUnit.HOURS); // A cada hora
    }


    public static void idk() {
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


            // Schedule a task to run every day at 8am
            scheduler.scheduleAtFixedRate(() -> {
                // Task logic for 8am
                System.out.println("Task executed at 8am: " + System.currentTimeMillis());
                numDias--;
                if (numDias <= 0) {
                    // After 30 days, cancel the task
                    scheduler.shutdown();
                }
            }, getInitialDelayForTime(8, 0), 1, TimeUnit.DAYS);

            // Schedule a task to run every day at 5pm
            scheduler.scheduleAtFixedRate(() -> {
                // Task logic for 5pm
                System.out.println("Task executed at 5pm: " + System.currentTimeMillis());
            }, getInitialDelayForTime(17, 0), 1, TimeUnit.DAYS);
        }

        // Calculate the initial delay until the specified time of day (hour, minute)
        private static long getInitialDelayForTime(int hour, int minute) {
            long now = System.currentTimeMillis();
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendar.set(java.util.Calendar.HOUR_OF_DAY, hour);
            calendar.set(java.util.Calendar.MINUTE, minute);
            calendar.set(java.util.Calendar.SECOND, 0);
            calendar.set(java.util.Calendar.MILLISECOND, 0);

            long initialDelay = calendar.getTimeInMillis() - now;
            if (initialDelay <= 0) {
                // If the specified time has already passed today, schedule for tomorrow
                initialDelay += TimeUnit.DAYS.toMillis(1);
            }
            return initialDelay;
        }
    


}
