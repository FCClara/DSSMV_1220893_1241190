package com.example.projectdroid_1220893_1241190.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.projectdroid_1220893_1241190.dto.HistoricoDTO;
import com.example.projectdroid_1220893_1241190.service.HistoricoService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoricoController {

    public interface HistoricoCallback {
        void onSucesso(List<HistoricoDTO> historicos);
        void onErro(String mensagem);
    }

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    private final Context ctx;

    public HistoricoController(Context context) {
        this.ctx = context.getApplicationContext();
    }

    public void obterHistoricoDoProduto(String nomeProduto, HistoricoCallback cb) {
        if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
            cb.onErro("Produto invalido");
            return;
        }

        executor.execute(() -> {
            try {
                List<HistoricoDTO> todos = HistoricoService.obterHistorico(ctx);

                List<HistoricoDTO> filtrados = new ArrayList<>();
                for (HistoricoDTO h : todos) {
                    if (h.getProduto() != null
                            && nomeProduto.trim().equalsIgnoreCase(
                            h.getProduto().getNome() == null ? "" : h.getProduto().getNome().trim())) {
                        filtrados.add(h);
                    }
                }

                mainHandler.post(() -> cb.onSucesso(filtrados));
            } catch (Exception e) {
                android.util.Log.e("HISTORICO", "Falhou: " + e.getMessage(), e);
                mainHandler.post(() -> cb.onErro("Erro ao carregar historico"));
            }
        });
    }
}