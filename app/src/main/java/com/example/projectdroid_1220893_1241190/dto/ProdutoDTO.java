package com.example.projectdroid_1220893_1241190.dto;

import com.example.projectdroid_1220893_1241190.model.Tipo;

public class ProdutoDTO {
    public ProdutoDTO() {}

    public ProdutoDTO(Tipo tipo, String nome, double preco) {
        this.tipo = tipo;
        this.nome = nome;
        this.preco = preco;
    }

    private Tipo tipo;
    private String nome;
    private double preco;

    // Getters e setters para os atributos

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

}
