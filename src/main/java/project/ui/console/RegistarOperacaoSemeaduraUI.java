package project.ui.console;

import project.controller.operacoes.RegistarOperacaoSemeaduraController;
import project.ui.console.utils.Utils;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RegistarOperacaoSemeaduraUI implements Runnable {

    private RegistarOperacaoSemeaduraController controller;

    private Date dataOperacao;
    private Integer idParcela;
    private Integer idCultura;
    private Date dataInicio;
    private Date dataFim = null;
    private String tipoUnidade;
    private Double quantidade;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

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

            Map<Integer, String>  fieldsIDs = controller.getFieldsIDs();  //MOSTRA LISTA DAS PARCELAS
            idParcela = (Integer) Utils.showAndSelectIndex(fieldsIDs, "Selecione o id da parcela:");

            Map<Integer, String> cultureIDs = controller.getCulturesIDs();  //MOSTRA LISTA DAS CULTURAS
            idCultura = (Integer) Utils.showAndSelectIndex(cultureIDs, "Selecione o id da cultura:");

            List<String> unitTypes = controller.getUnitTypes();  //MOSTRA LISTA DAS UNIDADES
            index = Utils.showAndSelectIndex(unitTypes, "Tipo de Unidade:");
            tipoUnidade = unitTypes.get(index);

            System.out.print("Data do início da operação: ");
            String dataInicial = scanner.next();
            Utils.validateDate(dataInicial);
            dataInicio = formatter.parse(dataInicial);

            quantidade = Utils.readDoubleFromConsole("Quantidade: ");

            System.out.print("Data do fim da operação (se ainda não houver selecionar ENTER): ");
            String dataFinal = scanner.next();
            if (!(dataFinal.isBlank()) && !(dataFinal.isEmpty())){
                validateEndDate(dataInicio, dataFinal);
                dataFim = formatter.parse(dataFinal);
            }

            System.out.print("Data da Operação (dd-mm-yyyy): ");
            String dataOp = scanner.next();
            validateOperationDate(dataOp);
            dataOperacao =formatter.parse(dataOp);

            controller.registerOperation(idParcela, idCultura, dataOperacao, dataInicio, dataFim, tipoUnidade, quantidade);
            System.out.println("\nOperação de semeadura registada com sucesso.");
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
