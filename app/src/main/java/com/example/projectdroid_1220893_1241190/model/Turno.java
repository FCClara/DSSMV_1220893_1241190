package com.example.projectdroid_1220893_1241190.model;

public class Turno {

    private String id;
    private Dia dia;
    private String HoraEntrada;
    private String HoraSaida;

    public Turno(String id, Dia dia, String horaEntrada, String horaSaida) {
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
