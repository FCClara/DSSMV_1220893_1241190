package com.example.projectdroid_1220893_1241190.service;

import android.content.Context;

import com.example.projectdroid_1220893_1241190.dto.*;
import com.example.projectdroid_1220893_1241190.handler.JsonHandler;
import com.example.projectdroid_1220893_1241190.handler.NetworkHandler;

import java.util.ArrayList;
public class ProdutoService {

    public static ArrayList<ProdutoDTO> obterProdutos(Context ctx) throws Exception {
        String json = NetworkHandler.get(ctx, "produto");
        return new ArrayList<>(JsonHandler.deSerializeJson2ListProdutoDto(json));

    }

    public static void atualizarQuantidades(Context ctx, String produtoId, int armazem, int montra)
            throws Exception {
        String body = JsonHandler.serializeQuantidadesProduto(armazem, montra);
        NetworkHandler.patch(ctx, "produto/" + produtoId, body);
    }

    public static void atualizarPreco(Context ctx, String produtoId, double preco)
        throws Exception{
            String body = JsonHandler.serializeAtualizarPreco(preco);
            NetworkHandler.patch(ctx, "produto/" + produtoId, body);
    }
}


