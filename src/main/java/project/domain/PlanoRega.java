package project.domain;

import java.util.List;

public class PlanoRega {
    private List<Rega> planoDeRega;

    public PlanoRega(List<Rega> planoDeRega){
        this.planoDeRega = planoDeRega;
    }

    public List<Rega> getPlanoDeRega() {
        return planoDeRega;
    }

    public void setPlanoDeRega(List<Rega> planoDeRega) {
        this.planoDeRega = planoDeRega;
    }

}
