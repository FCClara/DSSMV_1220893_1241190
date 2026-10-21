package com.example.projectdroid_1220893_1241190.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectdroid_1220893_1241190.R;

public class AcoesProduto extends AppCompatActivity {

    private TextView tvNome, tvTipo, tvQuantidadeTotal, tvQuantidadeArmazem, tvQuantidadeMontra, tvFornecedor, tvPreco;

    private Button goHistoricoProduto;
    private Button goMoverProduto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_acoes_produto);

        tvNome = findViewById(R.id.nomeProduto);
        tvTipo = findViewById(R.id.tipoDoProduto);
        tvQuantidadeTotal = findViewById(R.id.quantidadeTotal);
        tvQuantidadeArmazem = findViewById(R.id.quantiadeArmazem);
        tvQuantidadeMontra = findViewById(R.id.quantidadeMontra);
        tvFornecedor = findViewById(R.id.fornecedorProduto);
        tvPreco = findViewById(R.id.precoProduto);

        goHistoricoProduto = findViewById(R.id.goHistoricoProduto);
        goMoverProduto = findViewById(R.id.goMoverProduto);

        configUI();


        View rootView = findViewById(R.id.main);
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }

    private void configUI(){

        String nome = getIntent().getStringExtra("PRODUTO_NOME");
        String tipo = getIntent().getStringExtra("PRODUTO_TIPO");
        double preco = getIntent().getDoubleExtra("PRODUTO_PRECO", 0.0);
        int armazem = getIntent().getIntExtra("PRODUTO_ARMAZEM", 0);
        int montra = getIntent().getIntExtra("PRODUTO_MONTRA", 0);
        String fornecedor = getIntent().getStringExtra("PRODUTO_FORNECEDOR");
        int total = armazem + montra;

        tvNome.setText(nome != null ? nome : "Nome indisponível");
        tvTipo.setText("Tipo: " + (tipo != null ? tipo : "-"));
        tvQuantidadeTotal.setText("Quantidade total: " + total);
        tvQuantidadeArmazem.setText("Quantidade armazem: " + armazem);
        tvQuantidadeMontra.setText("Quantidade montra: " + montra);
        tvFornecedor.setText("Fornecedor: " + (fornecedor != null ? fornecedor : "-"));
        tvPreco.setText("Preco: " + preco + "€");

        goMoverProduto.setOnClickListener(v -> {
            Intent intent = new Intent(
                    AcoesProduto.this, MoverProduto.class
            );

            startActivity(intent);
        });

        goHistoricoProduto.setOnClickListener(v-> {
            Intent intent = new Intent(
                    AcoesProduto.this, MoverProduto.class
            );

            startActivity(intent);
        });

    }
}