package com.example.projectdroid_1220893_1241190.model;

public class Funcao {

    private String id;
    private String funcao;

    public Funcao(String id,String funcao) {
        this.id=id;
        this.funcao = funcao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
}
