package com.example.projectdroid_1220893_1241190.model;

public class Fornecedor {

    private String nome;

    public Fornecedor(String nome){
        setNome(nome);
    }

    private boolean isNomeValid(String nome) {
        if (nome == null) {
            return false;
        }
        return nome.matches("^[\\p{L} ]+$");
    }
    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        if (isNomeValid(nome)){
            this.nome = nome;}
        else{
            throw new IllegalArgumentException("Nome nao pode estar vazio");
        }
    }
}
