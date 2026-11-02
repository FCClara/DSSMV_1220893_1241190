package com.example.projectdroid_1220893_1241190.model;

import java.time.LocalTime;

public class Turno {

    private String id;
    private Dia dia;
    private LocalTime HoraEntrada;
    private LocalTime HoraSaida;

    public Turno(String id, Dia dia, LocalTime horaEntrada, LocalTime horaSaida) {
        this.id = id;
        this.dia = dia;
        this.HoraEntrada = horaEntrada;
        this.HoraSaida = horaSaida;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Dia getDia() {
        return dia;
    }

    public void setDia(Dia dia) {
        this.dia = dia;
    }

    public LocalTime getHoraEntrada() {
        return HoraEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.HoraEntrada = horaEntrada;
    }

    public LocalTime getHoraSaida() {
        return HoraSaida;
    }

    public void setHoraSaida(LocalTime horaSaida) {
        this.HoraSaida = horaSaida;
    }
}
