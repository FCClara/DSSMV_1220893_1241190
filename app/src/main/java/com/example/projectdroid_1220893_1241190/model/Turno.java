package com.example.projectdroid_1220893_1241190.model;

import java.util.regex.Pattern;

public class Turno {

    private static final Pattern HORA_PATTERN =
            Pattern.compile("^([01]\\d|2[0-3]):[0-5]\\d$");
    private String id;
    private Dia dia;
    private String HoraEntrada;
    private String HoraSaida;

    public Turno(String id, Dia dia, String horaEntrada, String horaSaida) {

        if (dia == null) throw new IllegalArgumentException("Dia invalido");

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
        if(dia == null) throw new IllegalArgumentException("Dia invalido");
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
