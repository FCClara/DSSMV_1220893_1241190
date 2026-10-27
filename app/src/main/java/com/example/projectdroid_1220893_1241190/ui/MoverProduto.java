package com.example.projectdroid_1220893_1241190.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.controller.ProdutoController;
import com.example.projectdroid_1220893_1241190.dto.HistoricoDTO;
import com.example.projectdroid_1220893_1241190.dto.ProdutoDTO;
import com.example.projectdroid_1220893_1241190.service.HistoricoService;
import com.example.projectdroid_1220893_1241190.service.ProdutoService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MoverProduto extends AppCompatActivity {

    private EditText etArmazemMontra, etMontraLixo;
    private String produtoId;
    private int armazem, montra;
    private ProdutoController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mover_produto);

        controller = new ProdutoController(this);
        produtoId = getIntent().getStringExtra("PRODUTO_ID");
        armazem = getIntent().getIntExtra("PRODUTO_ARMAZEM", 0);
        montra = getIntent().getIntExtra("PRODUTO_MONTRA", 0);

        etArmazemMontra = findViewById(R.id.editTextNumber);
        etMontraLixo = findViewById(R.id.editTextNumber2);

        findViewById(R.id.button2).setOnClickListener(v -> {
            int qtd = lerQuantidade(etArmazemMontra);
            if (qtd > 0) controller.moverArmazemParaMontra(produtoId, armazem, montra, qtd, callback);
        });

        findViewById(R.id.button1).setOnClickListener(v -> {
            int qtd = lerQuantidade(etMontraLixo);
            if (qtd > 0) controller.moverMontraParaLixo(produtoId, armazem, montra, qtd, callback);
        });
    }

    private final ProdutoController.MoverCallback callback = new ProdutoController.MoverCallback() {
        @Override
        public void onSucesso(int novoArmazem, int novaMontra) {
            armazem = novoArmazem;
            montra = novaMontra;

            Intent resultado = new Intent();
            resultado.putExtra("PRODUTO_ARMAZEM", novoArmazem);
            resultado.putExtra("PRODUTO_MONTRA", novaMontra);
            setResult(RESULT_OK, resultado);

            Toast.makeText(MoverProduto.this, "Movido com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }

        @Override
        public void onErro(String mensagem) {
            Toast.makeText(MoverProduto.this, mensagem, Toast.LENGTH_LONG).show();
        }
    };

    private int lerQuantidade(EditText et) {
        String s = et.getText().toString().trim();
        if (s.isEmpty()) {
            Toast.makeText(this, "Indique a quantidade", Toast.LENGTH_SHORT).show();
            return -1;
        }
        return Integer.parseInt(s);
    }
}