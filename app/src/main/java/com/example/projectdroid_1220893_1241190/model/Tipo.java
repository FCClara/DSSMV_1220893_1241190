package com.example.projectdroid_1220893_1241190.model;

public class Tipo {

    private String tipo;

    private boolean isTipoValid(String tipo){
        if(tipo.length() != 4 && tipo.contains("[a-zA-z]") == true){
            return false;
        }
        return true;
    }

    public Tipo(String tipo){
        setTipo(tipo);
    }

    public String getTipos(){ return this.tipo;}

    public void setTipo(String tipo){
        if(isTipoValid(tipo)){
            this.tipo = tipo;
        } else {
            System.out.println("nao");
        }
    }
}