package com.example.projectdroid_1220893_1241190.model;

public class Produto {


    private String id;
    private String  nome;
    private Tipo tipo;

    private double preco;

    private int quantidadeArmazem;

    private int quantidadeMontra;

    private Fornecedor fornecedor;

    //private String foto;

    private String barcode;


    public Produto(String id, String nome, Tipo tipo, double preco, int quantidadeArmazem, int quantidadeMontra, Fornecedor fornecedor, String barcode) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
        this.quantidadeArmazem = quantidadeArmazem;
        this.quantidadeMontra = quantidadeMontra;
        this.fornecedor = fornecedor;
        //this.foto = foto;
        this.barcode = barcode;
    }

    private boolean isNomeValid(String nome) {
        if (nome == null) {
            return false;
        }
        return nome.matches("^[\\p{L} ]+$");
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome){
        if (isNomeValid(nome)){
            this.nome = nome;}
        else{
            throw new IllegalArgumentException("Nome nao pode estar vazio");}
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo){
        if(tipo == null){
            throw new IllegalArgumentException("Tipo tem de ser selecionado.");
        }
        this.tipo = tipo;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco){
        if(preco < 0){
            throw new IllegalArgumentException("Preco tem de ser superior a 0.");
        }
        this.preco = preco;
    }

    public int getQuantidadeArmazem() {
        return quantidadeArmazem;
    }

    public void setQuantidadeArmazem(int quantidadeArmazem){
        if(quantidadeArmazem < 0){
            throw new IllegalArgumentException("Quantidade nao pode ser inferior a zero;");
        }
        this.quantidadeArmazem = quantidadeArmazem;
    }

    public int getQuantidadeMontra() {
        return quantidadeMontra;
    }

    public void setQuantidadeMontra(int quantidadeMontra) {
        if (quantidadeMontra < 0){
            throw new IllegalArgumentException("Quantidade Montra nao pode ser inferior a zero");
        }
        this.quantidadeMontra = quantidadeMontra;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        if(fornecedor == null){
            throw new IllegalArgumentException("Tem de selecionar um fornecedor");
        }
        this.fornecedor = fornecedor;
    }

    //public String getFoto() {return foto;}

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
}
