package com.example.projectdroid_1220893_1241190.ui;

import android.content.Intent;
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
import com.example.projectdroid_1220893_1241190.controller.ProdutoController;

public class MudarPreco extends AppCompatActivity {

    private EditText editPreco;
    private String produtoId;
    private double preco;

    private ProdutoController controller;

    private Button mudarPreco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mudar_preco);
        controller =  new ProdutoController(this);
        produtoId = getIntent().getStringExtra("produtoId");
        preco = getIntent().getDoubleExtra("preco", 0.0);

        editPreco = findViewById(R.id.editarPreco);

        android.util.Log.d("MANDARCONTROLLER", "YAY" + produtoId);


        findViewById(R.id.buttonMudarPrecoFinal).setOnClickListener(v->{
            double novoPreco = lerPreco(editPreco);
            android.util.Log.d("MANDARCONTROLLER", "YAY" + produtoId + novoPreco);

            if (novoPreco > 0.0) controller.mudarPreco(produtoId, novoPreco, preco, callback);
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private final ProdutoController.novoPrecoCallback callback = new ProdutoController.novoPrecoCallback() {
        @Override
        public void onSucesso(double novoPrecoFinal) {
            preco = novoPrecoFinal;

            Intent resultado = new Intent();
            resultado.putExtra("PRECO NOVO", novoPrecoFinal);
            setResult(RESULT_OK, resultado);

            Toast.makeText(MudarPreco.this, "Mudado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onErro(String mensagem) {
            Toast.makeText(MudarPreco.this, mensagem, Toast.LENGTH_LONG).show();
        }
    };

    private double lerPreco(EditText et) {
        String p = et.getText().toString().trim();
        if (p.isEmpty()) {
            Toast.makeText(this, "Indique o preco", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return Double.parseDouble(p.replace(',', '.'));    }
}