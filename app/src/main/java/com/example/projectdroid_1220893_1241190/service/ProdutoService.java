package com.example.projectdroid_1220893_1241190.service;

import android.content.Context;

import com.example.projectdroid_1220893_1241190.*;
import com.example.projectdroid_1220893_1241190.dto.*;

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

            android.util.Log.d("DEBUG_JSON", "--- Produto: " + objeto.optString("nome") + " ---");
            android.util.Log.d("DEBUG_JSON", "Tipo raw info: " + objeto.opt("tipo"));
            android.util.Log.d("DEBUG_JSON", "Fornecedor raw info: " + objeto.opt("fornecedor"));
            ProdutoDTO dto = new ProdutoDTO();
            dto.setNome(objeto.optString("nome", ""));
            dto.setPreco(objeto.optDouble("preco", 0.0));
            dto.setQuantidadeArmazem(objeto.optInt("quantidadeArmazem", 0));
            dto.setQuantidadeMontra(objeto.optInt("quantidadeMontra", 0));
            //dto.setFoto(objeto.optString("foto", "");

            String nomeTipo = extractNameFromJson(objeto.opt("tipo"), "tipo");
            TipoDTO tipoDTO = new TipoDTO();
            tipoDTO.setTipo(nomeTipo);
            dto.setTipo(tipoDTO);

            String nomeFornecedor = extractNameFromJson(objeto.opt("fornecedor"), "nome");
            FornecedorDTO fornecedorDTO = new FornecedorDTO();
            fornecedorDTO.setNome(nomeFornecedor);
            dto.setFornecedor(fornecedorDTO);

            android.util.Log.d("DEBUG_PRODUTO", "Produto Deserializado -> Nome: " + dto.getNome() +
                    ", Preco: " + dto.getPreco() +
                    ", Armazem: " + dto.getQuantidadeArmazem() +
                    ", Montra: " + dto.getQuantidadeMontra() +
                    ", Tipo: " + (dto.getTipo() != null ? dto.getTipo().getTipo() : "null") +
                    ", Fornecedor: " + (dto.getFornecedor() != null ? dto.getFornecedor().getNome() : "null"));
            produtos.add(dto);
        }

        connection.disconnect();
        return produtos;
    }

    //como o tipo vem como id, tipo, data criada etc, este extractNameFromJson,
    //vai buscar apenas o tipo, e nao o resto da info
    //same com o forncedor
    //usar isto em outras etentidades que necessitam, poupa codigo :)

    //esta keyToFind, é o nome do atributo, nos estamos a mandar isto em cima no dto do produto
    private static String extractNameFromJson(Object obj, String keyToFind) {
        if (obj == null) return "";
        try {
            if (obj instanceof JSONArray) {
                JSONArray array = (JSONArray) obj;
                if (array.length() > 0) {
                    JSONObject item = array.getJSONObject(0);
                    return item.optString(keyToFind, "");
                }
            } else if (obj instanceof JSONObject) {
                return ((JSONObject) obj).optString(keyToFind, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj.toString();
    }
}
