package project;

public class Edificio {

    private int edificioID;
     private String designacao;
     private double area;
     private TipoDeEdificio tipoDeEdificio;

    public Edificio(int edificioID, String designacao, double area, TipoDeEdificio tipoDeEdificio) {
        this.edificioID = edificioID;
        this.designacao = designacao;
        this.area = area;
        this.tipoDeEdificio = tipoDeEdificio;
    }
}
