package com.example.projectdroid_1220893_1241190.model;


public class Horario {

    private Funcionario funcionario;

    private Turno TurnoSegunda;
    private Turno TurnoTerca;
    private Turno TurnoQuarta;

    private Turno TurnoQuinta;

    private Turno TurnoSexta;

    private Turno TurnoSabado;

    private Turno TurnoDomingo;

    public Horario(Funcionario funcionario, Turno turnoSegunda, Turno turnoTerca, Turno turnoQuarta, Turno turnoQuinta, Turno turnoSexta, Turno turnoSabado, Turno turnoDomingo) {
        this.funcionario = funcionario;
        TurnoSegunda = turnoSegunda;
        TurnoTerca = turnoTerca;
        TurnoQuarta = turnoQuarta;
        TurnoQuinta = turnoQuinta;
        TurnoSexta = turnoSexta;
        TurnoSabado = turnoSabado;
        TurnoDomingo = turnoDomingo;
    }

    public Funcionario getFuncionario(){
        return funcionario;
    }
    public void setFuncionario(Funcionario funcionario){
        this.funcionario = funcionario;
    }

    public Turno getTurnoSegunda() {
        return TurnoSegunda;
    }

    public void setTurnoSegunda(Turno turnoSegunda) {
        TurnoSegunda = turnoSegunda;
    }

    public Turno getTurnoTerca() {
        return TurnoTerca;
    }

    public void setTurnoTerca(Turno turnoTerca) {
        TurnoTerca = turnoTerca;
    }

    public Turno getTurnoQuarta() {
        return TurnoQuarta;
    }

    public void setTurnoQuarta(Turno turnoQuarta) {
        TurnoQuarta = turnoQuarta;
    }

    public Turno getTurnoQuinta() {
        return TurnoQuinta;
    }

    public void setTurnoQuinta(Turno turnoQuinta) {
        TurnoQuinta = turnoQuinta;
    }

    public Turno getTurnoSexta() {
        return TurnoSexta;
    }

    public void setTurnoSexta(Turno turnoSexta) {
        TurnoSexta = turnoSexta;
    }

    public Turno getTurnoSabado() {
        return TurnoSabado;
    }

    public void setTurnoSabado(Turno turnoSabado) {
        TurnoSabado = turnoSabado;
    }

    public Turno getTurnoDomingo() {
        return TurnoDomingo;
    }

    public void setTurnoDomingo(Turno turnoDomingo) {
        TurnoDomingo = turnoDomingo;
    }
}
