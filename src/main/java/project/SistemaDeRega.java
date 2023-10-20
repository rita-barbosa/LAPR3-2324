package project;

import java.util.Set;

public class SistemaDeRega{

    private final ControladorRega controladorRega;

    private Set<PlanoDeRega> planoDeRegas;

    public SistemaDeRega( ControladorRega controlador) {
        this.controladorRega = controlador;
    }

    public boolean verificarRega(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ControladorRega getControladorRega() {
        return controladorRega;
    }

    public Set<PlanoDeRega> getPlanoDeRegas() {
        return planoDeRegas;
    }
}
