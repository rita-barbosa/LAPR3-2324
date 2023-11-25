package project.ui.console.operacoes;

import project.controller.RegistarOperacaoSemeaduraController;
import project.ui.console.utils.Utils;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RegistarOperacaoSemeaduraUI implements Runnable {

    private RegistarOperacaoSemeaduraController controller;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    String designacaoOperacaoAgricola = "Semeadura";
    private Date dataOperacao;
    private String nomeParcela;
    private String tipoUnidade;
    private Double quantidade;
    private String nomeComum;
    private String variedade;
    private String permanencia;

    public RegistarOperacaoSemeaduraUI() {
        controller = new RegistarOperacaoSemeaduraController();
    }

    @Override
    public void run() {
        try {
            int index;
            System.out.println("-------------------------------------------");
            System.out.println("| Registar uma nova Operação de Semeadura |");
            System.out.println("-------------------------------------------");

            Scanner scanner = new Scanner(System.in);

            List<String> fields = controller.getFieldsNames();
            index = Utils.showAndSelectIndexNoCancel(fields, "Selecione a parcela:");
            nomeParcela = fields.get(index);

            List<String> cultures = controller.getCultures();
            index = Utils.showAndSelectIndexNoCancel(cultures, "Selecione a cultura:");
            String cultura = cultures.get(index);
            processCultureChoice(cultura);

            if (permanencia.equals("Permanente")) {
                tipoUnidade = "un";
            } else {
                List<String> unitTypes = new ArrayList<>();
                unitTypes.add("ha");
                unitTypes.add("m2");
                index = Utils.showAndSelectIndexNoCancel(unitTypes, "Indique o tipo de unidade:");
                tipoUnidade = unitTypes.get(index);
            }

            System.out.print("Data da operação (formato: dd/mm/yyyy): ");
            String dataOp = scanner.next();
            Utils.validateDate(dataOp);
            dataOp = dataOp.replace("/", "-");
            dataOperacao = formatter.parse(dataOp);

            quantidade = Utils.readDoubleFromConsole("Indique a quantidade a semear:");

            boolean opStatus = controller.registerOperation(nomeParcela, nomeComum, variedade, dataOperacao, tipoUnidade, quantidade);
            if (opStatus) {
                System.out.println("\nOperação de semeadura registada com sucesso.\n");
            } else {
                System.out.println("\nFalha em registar a operação de semeadura.\n");
            }
        } catch (ParseException | SQLException e) {
            System.out.println("\nFalha em registar a operação de semeadura.\n" + e.getMessage().split("\n")[0].substring(11) + "\n");
        }
    }

    private void processCultureChoice(String cultura) {
        String[] culturaInfo = cultura.split("\\|");
        permanencia = culturaInfo[1].trim();

        String[] plantaInfo = culturaInfo[0].split(" ");
        for (String info : plantaInfo) {
            boolean justUpper = true;
            for (char c : info.toCharArray()) {
                if (Character.isLowerCase(c)) {
                    justUpper = false;
                    break;
                }
            }
            if (!justUpper) {
                nomeComum = concatenateWithSpace(nomeComum, info);
            } else {
                variedade = concatenateWithSpace(variedade, info);
            }
        }
        variedade = variedade.trim();
        nomeComum = nomeComum.trim();
    }

    private String concatenateWithSpace(String original, String info) {
        if (original == null) {
            return info;
        } else {
            return original + " " + info;
        }
    }

}