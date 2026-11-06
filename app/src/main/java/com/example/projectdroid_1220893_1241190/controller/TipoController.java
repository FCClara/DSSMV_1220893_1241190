package com.example.projectdroid_1220893_1241190.controller;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.projectdroid_1220893_1241190.dto.TipoDTO;
import com.example.projectdroid_1220893_1241190.model.Tipo;
import com.example.projectdroid_1220893_1241190.service.TipoService;

import java.util.ArrayList;

public class TipoController {

    public interface TipoCallback {
        void onSucesso();
        void onErro(String mensagem);
    }

    public static void adicionarTipo(Activity activity, String nomeTipo, TipoCallback callback){

        try {
            new Tipo(nomeTipo);
        } catch (IllegalArgumentException e) {
            callback.onErro(e.getMessage());
            return;
        }

        new Thread(() -> {
            try {
                if (TipoService.existeTipo(nomeTipo, activity)) {
                    activity.runOnUiThread(() -> callback.onErro("Tipo de produto já existe!"));
                    return;
                }

                TipoService.adicionarTipo(activity, new TipoDTO(nomeTipo));
                activity.runOnUiThread(callback::onSucesso);

            } catch (Exception e) {
                e.printStackTrace();
                activity.runOnUiThread(() ->
                        callback.onErro("Erro ao adicionar tipo: " + e.getMessage()));
            }
        }).start();
    }

    public static void carregarSpinnerTipos(Activity activity, Spinner spinner) {
        new Thread(() -> {
            try {
                ArrayList<TipoDTO> tipos = TipoService.obterTipos(activity);

                ArrayList<String> nomesTipos = new ArrayList<>();

                if(tipos!= null){
                    for (TipoDTO t : tipos){
                        if (t != null && t.getTipo() != null){
                            nomesTipos.add(t.getTipo());
                        }
                    }
                }
                activity.runOnUiThread(() -> {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            activity,
                            android.R.layout.simple_spinner_item,
                            nomesTipos
                    );

                    adapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item
                    );

                    spinner.setAdapter(adapter);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}