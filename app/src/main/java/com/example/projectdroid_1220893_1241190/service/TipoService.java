package com.example.projectdroid_1220893_1241190.service;

import android.content.Context;

import com.example.projectdroid_1220893_1241190.*;
import com.example.projectdroid_1220893_1241190.dto.TipoDTO;
import com.example.projectdroid_1220893_1241190.handler.JsonHandler;
import com.example.projectdroid_1220893_1241190.handler.NetworkHandler;
import com.example.projectdroid_1220893_1241190.model.Tipo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class TipoService {

    public static ArrayList<TipoDTO> obterTipos(Context cxt)
            throws Exception {
        String json = NetworkHandler.get(cxt, "tipo");
        return new ArrayList<>(JsonHandler.deSerializeJson2ListTipoDto(json));
    }

    public static boolean existeTipo(String nomeTipo, Context ctx) throws Exception {
        ArrayList<TipoDTO> tipos = obterTipos(ctx);

        if (tipos == null) {
            return false;
        }

        //este equalsIgnoreCase(nomeTipo) é para quando adicionarmos,
        //ele verifica se existe ja, independetemente as mudancas de maiuscula ou miniscula
        //ou seja, temos bebida, e escrevemos Bebida, ele vai notar que é igual ent nao adiciona
        for (TipoDTO t : tipos) {
            if (t != null && t.getTipo() != null && t.getTipo().equalsIgnoreCase(nomeTipo)) {
                return true;
            }
        }
        return false;
    }

    public static void adicionarTipo(Context ctx, TipoDTO dto) throws Exception {
        String body = JsonHandler.serializeTipoDTO2Json(dto);   // DTO -> JSON string
        NetworkHandler.post(ctx, "tipo", body);                 // body goes here
    }
}