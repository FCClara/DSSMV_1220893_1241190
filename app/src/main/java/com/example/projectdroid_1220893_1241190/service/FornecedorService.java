package com.example.projectdroid_1220893_1241190.service;

import android.content.Context;

import com.example.projectdroid_1220893_1241190.dto.FornecedorDTO;
import com.example.projectdroid_1220893_1241190.handler.JsonHandler;
import com.example.projectdroid_1220893_1241190.handler.NetworkHandler;

import java.util.ArrayList;

public class FornecedorService {

    public static ArrayList<FornecedorDTO> obterFornecedores(Context ctx) throws Exception {
        String json = NetworkHandler.get(ctx, "fornecedor");
        return new ArrayList<>(JsonHandler.deSerializeJson2ListFornecedorDto(json));
    }

    public static boolean existeFornecedor(String nomeFornecedor, Context ctx) throws Exception{
        ArrayList<FornecedorDTO> fornecedores = obterFornecedores(ctx);

        for(FornecedorDTO f : fornecedores){
            if(f != null && f.getNome() != null && f.getNome().equalsIgnoreCase((nomeFornecedor))){
                return true;
            }
        }
        return false;
    }

    public static void adicionarFornecedor(Context ctx, FornecedorDTO dto) throws Exception{
        String body = JsonHandler.serializeFornecedorDTO2Json(dto);
        NetworkHandler.post(ctx, "fornecedor", body);
    }
}