package project.domain;

import org.w3c.dom.ls.LSInput;
import project.exception.ExcecaoFicheiro;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ImportarFicheiro {
    private static final String TODOS_DIAS = "T";

    private static final String DIAS_PARES = "P";

    private static final String DIAS_IMPARES = "I";
    private static final String TRES_DIAS = "3";

    public static boolean importWateringPlan(String filepath) throws IOException {
        try {
            ExcecaoFicheiro.verificarFicheiro(filepath, ".txt");
            ExcecaoFicheiro.validarPlanoRega(new File(filepath));
            ExcecaoFicheiro.verificarEstruturaFicheiro(new File(filepath));
        } catch (ExcecaoFicheiro e) {
            System.out.printf("%s\n\n", e.getMessage());
            return false;
        }
        File file = new File(filepath);
        Set<LocalTime> timeTurns = new HashSet<>();
        List<Rega> wateringAreas = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String[] line = reader.readLine().split(",");
        for (String time : line) {
            time = time.trim();
            timeTurns.add(LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
        }

        String currentLine;
        List<String> lineRega = new ArrayList<>();
        int counter = 0;
        while ((currentLine = reader.readLine()) != null) {
            line = currentLine.split(",");
            for (int i = 0; i < 3; i++) {
                lineRega.add(line[i]);
            }
            counter++;
        }
        LocalDate tempData = LocalDate.now();
        for (int i = 0; i < 30; i++) {
            for (LocalTime hora : timeTurns) {
                if (LocalTime.now().isBefore(hora)) {
                    LocalTime tempHora = hora;
                    for (int j = 1; j <= counter; j++) {
                        switch (lineRega.get(((j*3)-1))) {
                            case TODOS_DIAS:
                                tempHora = tempHora.plusMinutes(Integer.parseInt(lineRega.get(((j*3)-2))));
                                wateringAreas.add(new Rega(lineRega.get((j*3)-3),tempHora.minusMinutes(Integer.parseInt(lineRega.get(((j*3)-2)))),tempHora,tempData));
                                break;
                            case DIAS_IMPARES:
                                if (tempData.getDayOfMonth() % 2 == 1) {
                                    tempHora = tempHora.plusMinutes(Integer.parseInt(lineRega.get(((j*3)-2))));
                                    wateringAreas.add(new Rega(lineRega.get((j*3)-3),tempHora.minusMinutes(Integer.parseInt(lineRega.get(((j*3)-2)))),tempHora,tempData));
                                }
                                break;
                            case DIAS_PARES:
                                if (tempData.getDayOfMonth() % 2 == 0) {
                                    tempHora = tempHora.plusMinutes(Integer.parseInt(lineRega.get(((j*3)-2))));
                                    wateringAreas.add(new Rega(lineRega.get((j*3)-3),tempHora.minusMinutes(Integer.parseInt(lineRega.get(((j*3)-2)))),tempHora,tempData));
                                }
                                break;
                            case TRES_DIAS:
                                if (tempData.getDayOfMonth() % 3 == 0) {
                                    tempHora = tempHora.plusMinutes(Integer.parseInt(lineRega.get(((j*3)-2))));
                                    wateringAreas.add(new Rega(lineRega.get((j*3)-3),tempHora.minusMinutes(Integer.parseInt(lineRega.get(((j*3)-2)))),tempHora,tempData));
                                }
                                break;
                        }
                    }
                }
            }
            tempData = tempData.plusDays(1);
        }
        SistemaDeRega.setPlanoDeRega(new PlanoRega(wateringAreas));
        SistemaDeRega.setInicioDoPlanoDeRega(LocalDate.now());
        SistemaDeRega.setTempoInicialDeRega(timeTurns);
        reader.close();
        return true;
    }


    public static boolean importRedeDistribuicao(String locais, String distancias) throws ExcecaoFicheiro, IOException {
        ExcecaoFicheiro.verificarFicheiro(locais, ".csv");
        ExcecaoFicheiro.verificarFicheiro(distancias, ".csv");
        importFicheiroLocais(locais);
        importFicheiroDistancias(distancias);

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
            redeHub.addRoute(new Local(line[0]), new Local(line[1]), Integer.parseInt(line[2]));
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
            redeHub.addHub(line[0], Double.parseDouble(line[1]), Double.parseDouble(line[2]));
        }
    }
}
