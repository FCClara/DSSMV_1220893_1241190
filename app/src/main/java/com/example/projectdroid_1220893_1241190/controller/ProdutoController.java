package com.example.projectdroid_1220893_1241190.controller;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.projectdroid_1220893_1241190.MainActivity;
import com.example.projectdroid_1220893_1241190.model.Produto;
import com.example.projectdroid_1220893_1241190.service.ProdutoService;
import com.example.projectdroid_1220893_1241190.service.TipoService;

import java.util.ArrayList;

public class ProdutoController {

    public static void carregarSpinnerProdutos(Activity activity, Spinner spinner){
        new Thread(() -> {
            try{
                ArrayList<Produto> listaProdutos = ProdutoService.obterProdutos();

                ArrayList<String> nomesProdutos = new ArrayList<>();
                if (listaProdutos != null) {
                    for (Produto p : listaProdutos) {
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
