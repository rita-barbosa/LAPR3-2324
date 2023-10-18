package project;

import java.util.Objects;

public class TipoDeEdificio {
    private String designacao;

    public TipoDeEdificio(String designacao) {
        this.designacao = designacao;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoDeEdificio that = (TipoDeEdificio) o;
        return Objects.equals(designacao, that.designacao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(designacao);
    }
}
