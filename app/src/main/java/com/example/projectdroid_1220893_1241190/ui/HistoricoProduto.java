package com.example.projectdroid_1220893_1241190.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.adapter.HistoricoAdapter;
import com.example.projectdroid_1220893_1241190.dto.HistoricoDTO;
import com.example.projectdroid_1220893_1241190.service.HistoricoService;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HistoricoProduto extends AppCompatActivity {

    public static final String EXTRA_PRODUTO_NOME = "produto_nome";

    private final ArrayList<HistoricoDTO> listaFiltrada = new ArrayList<>();
    private HistoricoAdapter adapter;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_produto);

        String nomeProduto = getIntent().getStringExtra(EXTRA_PRODUTO_NOME);
        if (nomeProduto == null) nomeProduto = "";

        ((TextView) findViewById(R.id.tvNomeProduto)).setText(nomeProduto);

        RecyclerView rv = findViewById(R.id.rvHistorico);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoricoAdapter(listaFiltrada);
        rv.setAdapter(adapter);

        carregarHistorico(nomeProduto);
    }

    private void carregarHistorico(String nomeProduto) {
        executor.execute(() -> {
            try {
                ArrayList<HistoricoDTO> todos = HistoricoService.obterHistorico(this);

                ArrayList<HistoricoDTO> filtrados = new ArrayList<>();
                for (HistoricoDTO h : todos) {
                    if (h.getProduto() != null
                            && nomeProduto.equalsIgnoreCase(h.getProduto().getNome())) {
                        filtrados.add(h);
                    }
                }

                mainHandler.post(() -> {
                    listaFiltrada.clear();
                    listaFiltrada.addAll(filtrados);
                    adapter.notifyDataSetChanged();

                    if (filtrados.isEmpty()) {
                        Toast.makeText(this, "Sem historico para este produto",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                mainHandler.post(() ->
                        Toast.makeText(this, "Erro ao carregar historico",
                                Toast.LENGTH_LONG).show());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}