package com.example.projectdroid_1220893_1241190.handler;

import android.util.Log;

import com.example.projectdroid_1220893_1241190.dto.DiaDTO;
import com.example.projectdroid_1220893_1241190.dto.FornecedorDTO;
import com.example.projectdroid_1220893_1241190.dto.FuncionarioDTO;
import com.example.projectdroid_1220893_1241190.dto.HistoricoDTO;
import com.example.projectdroid_1220893_1241190.dto.HorarioDTO;
import com.example.projectdroid_1220893_1241190.dto.ProdutoDTO;
import com.example.projectdroid_1220893_1241190.dto.TipoDTO;
import com.example.projectdroid_1220893_1241190.dto.TurnoDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
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
            dto.setBarcode(objeto.optString("barcode", ""));

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
                    ", Tipo: " + (dto.getTipo()     != null ? dto.getTipo().getTipo() : "null") +
                    ", Fornecedor: " + (dto.getFornecedor() != null ? dto.getFornecedor().getNome() : "null") +
                    ", Barcode" + (dto.getBarcode()));
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

    public static String serializeAtualizarPreco(double preco) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("preco", preco);
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

    public static String serialize2JsonTurno(TurnoDTO dto) throws JSONException{
        JSONObject jsonObject = new JSONObject();
        if(dto.getDia() != null && dto.getDia().getId() !=null){
            jsonObject.put("Dia", dto.getDia().getId());
        }
        jsonObject.put("HoraEntrada", dto.getHoraEntrada());
        jsonObject.put("HoraSaida", dto.getHoraSaida());
        return jsonObject.toString();
    }

    public static List<DiaDTO> deSeralize2JsonDiaDTO(String resp) throws JSONException{
        JSONArray jsonArray = new JSONArray(resp);
        List<DiaDTO> lista = new ArrayList<>();

        for(int i =0; i<jsonArray.length(); i++){
            JSONObject object = jsonArray.getJSONObject(i);
            DiaDTO dto = new DiaDTO();
            dto.setId(object.optString("_id", ""));
            dto.setDia(object.optString("dia", ""));
            lista.add(dto);
        }
        return lista;
    }
    public static List<TurnoDTO> deSeralize2JsonTurnoDTO(String resp) throws JSONException{
        JSONArray jsonArray = new JSONArray(resp);
        List<TurnoDTO> list = new ArrayList<>();

        for (int i=0; i < jsonArray.length(); i++){

            JSONObject object = jsonArray.getJSONObject(i);

            TurnoDTO dto = new TurnoDTO();
            dto.setId(object.optString("_id", ""));
            dto.setHoraEntrada(object.optString("HoraEntrada").substring(11,16));
            dto.setHoraSaida(object.optString("HoraSaida").substring(11,16));

            DiaDTO diaDTO = new DiaDTO();
            diaDTO.setId(extractString(object.optString("Dia"), "_id"));
            diaDTO.setDia(extractString(object.opt("Dia"), "dia"));
            dto.setDia(diaDTO);

            list.add(dto);
        }
        return list;
    }

    //isto esta agora fora do deseralize pq usa-se no campoDia que por sua fez usamos no service
    private static final String[] CAMPOS_TURNO = {"TurnoSegunda", "TurnoTerca", "TurnoQuarta",
            "TurnoQuinta", "TurnoSexta", "TurnoSabado", "TurnoDomingo"};
    private static final String[] NOMES_DIA = {"Segunda", "Terça", "Quarta", "Quinta",
            "Sexta", "Sábado", "Domingo"};

    public static String campoDoDia(String nomeDia) {
        for (int i = 0; i < NOMES_DIA.length; i++) {
            if (NOMES_DIA[i].equalsIgnoreCase(nomeDia)) return CAMPOS_TURNO[i];
        }
        return null;
    }

    public static List<HorarioDTO> deSeralize2JsonHorarioDTO(String resp) throws JSONException{
        JSONArray jsonArray = new JSONArray(resp);
        List<HorarioDTO> lista = new ArrayList<>();

        for(int i=0; i < jsonArray.length(); i++){
            JSONObject object = jsonArray.getJSONObject(i);
            Log.d("HORARIO_DEBUG", "RAW horario: " + object.toString());

            Iterator<String> it = object.keys();
            while (it.hasNext()) {
                String k = it.next();
                Log.d("HORARIO_DEBUG", "campo: '" + k + "' = " + object.opt(k));
            }

            HorarioDTO dto = new HorarioDTO();

            dto.setId(object.optString("_id", ""));

            FuncionarioDTO funcionarioDTO = new FuncionarioDTO();
            funcionarioDTO.setId(extractString(object.opt("Funcionario"), "_id"));
            funcionarioDTO.setNome(extractString(object.opt("Funcionario"), "Nome"));
            dto.setFuncionario(funcionarioDTO);

            String[] campos = CAMPOS_TURNO;
            String[] nomesDia = NOMES_DIA;

            for (int d = 0; d < campos.length; d++) {
                String turnoId = extractString(object.opt(campos[d]), "_id");
                if (!turnoId.isEmpty()) {
                    dto.setTurno(nomesDia[d], turnoId);
                }
            }
            lista.add(dto);
        }
        return lista;
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

    public static List<FuncionarioDTO> deSerializeJson2ListFuncionarioDTO(String resp) throws JSONException{
        JSONArray  jsonArray = new JSONArray(resp);
        List<FuncionarioDTO> lista = new ArrayList<>();

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject object = jsonArray.getJSONObject(i);

            FuncionarioDTO dto = new FuncionarioDTO();
            dto.setId(object.optString("_id",""));
            dto.setNome(object.optString("Nome",""));
            dto.setFuncao(extractString(object.opt("Funcao"), "funcao"));
            dto.setEmail(object.optString("email", ""));
            dto.setMorada(object.optString("morada",""));
            dto.setNif(object.optInt("NIF",0));
            dto.setnCartao(object.optInt("nCartao",0));
            dto.setSalario(object.optDouble("salario",0.0));
            lista.add(dto);
        }
        return lista;
    }
}