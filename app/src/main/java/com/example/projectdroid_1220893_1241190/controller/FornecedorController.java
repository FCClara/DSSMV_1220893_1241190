package com.example.projectdroid_1220893_1241190.controller;

import android.app.Activity;

import com.example.projectdroid_1220893_1241190.dto.FornecedorDTO;
import com.example.projectdroid_1220893_1241190.model.Fornecedor;
import com.example.projectdroid_1220893_1241190.service.FornecedorService;

public class FornecedorController {

    public interface FornecedorCallback {
        void onSucesso();
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
}