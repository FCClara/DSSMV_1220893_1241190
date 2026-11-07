package com.example.projectdroid_1220893_1241190.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.projectdroid_1220893_1241190.dto.FuncionarioDTO;
import com.example.projectdroid_1220893_1241190.service.FuncionarioService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FuncionarioController {

    public interface FuncionariosCallback{
        void onSucesso(List<FuncionarioDTO> funcionarios);
        void onErro(String mensagem);
    }

    public interface FuncionarioCriarCallback{
        void onSucesso(List<FuncionarioDTO> funcionario);
        void onErro(String mensagem);
    }

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    private final Context ctx;

    public FuncionarioController(Context context){
        this.ctx = context.getApplicationContext();
    }

    public void obterFuncionarios(FuncionariosCallback cb){
        executor.execute(() ->{
            try{
                List<FuncionarioDTO> lista = FuncionarioService.obterFuncionarios(ctx);
                mainHandler.post(() -> cb.onSucesso(lista));
            } catch (Exception e) {
                mainHandler.post(() -> cb.onErro("Erro a carregar funcionários"));
            }
        });
    }

    public void criarFuncionarios(String nome, String email, String morada, int nif, int nCartao, double salario){

    }
}
