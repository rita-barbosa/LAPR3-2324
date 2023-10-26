package project.domain;

import org.junit.Test;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class SistemaDeRegaTest {


    @Test
    public void testGenerateDailyFileOfWateringPlan() {
        SistemaDeRega.setInicioDoPlanoDeRega(LocalDate.parse("05-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        // 8, 11, 14, 17, ...
        Set<LocalTime> times = new LinkedHashSet<>();
        times.add(LocalTime.parse("07:00"));
        times.add(LocalTime.parse("18:30"));

        SistemaDeRega.setTempoInicialDeRega(times);

        Set<Rega> setores = new LinkedHashSet<>();
        setores.add(new Rega("Sector1", 30, "T"));
        setores.add(new Rega("Sector2", 45, "I"));
        setores.add(new Rega("Sector3", 12, "P"));
        setores.add(new Rega("Sector4", 5, "3"));

        SistemaDeRega.setPlanoDeRega(setores);

        LocalDate date = LocalDate.parse("15-10-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        boolean result = SistemaDeRega.generateWateringDayRegister(date);

        assertTrue(result);

        String filepath = "files/WateringRegisters/WateringRegisters15-10-2022.csv";

        File file = new File(filepath);

        try {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String header = reader.readLine();
        String line1 = reader.readLine();
        String line2 = reader.readLine();
        String line4 = reader.readLine();
        String line5 = reader.readLine();
        reader.close();

        assertEquals("Dia,Sector,Duracao,Inicio,Final", header);
        assertEquals("15/10/2022,Sector1,30,07:00,07:30", line1);
        assertEquals("15/10/2022,Sector2,45,07:30,08:15", line2);
        assertEquals("15/10/2022,Sector1,30,18:30,19:00", line4);
        assertEquals("15/10/2022,Sector2,45,19:00,19:45", line5);
    } catch (IOException e) {
        fail("IOException occurred when reading the file.");
    }
        if (file.exists()) {
            file.delete();
        }
    }

}