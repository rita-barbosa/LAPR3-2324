package project.domain;

import java.util.Objects;

public class Hub {
    private String numId;
    private GpsCoordinates coordenadas;

    public Hub(String numId, GpsCoordinates coordenadas) {
        this.numId = numId;
        this.coordenadas = coordenadas;
    }

    public Hub(String numId, Double lat, Double lon) {
        this.numId = numId;
        this.coordenadas = new GpsCoordinates(lat, lon);
    }

    public Hub(String numId) {
        this.numId = numId;
    }

    public String getNumId() {
        return numId;
    }

    public GpsCoordinates getCoordenadas() {
        return coordenadas;
    }

    public boolean hasNumId(String id) {
        return id.compareTo(this.numId) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hub hub = (Hub) o;
        return Objects.equals(numId, hub.numId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numId);
    }

    @Override
    public String toString() {
        return "[" + numId + " - " + coordenadas.toString() + "]";
    }
}
