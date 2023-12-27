package project.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Rega {
    private String idSetor;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private LocalDate data;
    private String receita;

    public Rega(String idSetor, LocalTime horaInicio, LocalTime horaFim, LocalDate data) {
        this.idSetor = idSetor;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.data = data;
    }

    public Rega(String idSetor, LocalTime horaInicio, LocalTime horaFim, LocalDate data, String receita) {
        this.idSetor = idSetor;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.data = data;
        this.receita = receita;
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

    public String getReceita() {
        return receita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rega that = (Rega) o;
        return Objects.equals(idSetor, that.idSetor) && Objects.equals(horaInicio, that.horaInicio) && Objects.equals(horaFim, that.horaFim) && Objects.equals(data, that.data) && Objects.equals(receita, that.receita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idSetor, horaInicio, horaFim, data, receita);
    }


    @Override
    public String toString() {
        if (receita == null) {
            return "Rega{" +
                    "idSetor='" + idSetor + '\'' +
                    ", horaInicio=" + horaInicio +
                    ", horaFim=" + horaFim +
                    ", data=" + data +
                    '}';
        } else {
            return "Rega{" +
                    "idSetor='" + idSetor + '\'' +
                    ", horaInicio=" + horaInicio +
                    ", horaFim=" + horaFim +
                    ", data=" + data +
                    ", receita=" + data +
                    '}';
        }
    }


}
