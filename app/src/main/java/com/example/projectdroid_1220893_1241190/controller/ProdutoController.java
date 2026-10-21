package com.example.projectdroid_1220893_1241190.controller;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.projectdroid_1220893_1241190.MainActivity;
import com.example.projectdroid_1220893_1241190.dto.ProdutoDTO;
import com.example.projectdroid_1220893_1241190.model.Produto;
import com.example.projectdroid_1220893_1241190.service.ProdutoService;
import com.example.projectdroid_1220893_1241190.service.TipoService;

import java.util.ArrayList;

public class ProdutoController {

    public static void carregarSpinnerProdutos(Activity activity, Spinner spinner){
        new Thread(() -> {
            try {
                ArrayList<ProdutoDTO> listaProdutos = ProdutoService.obterProdutos(activity);

                ArrayList<String> nomesProdutos = new ArrayList<>();
                if (listaProdutos != null) {
                    for (ProdutoDTO p : listaProdutos) {
                        if (p != null && p.getNome() != null) {
                            nomesProdutos.add(p.getNome());
                        }
                    }
                }

                activity.runOnUiThread(() ->{
                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(
                                    activity,
                                    android.R.layout.simple_spinner_item,
                                    nomesProdutos
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