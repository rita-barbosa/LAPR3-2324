package project.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Rega {

    private String idSetor;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private LocalDate data;

    public Rega(String idSetor, LocalTime horaInicio, LocalTime horaFim, LocalDate data) {
        this.idSetor = idSetor;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.data = data;
    }

    public String getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(String idSetor) {
        this.idSetor = idSetor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rega that = (Rega) o;
        return Objects.equals(idSetor, that.idSetor) && Objects.equals(horaInicio, that.horaInicio) && Objects.equals(horaFim, that.horaFim) && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSetor, horaInicio, horaFim, data);
    }


      @Override
    public String toString() {
        return "Rega{" +
                "idSetor='" + idSetor + '\'' +
                ", horaInicio=" + horaInicio +
                ", horaFim=" + horaFim +
                ", data=" + data +
                '}';
    }


}
