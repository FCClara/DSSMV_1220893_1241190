package com.example.projectdroid_1220893_1241190.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectdroid_1220893_1241190.R;

public class FichaFuncionario extends AppCompatActivity {

    private TextView tvNome, tvFuncao, tvEmail, tvMorada, tvNIF, tvnCartao, tvSalario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ficha_funcionario);

        tvNome = findViewById(R.id.nomeFuncionario);
        tvFuncao = findViewById(R.id.funcaoFuncionario);
        tvEmail = findViewById(R.id.emailFuncionario);
        tvMorada = findViewById(R.id.moradaFuncionario);
        tvNIF = findViewById(R.id.nifFuncionario);
        tvnCartao = findViewById(R.id.nCartaoFuncionario);
        tvSalario = findViewById(R.id.salarioFuncionario);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
