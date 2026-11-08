package com.example.projectdroid_1220893_1241190.service;

import android.content.Context;

import com.example.projectdroid_1220893_1241190.dto.FuncaoDTO;
import com.example.projectdroid_1220893_1241190.dto.FuncionarioDTO;
import com.example.projectdroid_1220893_1241190.handler.JsonHandler;
import com.example.projectdroid_1220893_1241190.handler.NetworkHandler;

import org.json.JSONObject;

import java.util.ArrayList;

public class FuncionarioService {

    public static ArrayList<FuncionarioDTO> obterFuncionarios(Context ctx) throws Exception {
        String json = NetworkHandler.get(ctx, "pessoas");
        return new ArrayList<>(JsonHandler.deSerializeJson2ListFuncionarioDTO(json));
    }

    public static ArrayList<FuncaoDTO> obterFuncao(Context ctx) throws Exception{
        String json = NetworkHandler.get(ctx, "funcao");
        return new ArrayList<>(JsonHandler.deSeralizeJson2ListFuncaoDTO(json));
    }

    public static String adicionarFuncionario(Context ctx, FuncionarioDTO dto) throws Exception{
        String body = JsonHandler.serializeFuncionarioDTO2Json(dto);
        String resposta = NetworkHandler.post(ctx, "pessoas", body);
        return new JSONObject(resposta).getString("_id");
    }
}
