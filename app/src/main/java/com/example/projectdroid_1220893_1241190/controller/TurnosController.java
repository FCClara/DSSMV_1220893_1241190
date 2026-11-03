package com.example.projectdroid_1220893_1241190.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.projectdroid_1220893_1241190.dto.DiaDTO;
import com.example.projectdroid_1220893_1241190.dto.HorarioDTO;
import com.example.projectdroid_1220893_1241190.dto.TurnoDTO;
import com.example.projectdroid_1220893_1241190.service.HorarioService;

import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TurnosController {

    public interface TurnoCallback{
        void onSucesso(List<TurnoDTO> turnos);
        void onErro(String mensagem);
    }
    public interface DiasCallback {
        void onSucesso(List<DiaDTO> dias);
        void onErro(String mensagem);
    }



    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    private final Context ctx;

    public TurnosController(Context context ){this.ctx = context.getApplicationContext();}

    public void obterDias(DiasCallback cb) {
        executor.execute(() -> {
            try {
                List<DiaDTO> dias = HorarioService.obterDias(ctx);
                mainHandler.post(() -> cb.onSucesso(dias));
            } catch (Exception e) {
                mainHandler.post(() -> cb.onErro("Erro a carregar dias"));
            }
        });
    }
    public void obterTurnos(String nomeDia, TurnoCallback cb){

        if (nomeDia == null || nomeDia.trim().isEmpty()) {
            cb.onErro("Dia invalido");
            return;
        }

        executor.execute(() -> {
            try {
                List<TurnoDTO> todos = HorarioService.obterTurnos(ctx);
                Log.d("TurnosController", "todos=" + todos.size() + " | filtro='" + nomeDia + "'");

                List<TurnoDTO> filtrados = new ArrayList<>();
                for(TurnoDTO d : todos){
                    Log.d("TurnosController", "turno dia=" + (d.getDia() != null ? "'" + d.getDia().getDia() + "'" : "null"));
                    if(d.getDia() !=null && nomeDia.trim().equalsIgnoreCase(d.getDia().getDia() == null? ""
                            : d.getDia().getDia().trim())){
                        filtrados.add(d);
                    }
                }
                Log.d("TurnosController", "filtrados=" + filtrados.size());
                mainHandler.post(() -> cb.onSucesso(filtrados));
            } catch (Exception e) {
                mainHandler.post(() -> cb.onErro("Erro a carregar turnos"));
                Log.e("TurnosController", "erro" + e.getMessage(), e);
            }
        });
    }
}
