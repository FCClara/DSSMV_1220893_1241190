package com.example.projectdroid_1220893_1241190.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.adapter.HistoricoAdapter;
import com.example.projectdroid_1220893_1241190.controller.HistoricoController;
import com.example.projectdroid_1220893_1241190.dto.HistoricoDTO;

import java.util.ArrayList;
import java.util.List;

public class HistoricoProduto extends AppCompatActivity {

    public static final String EXTRA_PRODUTO_NOME = "produto_nome";

    private final ArrayList<HistoricoDTO> lista = new ArrayList<>();
    private HistoricoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico_produto);

        String nomeProduto = getIntent().getStringExtra(EXTRA_PRODUTO_NOME);
        if (nomeProduto == null) nomeProduto = "";

        ((TextView) findViewById(R.id.tvNomeProduto)).setText(nomeProduto);

        RecyclerView rv = findViewById(R.id.rvHistorico);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoricoAdapter(lista);
        rv.setAdapter(adapter);

        new HistoricoController(this).obterHistoricoDoProduto(nomeProduto,
                new HistoricoController.HistoricoCallback() {
                    @Override
                    public void onSucesso(List<HistoricoDTO> historicos) {
                        lista.clear();
                        lista.addAll(historicos);
                        adapter.notifyDataSetChanged();

                        if (historicos.isEmpty()) {
                            Toast.makeText(HistoricoProduto.this,
                                    "Sem historico para este produto",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onErro(String mensagem) {
                        Toast.makeText(HistoricoProduto.this, mensagem,
                                Toast.LENGTH_LONG).show();
                    }
                });
    }
}