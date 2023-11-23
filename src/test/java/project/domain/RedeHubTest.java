package project.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.exception.ExcecaoFicheiro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;
import static project.domain.RedeHub.getBiggestDist;
import static project.domain.RedeHub.getShortestPathForFurthestNodes;

class RedeHubTest {

    @BeforeEach
    void setUp() throws ExcecaoFicheiro, IOException {
        ImportarFicheiro.importRedeDistribuicao("files/locais_small.csv","files/distancias_small.csv");
    }

    @Test
    void getInstance() {
    }

    @Test
    void addHub() {
    }

    @Test
    void addRoute() {
    }

    @Test
    void testToString() {
    }

    @Test
    void getRedeDistribuicao() {
    }

    @Test
    void calculateInfluence() {
    }

    @Test
    void calculateProximity() {
    }

    @Test
    void calculateCentrality() {
    }

    @Test
    void getShortestPathForFurthestNodesTestValid() {
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

        LinkedList<Local> path2 = getShortestPathForFurthestNodes();

        assertEquals(path1, path2);
    }

    @Test
    void getShortestPathForFurthestNodesTestInvalid() {
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
        path1.add(local4);
        path1.add(local3);
        path1.add(local5);
        path1.add(local6);
        path1.add(local7);

        LinkedList<Local> path2 = getShortestPathForFurthestNodes();

        assertNotEquals(path1, path2);
    }

    @Test
    void getBiggestDistTestValid1() {
        ArrayList<Integer> dist = new ArrayList<>();
        dist.add(0);

        assertEquals(0, getBiggestDist(dist));
    }

    @Test
    void getBiggestDistTestValid3() {
        ArrayList<Integer> dist = new ArrayList<>();
        dist.add(1);
        dist.add(0);
        dist.add(8000);

        assertEquals(2, getBiggestDist(dist));
    }

    @Test
    void getBiggestDistTestValid5() {
        ArrayList<Integer> dist = new ArrayList<>();
        dist.add(1234);
        dist.add(10000);
        dist.add(1);
        dist.add(0);
        dist.add(8000);

        assertEquals(1, getBiggestDist(dist));
    }

}