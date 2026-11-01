package com.example.projectdroid_1220893_1241190.model;

public class Funcionario {

    private String nome;
    private String funcao;
    private String email;
    private String morada;
    private int nif;
    private int nCartao;
    private double salario;

    public Funcionario(String nome, double salario, int nCartao, int nif, String morada, String email, String funcao) {
        this.nome = nome;
        this.salario = salario;
        this.nCartao = nCartao;
        this.nif = nif;
        this.morada = morada;
        this.email = email;
        this.funcao = funcao;
    }
}