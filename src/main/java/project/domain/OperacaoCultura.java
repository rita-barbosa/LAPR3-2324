package project.domain;


import java.util.Date;

public class OperacaoCultura extends Operacao{

    private Integer idParcela;
    private Integer idCultura;
    private Date dataInicial;
    private Date dataFinal;

    public OperacaoCultura(Integer idOperacao, String designacaoOperacaoAgricola, Date dataOperacao, Integer idParcela, Integer idCultura, Date dataInicial, Date dataFinal, String designacaoUnidade, Double quantidade) {
        super(idOperacao, designacaoOperacaoAgricola,designacaoUnidade, quantidade, dataOperacao);
        this.idCultura = idCultura;
        this.idParcela = idParcela;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
    }

    public OperacaoCultura(Integer idOperacao, String designacaoOperacaoAgricola, Date dataOperacao, Integer idParcela, Integer idCultura, Date dataInicial, String designacaoUnidade, Double quantidade) {
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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(String.format(" %10d | %10d |  %10s  | %10s |", idParcela, idCultura, dataInicial.toString(), (dataFinal == null) ? "0000-00-00" : dataFinal.toString()));
        return  stringBuilder.toString() ;
    }
}
