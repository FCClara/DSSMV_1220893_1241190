package com.example.projectdroid_1220893_1241190.dto;

public class DiaDTO {
    private String id;
    private String dia;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    @Override
    public String toString() {
        return "DiaDTO{id=" + id + ", dia=" + dia + "}";
    }
}
