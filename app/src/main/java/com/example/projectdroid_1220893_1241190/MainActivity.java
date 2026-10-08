package com.example.projectdroid_1220893_1241190;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectdroid_1220893_1241190.service.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;




public class MainActivity extends AppCompatActivity {
    //spinner é um controlo giratório, uma
    //forma rápida de selecionar um valor de um conjunto,
    //que neste caso vai ser os nossos tipos de produtos
    //isto é o nosso main activity, mas temos de adicionar ao layout

    private Spinner spinnerTipo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        configUI();

        // carregar para aparecer como as opcoes no dropdown
        carregarTipos();
    }

    private void configUI(){

        //vamos buscar o spinner do xml no layout
        spinnerTipo = findViewById(R.id.spinnerTipo);

        //butao para ir pra pagina do funcionario
        Button buttonNext = findViewById(R.id.goFuncionario);

        buttonNext.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Funcionario.class);
            startActivity(intent);
        });

        // deteta o espaço ocupado pelas barras do sistema, no nosso caso um sistema de android telemovel,
        // e ajusta o conteúdo da aplicação

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );
                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );
                    return insets;
                }
        );
    }

    private void carregarTipos() {

        new Thread(() -> {

            try {
                ArrayList<String> tipos =
                        tipoService.obterTipos();
                //aqui dá run do nosso ui
                runOnUiThread(() -> {

                    ArrayAdapter<String> adapter =
                            new ArrayAdapter<>(
                                    MainActivity.this,
                                    android.R.layout.simple_spinner_item,
                                    tipos
                            );

                    adapter.setDropDownViewResource(
                            android.R.layout.simple_spinner_dropdown_item
                    );

                    spinnerTipo.setAdapter(adapter);
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }
}