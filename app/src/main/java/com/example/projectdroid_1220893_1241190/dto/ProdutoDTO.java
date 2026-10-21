package com.example.projectdroid_1220893_1241190.dto;

import com.example.projectdroid_1220893_1241190.model.Tipo;

public class ProdutoDTO {
    private String nome;
    private Object tipo;
    private double preco;
    private int quantidadeArmazem;
    private int quantidadeMontra;
    private Object fornecedor;
    //private String foto;


    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Object getTipo() { return tipo; }
    public void setTipo(Object tipo) { this.tipo = tipo; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getQuantidadeArmazem() { return quantidadeArmazem; }
    public void setQuantidadeArmazem(int quantidadeArmazem) { this.quantidadeArmazem = quantidadeArmazem; }

    public int getQuantidadeMontra() { return quantidadeMontra; }
    public void setQuantidadeMontra(int quantidadeMontra) { this.quantidadeMontra = quantidadeMontra; }

    public Object getFornecedor() { return fornecedor; }
    public void setFornecedor(Object fornecedor) { this.fornecedor = fornecedor; }

    //public String getFoto() { return foto; }
    //public void setFoto(String foto) { this.foto = foto; }
}