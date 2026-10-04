package com.example.projectdroid_1220893_1241190;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        //vamos buscar o spinner do xml no layout
        spinnerTipo = findViewById(R.id.spinnerTipo);

        // carregar para aparecer como as opcoes no dropdown
        carregarTipos();


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

                //esta a ir buscar o url da nossa database e a adicionar tipo no final
                //apiconfig tem o nosso url
                URL url = new URL(
                        ApiConfig.BASE_URL + "tipo"
                );

                //para fazer a conexcao
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();

                //queremos um get pq estamos a ir buscar informacao
                connection.setRequestMethod("GET");
                connection.setRequestProperty(
                        "Accept",
                        "application/json"
                );

                //aqui mandamos a key para podermos acessar e verificar que temos permissao disso
                connection.setRequestProperty(
                        "x-apikey",
                        ApiConfig.API_KEY
                );

                //ta a ler a resposta mandada pela database
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                connection.getInputStream()
                        )
                );

                //aqui ele guarda a resposta
                StringBuilder response = new StringBuilder();
                String line;

                //e lê linha a linha
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                //ja nao tem mais linhas ent acaba
                reader.close();

                //converte a resposta para um array json
                //json é o formato comum que se usa para mandar dados entre servidores

                JSONArray jsonArray =
                        new JSONArray(response.toString());

                //cria array list e ta a adicionar  os que tem como valor tipo
                //assim se ele estivesse a receber mais informacao, mas nao fosse com o valor tipo, nao adiciona

                ArrayList<String> tipos = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objeto =
                            jsonArray.getJSONObject(i);

                    String tipo = objeto.getString("tipo");

                    tipos.add(tipo);
                }

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