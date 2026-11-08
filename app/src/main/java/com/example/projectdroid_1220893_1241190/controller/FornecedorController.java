package com.example.projectdroid_1220893_1241190.controller;

import android.app.Activity;
import android.content.Context;

import com.example.projectdroid_1220893_1241190.dto.FornecedorDTO;
import com.example.projectdroid_1220893_1241190.model.Fornecedor;
import com.example.projectdroid_1220893_1241190.service.FornecedorService;

import java.util.ArrayList;
import java.util.List;

public class FornecedorController {

    public interface FornecedorCallback {
        void onSucesso();
        void onErro(String mensagem);
    }

    public interface FornecedoresCallback{
        void onSucesso(List<FornecedorDTO> fornecedores);
        void onErro(String mensagem);
    }

    public static void adicionarFornecedor(Activity activity, String nomeFornecedor, FornecedorCallback callback) {

        try {
            new Fornecedor(nomeFornecedor);
        } catch (IllegalArgumentException e) {
            callback.onErro(e.getMessage());
            return;
        }


        new Thread(() -> {
            try {
                if (FornecedorService.existeFornecedor(nomeFornecedor, activity)) {
                    activity.runOnUiThread(() -> callback.onErro("Fornecedor já existe!"));
                    return;
                }
                FornecedorService.adicionarFornecedor(activity, new FornecedorDTO(nomeFornecedor));
                activity.runOnUiThread(callback::onSucesso);
            } catch (Exception e) {
                activity.runOnUiThread(() ->
                        callback.onErro("Erro ao adicionar fornecedor: " + e.getMessage()));
            }
        }).start();
    }

    public static void obterFornecedores(Activity activity, FornecedoresCallback callback){
        new Thread(() ->{
            try{
                ArrayList<FornecedorDTO> fornecedores = FornecedorService.obterFornecedores(activity);
                activity.runOnUiThread(() -> callback.onSucesso(fornecedores));
            } catch (Exception e){
                activity.runOnUiThread(() ->
                        callback.onErro("Erro ao carregar fornecedores: " + e.getMessage()));
            }
        }).start();
    }
}