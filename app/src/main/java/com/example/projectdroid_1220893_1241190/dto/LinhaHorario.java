package com.example.projectdroid_1220893_1241190.dto;

public class LinhaHorario {
    private final String primeiraColuna;
    private final String horaEntrada;
    private final String horaSaida;

    public LinhaHorario(String primeiraColuna, String horaEntrada, String horaSaida) {
        this.primeiraColuna = primeiraColuna;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
    }

    public String getPrimeiraColuna() { return primeiraColuna; }
    public String getHoraEntrada() { return horaEntrada; }
    public String getHoraSaida() { return horaSaida; }
}