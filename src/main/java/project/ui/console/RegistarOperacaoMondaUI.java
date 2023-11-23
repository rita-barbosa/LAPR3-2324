package project.ui.console;

import project.controller.RegistarOperacaoMondaController;
import project.ui.console.utils.Utils;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class RegistarOperacaoMondaUI implements Runnable {
    private RegistarOperacaoMondaController controller;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    private Date dataOperacao;
    private String nomeParcela;
    private String nomeComum;
    private String variedade;
    private String tipoUnidade;
    private Double quantidade;

    public RegistarOperacaoMondaUI() {
        controller = new RegistarOperacaoMondaController();
    }

    /*
       FAZER QUESTÃO SOBRE AS MAQUINAS!!!!!!
    */
    @Override
    public void run() {
        try {
            int index;
            System.out.println("-----------------------------------------");
            System.out.println("Registar uma nova Operação de Semeadura");
            System.out.println("-----------------------------------------");

            Scanner scanner = new Scanner(System.in);

            List<String> fields = controller.getFieldsNames();
            index = Utils.showAndSelectIndexNoCancel(fields, "Selecione a parcela:");
            nomeParcela = fields.get(index);

//                List<String> cultures = controller.getCulturesByField(nomeParcela);
//                index = Utils.showAndSelectIndex(cultures, "Selecione a cultura:");
//                String cultura = cultures.get(index);
//                String[] culturaInfo = cultura.split(" ");
//                nomeComum = culturaInfo[0];
//                variedade = culturaInfo[1];

            List<String> unitTypes = controller.getUnitTypes();
            index = Utils.showAndSelectIndexNoCancel(unitTypes, "Indique o tipo de unidade:");
            tipoUnidade = unitTypes.get(index);

            System.out.print("Data da operação (formato: dd/mm/yyyy): ");
            String dataOp = scanner.next();
            Utils.validateDate(dataOp);
            dataOp = dataOp.replace("/", "-");
            dataOperacao = formatter.parse(dataOp);

            quantidade = Utils.readDoubleFromConsole("Indique a quantidade a mondar:");

            //  boolean exists = controller.verifyIfOperationExists(nomeParcela, nomeComum, variedade, dataOperacao, tipoUnidade, quantidade);

            if (false) {
                boolean opStatus = controller.registerMondaOperation(nomeParcela, nomeComum, variedade, dataOperacao, tipoUnidade, quantidade);
                if (opStatus) {
                    System.out.println("\nOperação de semeadura registada com sucesso.\n");
                } else {
                    System.out.println("\nFalha em registar a operação de semeadura.\n");
                }
            } else {
                throw new SQLException("Os dados introduzidos se encontram no registo de uma operação já existente no sistema.");
            }
        } catch (ParseException | SQLException e) {
            System.out.println("\nFalha em registar a operação de semeadura.\n" + e.getMessage());
        }
    }


}
