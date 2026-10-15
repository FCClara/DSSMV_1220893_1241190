package com.example.projectdroid_1220893_1241190.service;

import com.example.projectdroid_1220893_1241190.*;
import com.example.projectdroid_1220893_1241190.model.Produto;
import com.example.projectdroid_1220893_1241190.model.Tipo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
public class ProdutoService {

    public static ArrayList<Produto> obterProdutos()
        throws Exception {

        URL url = new URL(
                ApiConfig.BASE_URL + "produto"
        );

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty(
                "Accept",
                "application/json"
        );

        connection.setRequestProperty(
                "x-apikey",
                ApiConfig.API_KEY
        );

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

        StringBuilder response =
                new StringBuilder();

        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        reader.close();

        JSONArray jsonArray =
                new JSONArray(response.toString());

        ArrayList<Produto> produtos =
                new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject objeto =
                    jsonArray.getJSONObject(i);

            String nome = objeto.optString("nome", "");
            Tipo tipo = null;

            if (objeto.has("tipo") && !objeto.isNull("tipo")) {
                Object tipoObj = objeto.get("tipo");

                if (tipoObj instanceof JSONObject) {
                    // If RestDB embeds the full Tipo record
                    JSONObject tipoJson = (JSONObject) tipoObj;
                    String tipoNome = tipoJson.optString("tipo");
                    // Pass the parsed attributes to your Tipo constructor
                    tipo = new Tipo(tipoNome);
                } else if (tipoObj instanceof String) {
                    // If RestDB only returns the foreign key ID string
                    String tipoId = (String) tipoObj;
                    tipo = new Tipo(tipoId);
                }
            }

            Produto produto = new Produto(nome, tipo);
            produtos.add(produto);
        }

        connection.disconnect();

        return produtos;

    }
}
