package com.example.projectdroid_1220893_1241190.model;

public class Historico {

    private Produto produto;

    private int quantidade;
    private String descricao;

    public Historico(Produto produto, int quantidade, String descricao) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.descricao = descricao;
    }

    public Produto getProduto() {
        if (produto == null){
            throw new IllegalArgumentException("Tem de ter produto");
        }
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if(descricao == null){
            throw new IllegalArgumentException("Tem de conter descricao");
        }
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if(quantidade < 0){
            throw new IllegalArgumentException("A quantidade tem de ser superior a 0");
        }
        this.quantidade = quantidade;
    }
}
