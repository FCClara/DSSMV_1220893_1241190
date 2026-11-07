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
import com.example.projectdroid_1220893_1241190.controller.FornecedorController;

public class CriarFornecedor extends AppCompatActivity {

    private EditText editTextNovoFornecedor;
    private Button buttonConfirmarFornecedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_criar_fornecedor);

        editTextNovoFornecedor = findViewById(R.id.editTextNovoFornecedor);
        buttonConfirmarFornecedor = findViewById(R.id.buttonConfirmarFornecedor);

        buttonConfirmarFornecedor.setOnClickListener(v -> adicionarFornecedor());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void adicionarFornecedor(){
        String nomeFornecedor = editTextNovoFornecedor.getText().toString().trim();

        if(nomeFornecedor.isEmpty()){
            Toast.makeText(this, "Fornecedor não pode estar vazio", Toast.LENGTH_SHORT).show();
            return;
        }
        FornecedorController.adicionarFornecedor(this, nomeFornecedor, new FornecedorController.FornecedorCallback() {
            @Override
            public void onSucesso() {
                Toast.makeText(CriarFornecedor.this, "Fornecedor adicionado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onErro(String mensagem) {
                Toast.makeText(CriarFornecedor.this, mensagem, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
