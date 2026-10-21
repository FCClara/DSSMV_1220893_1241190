package com.example.projectdroid_1220893_1241190.model;

public class Tipo {

    private String tipo;

    private boolean isTipoValid(String tipo) {
        if (tipo == null) {
            return false;
        }
        // é para todos os caracteres alfabeticos, includi Ç e acetos
        return tipo.matches("^[\\p{L}]+$");
    }

    public Tipo(String tipo){
        setTipo(tipo);
    }

    public String getTipos(){ return this.tipo;}

    public void setTipo(String tipo){
        if(isTipoValid(tipo)){
            this.tipo = tipo;
        } else {
            throw new IllegalArgumentException("So pode conter caracteres alfabeticos.");        }
    }
}