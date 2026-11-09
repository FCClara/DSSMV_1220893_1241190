package com.example.projectdroid_1220893_1241190.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.controller.TipoController;

public class CriarTipo extends AppCompatActivity{

    private EditText editTextNovoTipo;
    private Button buttonConfirmarTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_criar_tipo);

        editTextNovoTipo = findViewById(R.id.editTextNovoTipo);
        buttonConfirmarTipo = findViewById(R.id.buttonConfirmarTipo);

        buttonConfirmarTipo.setOnClickListener(v -> adicionarTipo());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void adicionarTipo(){
        String nomeTipo = editTextNovoTipo.getText().toString().trim();

        if (nomeTipo.isEmpty()){
            editTextNovoTipo.setError("Tipo não pode estar vazio");
            editTextNovoTipo.requestFocus();
            //Toast.makeText(this, "Tipo não pode estar vazio", Toast.LENGTH_SHORT).show();
            return;
        }

        TipoController.adicionarTipo(this, nomeTipo, new TipoController.TipoCallback(){
            @Override
            public void onSucesso(){
                Toast.makeText(CriarTipo.this, "Tipo adicionado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onErro(String mensagem){
                buttonConfirmarTipo.setEnabled(true);
                Toast.makeText(CriarTipo.this, mensagem, Toast.LENGTH_LONG).show();
            }
        });
    }
}