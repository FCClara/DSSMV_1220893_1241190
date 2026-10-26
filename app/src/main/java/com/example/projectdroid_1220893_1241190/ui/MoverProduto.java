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

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mover_produto);

        produtoId = getIntent().getStringExtra("PRODUTO_ID");
        armazem = getIntent().getIntExtra("PRODUTO_ARMAZEM", 0);
        montra = getIntent().getIntExtra("PRODUTO_MONTRA", 0);

        etArmazemMontra = findViewById(R.id.editTextNumber);   // Armazém -> Montra
        etMontraLixo = findViewById(R.id.editTextNumber2);     // Montra -> Lixo

        findViewById(R.id.button2).setOnClickListener(v -> {
            int qtd = lerQuantidade(etArmazemMontra);
            if (qtd <= 0) return;
            if (qtd > armazem) {
                toast("So tem " + armazem + " no armazem");
                return;
            }
            mover(qtd, armazem - qtd, montra + qtd, "Armazém -> Montra");
        });

        findViewById(R.id.button1).setOnClickListener(v -> {
            int qtd = lerQuantidade(etMontraLixo);
            if (qtd <= 0) return;
            if (qtd > montra) {
                toast("So tem " + montra + " na montra");
                return;
            }
            mover(qtd, armazem, montra - qtd, "Montra -> Lixo");
        });
    }

    private int lerQuantidade(EditText et) {
        String s = et.getText().toString().trim();
        if (s.isEmpty()) {
            toast("Indique a quantidade");
            return -1;
        }
        int qtd = Integer.parseInt(s);
        if (qtd <= 0) {
            toast("A quantidade tem de ser superior a 0");
            return -1;
        }
        return qtd;
    }

    private void mover(int qtdMovida, int novoArmazem, int novaMontra, String descricao) {
        if (produtoId == null || produtoId.isEmpty()) {
            toast("Produto invalido");
            return;
        }

        ProdutoDTO produto = new ProdutoDTO();
        produto.setId(produtoId);

        HistoricoDTO historico = new HistoricoDTO();
        historico.setProduto(produto);
        historico.setQuantidade(qtdMovida);
        historico.setDescricao(descricao);

        executor.execute(() -> {
            try {
                ProdutoService.atualizarQuantidades(this, produtoId, novoArmazem, novaMontra);
                HistoricoService.adicionarHistorico(this, historico);

                mainHandler.post(() -> {
                    armazem = novoArmazem;
                    montra = novaMontra;

                    //se nao tivermos isto, quando voltamos para a pagina das acoes,
                    //a quantidade nao muda
                    Intent resultado = new Intent();
                    resultado.putExtra("PRODUTO_ARMAZEM", novoArmazem);
                    resultado.putExtra("PRODUTO_MONTRA", novaMontra);
                    setResult(RESULT_OK, resultado);

                    toast("Movido com sucesso");
                    finish();
                });
            } catch (Exception e) {
                android.util.Log.e("MOVER", "Falhou: " + e.getMessage(), e);
                mainHandler.post(() -> toast("Erro ao mover produto"));
            }
        });
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}