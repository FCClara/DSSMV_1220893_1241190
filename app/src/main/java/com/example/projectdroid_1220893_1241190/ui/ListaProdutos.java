package com.example.projectdroid_1220893_1241190.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.dto.ProdutoDTO;
import com.example.projectdroid_1220893_1241190.service.ProdutoService;

import java.util.ArrayList;

public class ListaProdutos extends AppCompatActivity {

    private Spinner spinner;
    private ArrayList<ProdutoDTO> listaProdutosGlobal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_produtos);

        spinner = findViewById(R.id.spinnerProdutos);

        carregarSpinnerComDados();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    private void carregarSpinnerComDados() {
        new Thread(() -> {
            try {
                listaProdutosGlobal = ProdutoService.obterProdutos(ListaProdutos.this);

                ArrayList<String> nomesProdutos = new ArrayList<>();
                if (listaProdutosGlobal != null) {
                    for (ProdutoDTO p : listaProdutosGlobal) {
                        if (p != null && p.getNome() != null) {
                            nomesProdutos.add(p.getNome());
                        }
                    }
                }

                runOnUiThread(() -> {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            ListaProdutos.this,
                            android.R.layout.simple_spinner_item,
                            nomesProdutos
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);

                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        boolean userSelect = false;

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            // Prevents triggering automatically on layout load
                            if (!userSelect) {
                                userSelect = true;
                                return;
                            }

                            if (listaProdutosGlobal != null && position < listaProdutosGlobal.size()) {
                                ProdutoDTO produtoSelecionado = listaProdutosGlobal.get(position);

                                Intent intent = new Intent(ListaProdutos.this, AcoesProduto.class);
                                intent.putExtra("PRODUTO_NOME", produtoSelecionado.getNome());
                                intent.putExtra("PRODUTO_TIPO", (produtoSelecionado.getTipo() != null ? produtoSelecionado.getTipo().getTipo() : "").toString());
                                intent.putExtra("PRODUTO_PRECO", produtoSelecionado.getPreco());
                                intent.putExtra("PRODUTO_ARMAZEM", produtoSelecionado.getQuantidadeArmazem());
                                intent.putExtra("PRODUTO_MONTRA", produtoSelecionado.getQuantidadeMontra());
                                intent.putExtra("PRODUTO_FORNECEDOR", (produtoSelecionado.getFornecedor() != null ? produtoSelecionado.getFornecedor().getNome() : "").toString());
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}