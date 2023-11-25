package project.ui.console.operacoes;

import project.controller.RegistarOperacaoColheitaController;
import project.ui.console.utils.Utils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class RegistarOperacaoColheitaUI implements Runnable {

    private RegistarOperacaoColheitaController controller;

    //Pergunta se que a colheita é final, ou seja se é para fechar uma cultura.
    //apenas ir a OperacaoCultura
    //ver a cena da as culturas terem de ter ou não uma dataFim, após ser feita a colheita
    //talvez só mostrar as culturas que não tenham data final.

    private Integer idOperacao;
    private String designacaoOperacaoAgricola;
    private String designacaoUnidade;
    private Double quantidade;
    private Date dataOperacao;
    private Integer idParcela;
    private Integer idCultura;
    private Date dataInicio; //vou buscar à data que já tenho na base de dados
    private Date dataFim;   //perguntar se quer colocar dataFim ou não!!!
    private int opcaoCultura;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public RegistarOperacaoColheitaUI(){
        controller = new RegistarOperacaoColheitaController();
    }

    @Override
    public void run() {
        try {
            int index;
            System.out.println("-----------------------------------------");
            System.out.println("Registar uma nova Operação de Colheita");
            System.out.println("-----------------------------------------");

            Scanner scanner = new Scanner(System.in);

//            Map<BigDecimal, String> fieldsIDs = controller.getFieldsIDs();
//            BigDecimal bigDecimalValue = Utils.showAndSelectIndex(fieldsIDs, "Selecione o id da parcela:");
//            idParcela = bigDecimalValue.intValue();
//
//            Map<BigDecimal, String> cultureIDs = controller.getCulturesIDs();
//            bigDecimalValue = Utils.showAndSelectIndex(cultureIDs, "Selecione o id da cultura:");
//            idCultura = bigDecimalValue.intValue();

            List<String> unitTypes = controller.getUnitTypes();
            index = Utils.showAndSelectIndex(unitTypes, "Tipo de Unidade:");
            designacaoUnidade = unitTypes.get(index);

            //IR BUSCAR A DATA INICIAL DA CULTURA À DASE DE DADOS
            System.out.print("Data do início da operação: ");
            String dataInicial = scanner.next();
            Utils.validateDate(dataInicial);
            dataInicio = formatter.parse(dataInicial);

            quantidade = Utils.readDoubleFromConsole("Quantidade: ");

            System.out.print("Data da Operação (dd-mm-yyyy): ");
            String dataOp = scanner.next();
            Utils.validateDate(dataOp);
            dataOp = dataOp.replace("/", "-");
            dataOperacao = formatter.parse(dataOp);

            opcaoCultura = Utils.selectYesOrNoOption();

            if (opcaoCultura == 1){
                dataFim = dataOperacao;
            } else {
                dataFim = null;
            }

            //QUER TERMINAR A CULTURA??? (ADICIONAR DATA FINAL)
            //SE QUISER ELE ESCOLHE
            //SE NÃO QUISER VAI COMO NULL ??? -ANALISAR BEM O RELACIONAL
//            System.out.print("Data do fim da operação (se ainda não houver selecionar ENTER): ");
//            String dataFinal = scanner.next();
//            Utils.validateDate(dataFinal);
//            dataFinal = dataFinal.replace("/", "-");
//            dataFim = formatter.parse(dataFinal);
//            if (!(dataFinal.isBlank()) && !(dataFinal.isEmpty())){
//                validateEndDate(dataInicio, dataFinal);
//                dataFim = formatter.parse(dataFinal);
//            }


            controller.registerOperation(idParcela, idCultura, dataOperacao, dataInicio, dataFim, designacaoUnidade, quantidade);
            System.out.println("\nOperação de colheita registada com sucesso.");
        } catch (ParseException | SQLException e) {
            System.out.println("\nFalha em registar a operação de colheita.\n" + e.getMessage());
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
