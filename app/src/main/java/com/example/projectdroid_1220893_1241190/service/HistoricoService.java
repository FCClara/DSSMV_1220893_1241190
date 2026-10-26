package com.example.projectdroid_1220893_1241190.service;

import android.content.Context;

import com.example.projectdroid_1220893_1241190.ApiConfig;
import com.example.projectdroid_1220893_1241190.dto.HistoricoDTO;
import com.example.projectdroid_1220893_1241190.dto.ProdutoDTO;
import com.example.projectdroid_1220893_1241190.handler.JsonHandler;
import com.example.projectdroid_1220893_1241190.handler.NetworkHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HistoricoService {

    public static ArrayList<HistoricoDTO> obterHistorico(Context ctx) throws Exception{
        String json = NetworkHandler.get(ctx,"historico");
        return new ArrayList<>(JsonHandler.deSerializeJson2ListHistoricoDTO(json));
    }

    public static void adicionarHistorico(Context ctx, HistoricoDTO dto) throws Exception {
        String body = JsonHandler.serialize2JsonHistorico(dto);
        NetworkHandler.post(ctx, "historico", body);
    }
}
