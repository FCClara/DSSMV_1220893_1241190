package com.example.projectdroid_1220893_1241190.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.adapter.ProdutoAdapter;
import com.example.projectdroid_1220893_1241190.dto.ProdutoDTO;
import com.example.projectdroid_1220893_1241190.dto.TipoDTO;
import com.example.projectdroid_1220893_1241190.service.ProdutoService;
import com.example.projectdroid_1220893_1241190.service.TipoService;

import java.util.ArrayList;

public class ListaProdutos extends AppCompatActivity {

    private Spinner spinnerPTipos;
    private RecyclerView recyclerView;
    private ProdutoAdapter adapter;

    private ArrayList<ProdutoDTO> listaProdutosGlobal;
    private ArrayList<TipoDTO> listaTiposGlobal;
    private final ArrayList<ProdutoDTO> produtosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_produtos);

        spinnerPTipos = findViewById(R.id.spinnerPTipos);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProdutoAdapter(produtosFiltrados, this::abrirAcoesProduto);
        recyclerView.setAdapter(adapter);

        carregarSpinnerComTipos();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void carregarSpinnerComTipos(){
        new Thread(() -> {
            try {
                listaTiposGlobal = TipoService.obterTipos(ListaProdutos.this);
                listaProdutosGlobal = ProdutoService.obterProdutos(ListaProdutos.this);

                ArrayList<String> tipos = new ArrayList<>();
                if (listaTiposGlobal != null) {
                    for (TipoDTO t : listaTiposGlobal) {
                        if(t != null && t.getTipo() != null) {
                            tipos.add(t.getTipo());
                        }
                    }
                }
                runOnUiThread(() -> configurarSpinner(tipos));
            }   catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }

    private void configurarSpinner(ArrayList<String> tipos){
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, tipos);
         spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         spinnerPTipos.setAdapter(spinnerAdapter);
         spinnerPTipos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
             @Override
                     public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 filtrarPorTipo(tipos.get(position));
            }
            @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void filtrarPorTipo(String tipoSelecionado){
        produtosFiltrados.clear();

        if(listaProdutosGlobal != null){
            for(ProdutoDTO p : listaProdutosGlobal){
                if(p != null && p.getTipo() != null && tipoSelecionado.equalsIgnoreCase(p.getTipo().getTipo())){
                    produtosFiltrados.add(p);
                }
            }
        }

        adapter.notifyDataSetChanged();

        if (produtosFiltrados.isEmpty()) {
            Toast.makeText(this, "Sem produtos deste tipo", Toast.LENGTH_SHORT).show();
        }
    }
    /*private void carregarSpinnerComDados() {
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
    }*/

    private void abrirAcoesProduto(ProdutoDTO p) {
        Intent intent = new Intent(ListaProdutos.this, AcoesProduto.class);
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