package com.example.projectdroid_1220893_1241190.service;

import android.content.Context;

import com.example.projectdroid_1220893_1241190.dto.FuncionarioDTO;
import com.example.projectdroid_1220893_1241190.handler.JsonHandler;
import com.example.projectdroid_1220893_1241190.handler.NetworkHandler;

import java.util.ArrayList;

public class FuncionarioService {

    public static ArrayList<FuncionarioDTO> obterFuncionarios(Context ctx) throws Exception {
        String json = NetworkHandler.get(ctx, "pessoas");
        return new ArrayList<>(JsonHandler.deSerializeJson2ListFuncionarioDTO(json));
    }
}
