package com.example.projectdroid_1220893_1241190.dto;

public class LinhaHorario {
    private final String funcionario;
    private final String horaEntrada;
    private final String horaSaida;

    public LinhaHorario(String funcionario, String horaEntrada, String horaSaida) {
        this.funcionario = funcionario;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
    }

    public String getFuncionario() { return funcionario; }
    public String getHoraEntrada() { return horaEntrada; }
    public String getHoraSaida() { return horaSaida; }
}