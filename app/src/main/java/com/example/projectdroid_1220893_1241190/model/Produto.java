package com.example.projectdroid_1220893_1241190.model;

public class Produto {

    private String  nome;
    private Tipo tipo;

    public Produto(String nome, Tipo tipo){ this.nome = nome; this.tipo= tipo;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        nome = nome;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }
}
