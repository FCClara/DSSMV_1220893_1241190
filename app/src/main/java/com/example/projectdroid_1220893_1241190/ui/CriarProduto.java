package com.example.projectdroid_1220893_1241190.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.controller.FornecedorController;
import com.example.projectdroid_1220893_1241190.controller.ProdutoController;
import com.example.projectdroid_1220893_1241190.controller.TipoController;
import com.example.projectdroid_1220893_1241190.dto.FornecedorDTO;
import com.example.projectdroid_1220893_1241190.dto.TipoDTO;

import java.util.ArrayList;
import java.util.List;

public class CriarProduto extends AppCompatActivity {
    private Spinner spinnerTipoProduto;
    private Spinner spinnerFornecedorProduto;
    private EditText editTextNomeProduto;
    private EditText editTextPrecoProduto;
    private Button buttonConfirmarProduto;

    private List<TipoDTO> tipos = new ArrayList<>();
    private List<FornecedorDTO> fornecedores = new ArrayList<>();
    private ProdutoController produtoController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_criar_produto);

        spinnerTipoProduto = findViewById(R.id.spinnerTipoProduto);
        spinnerFornecedorProduto = findViewById(R.id.spinnerFornecedorProduto);
        editTextNomeProduto = findViewById(R.id.editTextNomeProduto);
        editTextPrecoProduto = findViewById(R.id.editTextPrecoProduto);
        buttonConfirmarProduto = findViewById(R.id.buttonConfirmarProduto);

        produtoController = new ProdutoController(this);
        carregarTipos();
        carregarFornecedores();

        buttonConfirmarProduto.setOnClickListener(v -> adicionarProduto());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void carregarTipos() {
        TipoController.obterTipos(this, new TipoController.TiposCallback() {
            @Override
            public void onSucesso(List<TipoDTO> lista) {
                tipos = lista;

                List<String> nomes = new ArrayList<>();
                for (TipoDTO t : lista) {
                    nomes.add(t.getTipo());
                }
                spinnerTipoProduto.setAdapter(criarAdapter(nomes));
            }

            @Override
            public void onErro(String mensagem) {
                Toast.makeText(CriarProduto.this, mensagem, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void carregarFornecedores() {
        FornecedorController.obterFornecedores(this, new FornecedorController.FornecedoresCallback() {
            @Override
            public void onSucesso(List<FornecedorDTO> lista) {
                fornecedores = lista;

                List<String> nomes = new ArrayList<>();
                for (FornecedorDTO f : lista) {
                    nomes.add(f.getNome());
                }
                spinnerFornecedorProduto.setAdapter(criarAdapter(nomes));
            }

            @Override
            public void onErro(String mensagem) {
                Toast.makeText(CriarProduto.this, mensagem, Toast.LENGTH_LONG).show();
            }
        });
    }

    private ArrayAdapter<String> criarAdapter(List<String> itens) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, itens);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void adicionarProduto() {
        String nome = editTextNomeProduto.getText().toString().trim();
        String textoPreco = editTextPrecoProduto.getText().toString().trim();

        if (tipos.isEmpty()) {
            Toast.makeText(this, "Não há tipos. Crie um tipo primeiro.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (fornecedores.isEmpty()) {
            Toast.makeText(this, "Não há fornecedores. Crie um fornecedor primeiro.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (nome.isEmpty()) {
            Toast.makeText(this, "Indique o nome do produto", Toast.LENGTH_SHORT).show();
            return;
        }
        if (textoPreco.isEmpty()) {
            Toast.makeText(this, "Indique o preço", Toast.LENGTH_SHORT).show();
            return;
        }

        double preco;
        try {
            preco = Double.parseDouble(textoPreco.replace(',', '.'));
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Preço inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        TipoDTO tipo = tipos.get(spinnerTipoProduto.getSelectedItemPosition());
        FornecedorDTO fornecedor = fornecedores.get(spinnerFornecedorProduto.getSelectedItemPosition());

        produtoController.adicionarProduto(nome, preco, tipo, fornecedor,
                new ProdutoController.CriarProdutoCallback() {
                    @Override
                    public void onSucesso() {
                        Toast.makeText(CriarProduto.this, "Produto adicionado com sucesso", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onErro(String mensagem) {
                        buttonConfirmarProduto.setEnabled(true);
                        Toast.makeText(CriarProduto.this, mensagem, Toast.LENGTH_LONG).show();
                    }
                });
    }
}

