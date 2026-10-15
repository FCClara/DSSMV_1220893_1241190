package com.example.projectdroid_1220893_1241190.service;

import com.example.projectdroid_1220893_1241190.*;
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

    public static ArrayList<String> obterTipos()
            throws Exception {

        //esta a ir buscar o url da nossa database e a adicionar tipo no final
        //apiconfig tem o nosso url

        URL url = new URL(
                ApiConfig.BASE_URL + "tipo"
        );

        //para fazer a conexcao
        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        //queremos um get pq estamos a ir buscar informacao
        connection.setRequestMethod("GET");
        connection.setRequestProperty(
                "Accept",
                "application/json"
        );

        //aqui mandamos a key para podermos acessar e verificar que temos permissao disso

        connection.setRequestProperty(
                "x-apikey",
                ApiConfig.API_KEY
        );

        //ta a ler a resposta mandada pela database
        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );
        //aqui ele guarda a resposta
        StringBuilder response =
                new StringBuilder();

        String line;

        //e lê linha a linha
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }

        //ja nao tem mais linhas ent acaba
        reader.close();

        //converte a resposta para um array json
        //json é o formato comum que se usa para mandar dados entre servidores
        JSONArray jsonArray =
                new JSONArray(response.toString());

        //cria array list e ta a adicionar  os que tem como valor tipo
        //assim se ele estivesse a receber mais informacao, mas nao fosse com o valor tipo, nao adiciona

        ArrayList<String> tipos =
                new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject objeto =
                    jsonArray.getJSONObject(i);

            String tipo =
                    objeto.getString("tipo");

            tipos.add(tipo);
        }

        connection.disconnect();

        return tipos;
    }

    public static boolean existeTipo(String nomeTipo) throws Exception {
        ArrayList<String> tipos = obterTipos();
        for (String t : tipos) {
            if (t != null && t.equalsIgnoreCase(nomeTipo)) {
                return true;
            }
        }
        return false;
    }

    public static void adicionarTipo(Tipo tipo)
            throws Exception {

        URL url = new URL(
                ApiConfig.BASE_URL + "tipo"
        );

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");

        connection.setRequestProperty(
                "Content-Type",
                "application/json"
        );

        connection.setRequestProperty(
                "Accept",
                "application/json"
        );

        connection.setRequestProperty(
                "x-apikey",
                ApiConfig.API_KEY
        );

        connection.setDoOutput(true);

        JSONObject json = new JSONObject();

        json.put("tipo", tipo.getTipo());

        OutputStream outputStream =
                connection.getOutputStream();

        outputStream.write(
                json.toString().getBytes("UTF-8")
        );

        outputStream.close();

        int responseCode =
                connection.getResponseCode();

        connection.disconnect();

        if (responseCode < 200 || responseCode >= 300) {
            throw new Exception(
                    "Erro ao adicionar tipo: "
                            + responseCode
            );
        }
    }
}