package com.example.projectdroid_1220893_1241190.dto;

import java.time.LocalTime;

public class TurnoDTO {

    private String id;
    private DiaDTO dia;
    private String HoraEntrada;
    private String HoraSaida;

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}
    public DiaDTO getDia() {
        return dia;
    }

    public void setDia(DiaDTO dia) {
        this.dia = dia;
    }

    public String getHoraEntrada() {
        return HoraEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.HoraEntrada = horaEntrada;
    }

    public String getHoraSaida() {
        return HoraSaida;
    }

    public void setHoraSaida(String horaSaida) {
        this.HoraSaida = horaSaida;
    }
}
