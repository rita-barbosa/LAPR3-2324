package project.ui.console;

import project.controller.operacoes.RegistarOperacaoSemeaduraController;
import project.ui.console.utils.Utils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RegistarOperacaoSemeaduraUI implements Runnable {

    private RegistarOperacaoSemeaduraController controller;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    String designacaoOperacaoAgricola = "Semeadura";
    private Date dataOperacao;
    private Integer idParcela;
    private Integer idCultura;
    private String tipoUnidade;
    private Double quantidade;

    public RegistarOperacaoSemeaduraUI() {
        controller = new RegistarOperacaoSemeaduraController();
    }

    @Override
    public void run() {
        try {
            int index;
            System.out.println("-----------------------------------------");
            System.out.println("Registar uma nova Operação de Semeadura");
            System.out.println("-----------------------------------------");

            Scanner scanner = new Scanner(System.in);

            Map<BigDecimal, String>  fieldsIDs = controller.getFieldsIDs();
            BigDecimal bigDecimalValue = Utils.showAndSelectIndex(fieldsIDs, "Selecione o id da parcela:");
            idParcela = bigDecimalValue.intValue();


            Map<BigDecimal, String> cultureIDs = controller.getCulturesIDs();
            bigDecimalValue = Utils.showAndSelectIndex(cultureIDs, "Selecione o id da cultura:");
            idCultura = bigDecimalValue.intValue();


            List<String> unitTypes = controller.getUnitTypes();
            index = Utils.showAndSelectIndex(unitTypes, "Indique o tipo de unidade:");
            tipoUnidade = unitTypes.get(index);

            System.out.print("Data da operação: ");
            String dataOp = scanner.next();
            Utils.validateDate(dataOp);
            dataOp = dataOp.replace("/", "-");
            dataOperacao = formatter.parse(dataOp);

            quantidade = Utils.readDoubleFromConsole("Quantidade: ");

            boolean opStatus = controller.registerOperation(idParcela, designacaoOperacaoAgricola, idCultura, dataOperacao, tipoUnidade, quantidade);

            if (opStatus){
                System.out.println("\nOperação de semeadura registada com sucesso.");
            }
        } catch (ParseException | SQLException e) {
            System.out.println("\nFalha em registar a operação de semeadura.\n" + e.getMessage());
        }
    }


    public static void validateEndDate(Date dataInicio, String dataFinal) {
        Utils.validateDate(dataFinal);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void validateOperationDate(String dataOp) {
        Utils.validateDate(dataOp);
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
