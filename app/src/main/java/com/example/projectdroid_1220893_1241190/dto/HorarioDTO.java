package com.example.projectdroid_1220893_1241190.dto;

import java.util.HashMap;
import java.util.Map;

public class HorarioDTO {

    private String id;
    private FuncionarioDTO funcionario;
    private Map<String, String> turnoPorDia = new HashMap<>(); // diaId -> turnoId

    public HorarioDTO() {
    }

    public void setId(String id){this.id = id;}
    public String getId() { return id; }

    public FuncionarioDTO getFuncionario() { return funcionario; }
    public void setFuncionario(FuncionarioDTO funcionario) { this.funcionario = funcionario; }

    public void setTurno(String diaId, String turnoId) { turnoPorDia.put(diaId, turnoId); }
    public String getTurnoDoDia(String diaId) { return turnoPorDia.get(diaId); }
}