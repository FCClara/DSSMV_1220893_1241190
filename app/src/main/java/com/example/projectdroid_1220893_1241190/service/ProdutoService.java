package com.example.projectdroid_1220893_1241190.service;

import android.content.Context;

import com.example.projectdroid_1220893_1241190.*;
import com.example.projectdroid_1220893_1241190.dto.ProdutoDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
public class ProdutoService {

    public static ArrayList<ProdutoDTO> obterProdutos(Context ctx) throws Exception {
        URL url = new URL(ApiConfig.getBaseUrl(ctx) + "produto");

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("x-apikey", ApiConfig.getApiKey(ctx));

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        android.util.Log.d("LOG_SERVICE", "Raw JSON Response: " + response.toString());

        JSONArray jsonArray = new JSONArray(response.toString());
        ArrayList<ProdutoDTO> produtos = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject objeto = jsonArray.getJSONObject(i);

            ProdutoDTO dto = new ProdutoDTO();
            dto.setNome(objeto.optString("nome", ""));
            dto.setPreco(objeto.optDouble("preco", 0.0));
            dto.setQuantidadeArmazem(objeto.optInt("quantidadeArmazem", 0));
            dto.setQuantidadeMontra(objeto.optInt("quantidadeMontra", 0));
            //dto.setFoto(objeto.optString("foto", "");


            if (objeto.has("tipo") && !objeto.isNull("tipo")) {
                dto.setTipo(objeto.get("tipo"));
            }
            if (objeto.has("fornecedor") && !objeto.isNull("fornecedor")) {
                dto.setFornecedor(objeto.get("fornecedor"));
            }

            produtos.add(dto);
        }

        connection.disconnect();
        return produtos;
    }
}
