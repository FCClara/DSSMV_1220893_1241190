package com.example.projectdroid_1220893_1241190.handler;

import com.example.projectdroid_1220893_1241190.dto.FornecedorDTO;
import com.example.projectdroid_1220893_1241190.dto.HistoricoDTO;
import com.example.projectdroid_1220893_1241190.dto.ProdutoDTO;
import com.example.projectdroid_1220893_1241190.dto.TipoDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonHandler {

    public static List<TipoDTO> deSerializeJson2ListTipoDto(String resp) throws JSONException{
        JSONArray jsonArray = new JSONArray(resp);

        ArrayList<TipoDTO> tipos = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject objeto = jsonArray.getJSONObject(i);

            TipoDTO dto = new TipoDTO();
            dto.setTipo(objeto.optString("tipo",""));

            tipos.add(dto);
        }
        return tipos;
    }

    //este é para adicionar, tranformas em json
    public static String serializeTipoDTO2Json(TipoDTO dto) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tipo", dto.getTipo());
        return jsonObject.toString();
    }
    public static List<ProdutoDTO> deSerializeJson2ListProdutoDto(String resp) throws JSONException{
        JSONArray jsonArray = new JSONArray(resp);
        List<ProdutoDTO> produtos = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject objeto = jsonArray.getJSONObject(i);

            /*android.util.Log.d("DEBUG_JSON", "--- Produto: " + objeto.optString("nome") + " ---");
            android.util.Log.d("DEBUG_JSON", "Tipo raw info: " + objeto.opt("tipo"));
            android.util.Log.d("DEBUG_JSON", "Fornecedor raw info: " + objeto.opt("fornecedor"));*/

            ProdutoDTO dto = new ProdutoDTO();
            dto.setId(objeto.optString("_id", ""));
            dto.setNome(objeto.optString("nome", ""));
            dto.setPreco(objeto.optDouble("preco", 0.0));
            dto.setQuantidadeArmazem(objeto.optInt("quantidadeArmazem", 0));
            dto.setQuantidadeMontra(objeto.optInt("quantidadeMontra", 0));
            //dto.setFoto(objeto.optString("foto", "");

            TipoDTO tipoDTO = new TipoDTO();
            tipoDTO.setTipo(extractString(objeto.opt("tipo"), "tipo"));
            dto.setTipo(tipoDTO);

            FornecedorDTO fornecedorDTO = new FornecedorDTO();
            fornecedorDTO.setNome(extractString(objeto.opt("fornecedor"), "nome"));
            dto.setFornecedor(fornecedorDTO);

            android.util.Log.d("DEBUG_PRODUTO", "Produto Deserializado -> Nome: " + dto.getNome() +
                    ", Preco: " + dto.getPreco() +
                    ", Armazem: " + dto.getQuantidadeArmazem() +
                    ", Montra: " + dto.getQuantidadeMontra() +
                    ", Tipo: " + (dto.getTipo() != null ? dto.getTipo().getTipo() : "null") +
                    ", Fornecedor: " + (dto.getFornecedor() != null ? dto.getFornecedor().getNome() : "null"));
            produtos.add(dto);
        }
        return produtos;
    }

    //para a mudanca de quantidades
    public static String serializeQuantidadesProduto(int armazem, int montra) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("quantidadeArmazem", armazem);
        jsonObject.put("quantidadeMontra", montra);
        return jsonObject.toString();
    }
    public static List<HistoricoDTO> deSerializeJson2ListHistoricoDTO(String resp) throws JSONException {
        JSONArray jsonArray = new JSONArray(resp);
        List<HistoricoDTO> list = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject objeto = jsonArray.getJSONObject(i);

            HistoricoDTO dto = new HistoricoDTO();
            dto.setQuantidade(objeto.optInt("quantidade", 0));
            dto.setDescricao(objeto.optString("descricao", ""));

            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setNome(extractString(objeto.opt("produto"), "nome"));
            dto.setProduto(produtoDTO);

            list.add(dto);
        }
        return list;
    }

    public static String serialize2JsonHistorico(HistoricoDTO dto) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        if (dto.getProduto() != null && dto.getProduto().getId() != null) {
            jsonObject.put("produto", dto.getProduto().getId());
        }
        jsonObject.put("quantidade", dto.getQuantidade());
        jsonObject.put("descricao", dto.getDescricao());
        return jsonObject.toString();
    }

    // restdb can return a linked field as an object or as an array of objects
    public static String extractString(Object obj, String key) {
        if (obj == null) return "";
        if (obj instanceof JSONArray) {
            JSONArray array = (JSONArray) obj;
            if (array.length() > 0) {
                JSONObject first = array.optJSONObject(0);
                return first != null ? first.optString(key, "") : "";
            }
            return "";
        }
        if (obj instanceof JSONObject) {
            return ((JSONObject) obj).optString(key, "");
        }
        return "";
    }
    // Função para "serialização" do barcode lido para a string que vamos procurar na database
    public static String normalizarCodigo(String raw) {
        if (raw == null) return "";
        String digitos = raw.replaceAll("\\D", "");
        if (digitos.length() == 12) {
            return "0" + digitos;   // UPC-A -> EAN-13
        }
        return digitos;
    }
}