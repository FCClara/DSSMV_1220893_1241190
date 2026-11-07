package com.example.projectdroid_1220893_1241190.dto;

public class TipoDTO {

     private String tipo;
     private String id;

     public TipoDTO(){}

    public TipoDTO(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getId(){
         return id;
    }

    public void setId(String id){
         this.id = id;
    }
}
