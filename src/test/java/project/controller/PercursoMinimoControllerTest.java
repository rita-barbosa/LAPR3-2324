package project.controller;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.domain.*;
import project.exception.ExcecaoFicheiro;
import project.structure.EstruturaDeEntregaDeDados;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static project.controller.PercursoMinimoController.*;

class PercursoMinimoControllerTest {
    @BeforeEach
    public void setUp() throws ExcecaoFicheiro, IOException {
        ImportarFicheiro.importRedeDistribuicao("files/locais_small.csv","files/distancias_small.csv");
    }
    @Test
    void analyzeDataTestValid() {
        LinkedList<Local> path1 = new LinkedList<>();

        Local local1 = new Local("CT15", new CoordenadasGps(41.7, -8.8333));
        Local local2 = new Local("CT12", new CoordenadasGps(41.1495, -8.6108));
        Local local3 = new Local("CT1", new CoordenadasGps(40.6389, -8.6553));
        Local local4 = new Local("CT10", new CoordenadasGps(39.7444, -8.8072));
        Local local5 = new Local("CT13", new CoordenadasGps(39.2369, -8.685));
        Local local6 = new Local("CT14", new CoordenadasGps(38.5243, -8.8926));
        Local local7 = new Local("CT8", new CoordenadasGps(37.0161, -7.935));

        path1.add(local1);
        path1.add(local2);
        path1.add(local3);
        path1.add(local4);
        path1.add(local5);
        path1.add(local6);
        path1.add(local7);

        ArrayList<Integer> indexDeCarregamentos = new ArrayList<>();

        int distancia = 605261;

        boolean flag = true;

        EstruturaDeEntregaDeDados estruturaDeEntregaDeDados = new EstruturaDeEntregaDeDados(distancia,path1,indexDeCarregamentos,flag);
        EstruturaDeEntregaDeDados estruturaDeEntregaDeDadosFunction = analyzeData(650000);

        assertEquals(estruturaDeEntregaDeDados,estruturaDeEntregaDeDadosFunction);
    }

    @Test
    void analyzeDataTestInvalidPercurso() {
        LinkedList<Local> path1 = new LinkedList<>();

        Local local1 = new Local("CT15", new CoordenadasGps(41.7, -8.8333));
        Local local2 = new Local("CT12", new CoordenadasGps(41.1495, -8.6108));
        Local local3 = new Local("CT1", new CoordenadasGps(40.6389, -8.6553));
        Local local5 = new Local("CT13", new CoordenadasGps(39.2369, -8.685));
        Local local6 = new Local("CT14", new CoordenadasGps(38.5243, -8.8926));
        Local local7 = new Local("CT8", new CoordenadasGps(37.0161, -7.935));

        path1.add(local1);
        path1.add(local2);
        path1.add(local3);
        path1.add(local5);
        path1.add(local6);
        path1.add(local7);

        ArrayList<Integer> indexDeCarregamentos = new ArrayList<>();

        int distancia = 605261;

        boolean flag = true;

        EstruturaDeEntregaDeDados estruturaDeEntregaDeDados = new EstruturaDeEntregaDeDados(distancia,path1,indexDeCarregamentos,flag);
        EstruturaDeEntregaDeDados estruturaDeEntregaDeDadosFunction = analyzeData(650000);

        assertNotEquals(estruturaDeEntregaDeDados,estruturaDeEntregaDeDadosFunction);
    }

    @Test
    void analyzeDataTestInvalidDistancia() {
        LinkedList<Local> path1 = new LinkedList<>();

        Local local1 = new Local("CT15", new CoordenadasGps(41.7, -8.8333));
        Local local2 = new Local("CT12", new CoordenadasGps(41.1495, -8.6108));
        Local local3 = new Local("CT1", new CoordenadasGps(40.6389, -8.6553));
        Local local4 = new Local("CT10", new CoordenadasGps(39.7444, -8.8072));
        Local local5 = new Local("CT13", new CoordenadasGps(39.2369, -8.685));
        Local local6 = new Local("CT14", new CoordenadasGps(38.5243, -8.8926));
        Local local7 = new Local("CT8", new CoordenadasGps(37.0161, -7.935));

        path1.add(local1);
        path1.add(local2);
        path1.add(local3);
        path1.add(local4);
        path1.add(local5);
        path1.add(local6);
        path1.add(local7);

        ArrayList<Integer> indexDeCarregamentos = new ArrayList<>();

        int distancia = 6023;

        boolean flag = true;

        EstruturaDeEntregaDeDados estruturaDeEntregaDeDados = new EstruturaDeEntregaDeDados(distancia,path1,indexDeCarregamentos,flag);
        EstruturaDeEntregaDeDados estruturaDeEntregaDeDadosFunction = analyzeData(650000);

        assertNotEquals(estruturaDeEntregaDeDados,estruturaDeEntregaDeDadosFunction);
    }

    @Test
    void analyzeDataTestInvalidFlag() {
        LinkedList<Local> path1 = new LinkedList<>();

        Local local1 = new Local("CT15", new CoordenadasGps(41.7, -8.8333));
        Local local2 = new Local("CT12", new CoordenadasGps(41.1495, -8.6108));
        Local local3 = new Local("CT1", new CoordenadasGps(40.6389, -8.6553));
        Local local4 = new Local("CT10", new CoordenadasGps(39.7444, -8.8072));
        Local local5 = new Local("CT13", new CoordenadasGps(39.2369, -8.685));
        Local local6 = new Local("CT14", new CoordenadasGps(38.5243, -8.8926));
        Local local7 = new Local("CT8", new CoordenadasGps(37.0161, -7.935));

        path1.add(local1);
        path1.add(local2);
        path1.add(local3);
        path1.add(local4);
        path1.add(local5);
        path1.add(local6);
        path1.add(local7);

        ArrayList<Integer> indexDeCarregamentos = new ArrayList<>();

        int distancia = 605261;

        boolean flag = false;

        EstruturaDeEntregaDeDados estruturaDeEntregaDeDados = new EstruturaDeEntregaDeDados(distancia,path1,indexDeCarregamentos,flag);
        EstruturaDeEntregaDeDados estruturaDeEntregaDeDadosFunction = analyzeData(650000);

        assertNotEquals(estruturaDeEntregaDeDados,estruturaDeEntregaDeDadosFunction);
    }

}