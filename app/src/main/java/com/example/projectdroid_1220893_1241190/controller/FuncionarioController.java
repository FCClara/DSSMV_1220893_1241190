package com.example.projectdroid_1220893_1241190.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.projectdroid_1220893_1241190.dto.FuncaoDTO;
import com.example.projectdroid_1220893_1241190.dto.FuncionarioDTO;
import com.example.projectdroid_1220893_1241190.service.FuncionarioService;
import com.example.projectdroid_1220893_1241190.ui.Funcionario;
import com.example.projectdroid_1220893_1241190.model.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FuncionarioController {

    public interface FuncionariosCallback{
        void onSucesso(List<FuncionarioDTO> funcionarios);
        void onErro(String mensagem);
    }

    public interface FuncionarioCriarCallback{
        void onSucesso(String funcId);
        void onErro(String mensagem);
    }

    public interface FuncaoCallback{
        void onSucesso(List<FuncaoDTO> funcao);
        void onErro(String mensagem);
    }

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    private final Context ctx;

    public FuncionarioController(Context context){
        this.ctx = context.getApplicationContext();
    }

    public void obterFuncao(FuncaoCallback cb){
        executor.execute(() ->{
            try{
                List<FuncaoDTO> lista = FuncionarioService.obterFuncao(ctx);
                mainHandler.post(() -> cb.onSucesso(lista));
            } catch (Exception e) {
                mainHandler.post(() -> cb.onErro("Erro a carregar funcao."));
            }
        });

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

    public void criarFuncionarios(String nome, String email, String morada, int nif, int nCartao, double salario, FuncaoDTO funcaoDTO, FuncionarioCriarCallback cb){
        /*try{
            Funcionario validacao = new Funcionario(null, null, 0.0,0,0,null, null, null);
        } catch (IllegalArgumentException e){
            cb.onErro(e.getMessage());
        }*/
        Log.d("JESUS", "KA"+ nome + nif);

        if(nome == null || nome.trim().isEmpty()){
            cb.onErro("Nome vazio");
            return;
        }
        if(email == null || email.trim().isEmpty()){
            cb.onErro("Email vazio");
            return;
        }
        if(morada == null || morada.trim().isEmpty()){
            cb.onErro("Morada vazio");
            return;
        }
        /*if(nif = null){
            cb.onErro("Nome vazio");
            return;
        }*/

        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setNome(nome);
        dto.setFuncao(funcaoDTO);
        dto.setEmail(email);
        dto.setMorada(morada);
        dto.setNif(nif);
        dto.setnCartao(nCartao);
        dto.setSalario(salario);

        executor.execute(() -> {
            try{
                String funcId = FuncionarioService.adicionarFuncionario(ctx, dto);
                mainHandler.post(()-> cb.onSucesso(funcId));
            } catch (Exception e) {
                mainHandler.post(()-> cb.onErro("Erro:"+e.getMessage()));
            }
        });

    }
}
