package com.example.projectdroid_1220893_1241190.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.controller.ProdutoController;
import android.view.View;

public class MoverProduto extends AppCompatActivity {

    private EditText etArmazemMontra, etMontraLixo, etFornecedorArmazem;
    private String produtoId;
    private int armazem, montra;
    private ProdutoController controller;
    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mover_produto);

        userRole = getIntent().getStringExtra("USER_ROLE");
        Log.d("USERMOVER", "user" + userRole);

        controller = new ProdutoController(this);
        produtoId = getIntent().getStringExtra("PRODUTO_ID");
        armazem = getIntent().getIntExtra("PRODUTO_ARMAZEM", 0);
        montra = getIntent().getIntExtra("PRODUTO_MONTRA", 0);

        etArmazemMontra = findViewById(R.id.edtArmazemMontra);
        etMontraLixo = findViewById(R.id.edtMontraLixo);
        etFornecedorArmazem = findViewById(R.id.edtFornecedorArmazem);

        aplicarRole();

        findViewById(R.id.buttonArmazemMontra).setOnClickListener(v -> {
            int qtd = lerQuantidade(etArmazemMontra);
            if (qtd > 0) controller.moverArmazemParaMontra(produtoId, armazem, montra, qtd, callback);
        });

        findViewById(R.id.buttonMontraLixo).setOnClickListener(v -> {
            int qtd = lerQuantidade(etMontraLixo);
            if (qtd > 0) controller.moverMontraParaLixo(produtoId, armazem, montra, qtd, callback);
        });

        findViewById(R.id.buttonFornecedorArmazem).setOnClickListener(v-> {
            int qtd = lerQuantidade(etFornecedorArmazem);
            if (qtd>0) controller.moverAdicionarStock(produtoId, armazem, montra, qtd, callback);
        });
    }

    private void aplicarRole(){
        boolean isDono = "DONO".equals(userRole);

        findViewById(R.id.secFornecedorArmazem)
                .setVisibility(isDono ? View.VISIBLE : View.GONE);
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