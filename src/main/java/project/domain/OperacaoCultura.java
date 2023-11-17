package project.domain;


import java.util.Date;

public class OperacaoCultura extends Operacao{

    private Integer idParcela;
    private Integer idCultura;
    private Date dataInicial;
    private Date dataFinal;

    public OperacaoCultura(int idOperacao, String designacaoOperacaoAgricola, Date dataOperacao, int idParcela, int idCultura, Date dataInicial, Date dataFinal, String designacaoUnidade, double quantidade) {
        super(idOperacao, designacaoOperacaoAgricola,designacaoUnidade, quantidade, dataOperacao);
        this.idCultura = idCultura;
        this.idParcela = idParcela;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public OperacaoCultura(int idOperacao, String designacaoOperacaoAgricola, Date dataOperacao, int idParcela, int idCultura, Date dataInicial, String designacaoUnidade, double quantidade) {
        super(idOperacao, designacaoOperacaoAgricola,designacaoUnidade, quantidade, dataOperacao);
        this.idCultura = idCultura;
        this.idParcela = idParcela;
        this.dataInicial = dataInicial;
        this.dataFinal = null;
    }

    public Integer getIdParcela() {
        return idParcela;
    }

    public Integer getIdCultura() {
        return idCultura;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }
}
