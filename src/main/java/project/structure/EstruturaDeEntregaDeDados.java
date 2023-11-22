package project.structure;

import project.domain.Local;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;

public class EstruturaDeEntregaDeDados {

    private int distanciaTotal;
    private LinkedList<Local> percurso;
    private ArrayList<Integer> carregamentos;
    private boolean flag;

    public EstruturaDeEntregaDeDados(int distanciaTotal, LinkedList<Local> percurso, ArrayList<Integer> carregamentos, boolean flag) {
        this.distanciaTotal = distanciaTotal;
        this.percurso = percurso;
        this.carregamentos = carregamentos;
        this.flag = flag;
    }

    public int getDistanciaTotal() {
        return distanciaTotal;
    }

    public void setDistanciaTotal(int distanciaTotal) {
        this.distanciaTotal = distanciaTotal;
    }

    public LinkedList<Local> getPercurso() {
        return percurso;
    }

    public void setPercurso(LinkedList<Local> percurso) {
        this.percurso = percurso;
    }

    public ArrayList<Integer> getCarregamentos() {
        return carregamentos;
    }

    public void setCarregamentos(ArrayList<Integer> carregamentos) {
        this.carregamentos = carregamentos;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }
}
