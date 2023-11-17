package project.domain;


import java.text.SimpleDateFormat;
import java.util.Date;

public class Operacao {

    private final int idOperacao;

    private final String designacaoOperacaoAgricola;

    private final String designacaoUnidade;
    private final Double quantidade;
    private final Date dataOperacao;

    public Operacao(int idOperacao, String designacaoOperacaoAgricola, String designacaoUnidade, Double quantidade, Date dataOperacao) {
        this.idOperacao = idOperacao;
        this.designacaoOperacaoAgricola = designacaoOperacaoAgricola;
        this.designacaoUnidade = designacaoUnidade;
        this.quantidade = quantidade;
        this.dataOperacao = dataOperacao;
    }


    public int getIdOperacao() {
        return idOperacao;
    }

    public String getDesignacaoOperacaoAgricola() {
        return designacaoOperacaoAgricola;
    }

    public String getDesignacaoUnidade() {
        return designacaoUnidade;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public Date getDataOperacao() {
        return dataOperacao;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(dataOperacao);

        return String.format("| %4d | %22s | %10s | %10d | %7s |",
                idOperacao, designacaoOperacaoAgricola, formattedDate, quantidade, designacaoUnidade);
    }

}
