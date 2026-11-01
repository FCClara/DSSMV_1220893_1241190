package com.example.projectdroid_1220893_1241190.dto;


public class ProdutoDTO {
    private String id;
    private String nome;
    private TipoDTO tipo;
    private double preco;
    private int quantidadeArmazem;
    private int quantidadeMontra;
    private FornecedorDTO fornecedor;
    //private String foto;

    private String barcode;


    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public TipoDTO getTipo() { return tipo; }
    public void setTipo(TipoDTO tipo) { this.tipo = tipo; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    public int getQuantidadeArmazem() { return quantidadeArmazem; }
    public void setQuantidadeArmazem(int quantidadeArmazem) { this.quantidadeArmazem = quantidadeArmazem; }

    public int getQuantidadeMontra() { return quantidadeMontra; }
    public void setQuantidadeMontra(int quantidadeMontra) { this.quantidadeMontra = quantidadeMontra; }

    public FornecedorDTO getFornecedor() { return fornecedor; }
    public void setFornecedor(FornecedorDTO fornecedor) { this.fornecedor = fornecedor; }

    //public String getFoto() { return foto; }
    //public void setFoto(String foto) { this.foto = foto; }

    public String getBarcode() {return barcode;}
    public void setBarcode(String barcode) {this.barcode = barcode;}
}