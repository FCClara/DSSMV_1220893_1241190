package com.example.projectdroid_1220893_1241190.controller;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.projectdroid_1220893_1241190.service.TipoService;

import java.util.ArrayList;

public class TipoController {

    public static void carregarSpinnerTipos(Activity activity, Spinner spinner) {
        new Thread(() -> {
            try {
                ArrayList<String> tipos = TipoService.obterTipos();

                activity.runOnUiThread(() -> {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            activity,
                            android.R.layout.simple_spinner_item,
                            tipos
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