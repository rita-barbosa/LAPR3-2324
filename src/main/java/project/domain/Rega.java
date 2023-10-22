package project.domain;
import java.util.Objects;

public class Rega {

    private String idParcela;
    private  Integer tempoRega;
    private String regularidade;

    public Rega(String idParcela, Integer tempoRega, String regularidade) {
        this.idParcela = idParcela;
        this.tempoRega = tempoRega;
        this.regularidade = regularidade;
    }

    public Integer getTempoRega() {
        return tempoRega;
    }

    public void setTempoRega(Integer tempoRega) {
        this.tempoRega = tempoRega;
    }

    public String getRegularidade() {
        return regularidade;
    }

    public void setRegularidade(String regularidade) {
        this.regularidade = regularidade;
    }

    public String getIdParcela() {
        return idParcela;
    }

    public void setIdParcela(String idParcela) {
        this.idParcela = idParcela;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rega that = (Rega) o;
        return Objects.equals(idParcela, that.idParcela) && Objects.equals(tempoRega, that.tempoRega) && Objects.equals(regularidade, that.regularidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idParcela, tempoRega, regularidade);
    }

}
