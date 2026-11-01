package com.example.projectdroid_1220893_1241190.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.adapter.PrecoAdapter;
import com.example.projectdroid_1220893_1241190.dto.ProdutoDTO;
import com.example.projectdroid_1220893_1241190.handler.JsonHandler;
import com.example.projectdroid_1220893_1241190.handler.NetworkHandler;
import com.example.projectdroid_1220893_1241190.service.PrecoService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Preco extends AppCompatActivity {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    private final PrecoService precoService = new PrecoService();

    private final List<ProdutoDTO> produtos = new ArrayList<>();
    private PrecoAdapter adapter;
    private ProgressBar progressBar;
    private Button btnAtualizarTodos;

    private final ActivityResultLauncher<Intent> mudarLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    carregarProdutos();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preco);

        progressBar = findViewById(R.id.progressBar);
        btnAtualizarTodos = findViewById(R.id.btnAtualizarTodos);

        RecyclerView recycler = findViewById(R.id.recyclerProdutos);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PrecoAdapter(produtos, this::abrirAcoesProduto);
        recycler.setAdapter(adapter);

        btnAtualizarTodos.setOnClickListener(v -> atualizarTodos());

        carregarProdutos();
    }

    private void carregarProdutos() {
        executor.execute(() -> {
            try {
                String resp = NetworkHandler.get(this, "/produto");
                List<ProdutoDTO> lista = JsonHandler.deSerializeJson2ListProdutoDto(resp);

                mainHandler.post(() -> {
                    produtos.clear();
                    produtos.addAll(lista);
                    adapter.notifyDataSetChanged();
                });
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                mainHandler.post(() ->
                        Toast.makeText(this, "Erro ao carregar produtos", Toast.LENGTH_SHORT).show());
            }
        });
    }
    private void addPrecoBarCode(ProdutoDTO produto) throws IOException, JSONException {
        JSONObject body = new JSONObject();
        body.put("preco", produto.getPreco());
        body.put("barcode", produto.getBarcode());

        NetworkHandler.patch(this, "/produto/" + produto.getId(), body.toString());
    }

    private void atualizarTodos() {
        btnAtualizarTodos.setEnabled(false);
        progressBar.setMax(produtos.size());
        progressBar.setProgress(0);
        progressBar.setVisibility(View.VISIBLE);

        executor.execute(() -> {
            for (int i = 0; i < produtos.size(); i++) {
                final int pos = i;
                ProdutoDTO produto = produtos.get(i);

                try {
                    String codigo = produto.getBarcode();
                    boolean barcodeNovo = false;

                    if (codigo == null || codigo.isEmpty()) {
                        codigo = precoService.procurarCodigoBarras(produto.getNome());
                        Thread.sleep(6500); // Open Food Facts search rate limit
                        if (codigo != null) {
                            produto.setBarcode(codigo);
                            barcodeNovo = true;
                        }
                    }

                    Double media = (codigo == null) ? null : precoService.mediaPreco(codigo);

                    if (media != null) {
                        addPrecoBarCode(produto);
                        mainHandler.post(() -> adapter.setPrecoMedio(pos, media));
                    } else {
                        // no price found, but still save a newly found barcode so we skip the search next time
                        if (barcodeNovo) addPrecoBarCode(produto);
                        mainHandler.post(() -> adapter.setSemPreco(pos));
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                    mainHandler.post(() -> adapter.setSemPreco(pos));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }

                mainHandler.post(() -> progressBar.setProgress(pos + 1));
            }

            mainHandler.post(() -> {
                progressBar.setVisibility(View.GONE);
                btnAtualizarTodos.setEnabled(true);
            });
        });
    }

    private void abrirAcoesProduto(ProdutoDTO p) {
        Intent intent = new Intent(Preco.this, MudarPreco.class);
        intent.putExtra("PRODUTO_ID", p.getId());
        intent.putExtra("PRODUTO_NOME", p.getNome());
        intent.putExtra("PRODUTO_TIPO", p.getTipo() != null ? p.getTipo().getTipo() : "");
        intent.putExtra("PRODUTO_PRECO", p.getPreco());
        intent.putExtra("PRODUTO_ARMAZEM", p.getQuantidadeArmazem());
        intent.putExtra("PRODUTO_MONTRA", p.getQuantidadeMontra());
        intent.putExtra("PRODUTO_FORNECEDOR", p.getFornecedor() != null ? p.getFornecedor().getNome() : "");
        startActivity(intent);
    }
}