package com.example.projectdroid_1220893_1241190.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectdroid_1220893_1241190.R;

public class AdicionarFuncionario extends AppCompatActivity {

    private EditText editTextNomeFuncionario, editTextEmailFuncionario, editTextMoradaFuncionario,
    editTextNifFuncionario, editTextNCartaoFuncionario, editTextSalarioFuncionario;

    private Button buttonConfirmarFuncionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar_funcionario);

        editTextNomeFuncionario = findViewById(R.id.editTextNomeFuncionario);
        editTextEmailFuncionario = findViewById(R.id.editTextEmailFuncionario);
        editTextMoradaFuncionario = findViewById(R.id.editTextMoradaFuncionario);
        editTextNifFuncionario = findViewById(R.id.editTextNifFuncionario);
        editTextNCartaoFuncionario = findViewById(R.id.editTextNCartaoFuncionario);
        editTextSalarioFuncionario = findViewById(R.id.editTextSalarioFuncionario);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}