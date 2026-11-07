package com.example.projectdroid_1220893_1241190.model;

public class Funcionario {

    private String id;
    private String nome;
    private Funcao funcao;
    private String email;
    private String morada;
    private int nif;
    private int nCartao;
    private double salario;

    public Funcionario(String id, String nome, double salario, int nCartao, int nif, String morada, String email, Funcao funcao) {
        this.id = id;
        this.nome = nome;
        this.salario = salario;
        this.nCartao = nCartao;
        this.nif = nif;
        this.morada = morada;
        this.email = email;
        this.funcao = funcao;
    }
}