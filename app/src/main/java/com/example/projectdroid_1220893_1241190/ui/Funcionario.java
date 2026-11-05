package com.example.projectdroid_1220893_1241190.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectdroid_1220893_1241190.controller.ProdutoController;
import com.example.projectdroid_1220893_1241190.dto.ProdutoDTO;
import com.example.projectdroid_1220893_1241190.handler.JsonHandler;
import com.example.projectdroid_1220893_1241190.handler.JsonHandler.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;



import com.example.projectdroid_1220893_1241190.R;

public class Funcionario extends AppCompatActivity {

    private EditText editTextTipo;
    private Button buttonAdicionarTipo;

    private Button buttonGoListProdutos, buttonGoHorario, buttonGoListaTurnos,buttonGoListaFuncionarios;
    private Button goPrecoProduto, buttonGoCodigoBarras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_funcionario);

        String userRole = getIntent().getStringExtra("USER_ROLE");

        editTextTipo = findViewById(R.id.editTextTipo);
        buttonAdicionarTipo = findViewById(R.id.buttonAdicionarTipo);
        buttonGoListProdutos = findViewById(R.id.buttonGoListProdutos);
        goPrecoProduto = findViewById(R.id.goPrecoProduto);
        buttonGoCodigoBarras = findViewById(R.id.buttonGoCodigoBarras);
        buttonGoHorario = findViewById(R.id.buttonGoHorario);
        buttonGoListaTurnos = findViewById(R.id.buttonGoListaTurnos);
        buttonGoListaFuncionarios = findViewById(R.id.buttonGoListaFuncionarios);

        // Regra de acúmulo de permissões/botões:
        if ("DONO".equals(userRole)) {
            configurarOpcoesFuncionario(userRole);
            configurarOpcoesGerente(userRole);
            //configurarOpcoesDono();

        } else if ("GERENTE".equals(userRole)) {
            configurarOpcoesFuncionario(userRole);
            configurarOpcoesGerente(userRole);
        } else {
             configurarOpcoesFuncionario(userRole);
        }


        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );
    }



    /*private void mostrarPaginaProduto() {

        // Por enquanto, esconder os elementos de Tipo

        editTextTipo.setVisibility(View.GONE);
        buttonAdicionarTipo.setVisibility(View.GONE);
        // Aqui vais colocar os elementos
        // relacionados com Produto
    }*/

    private void procurarProduto(String codigo) {
        new ProdutoController(this).procurarPorBarcode(codigo,
                new ProdutoController.ProdutoCallback() {
                    @Override
                    public void onSucesso(ProdutoDTO produto) {
                        startActivity(AcoesProduto.criarIntent(Funcionario.this, produto));
                    }

                    @Override
                    public void onErro(String mensagem) {
                        Toast.makeText(Funcionario.this, mensagem, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void configurarOpcoesFuncionario(String userRole) {

        buttonGoListProdutos.setVisibility(View.VISIBLE);
        buttonGoListProdutos.setOnClickListener( v -> {
            android.util.Log.d("NAV_DEBUG", "Botao clicado. A iniciar intent para ListaProdutos");
            Intent intent = new Intent(
                    Funcionario.this, ListaProdutos.class
            );
            startActivity(intent);
            android.util.Log.d("NAV_DEBUG", "startActivity chamado com sucesso.");
        });

        goPrecoProduto.setOnClickListener(v -> {
            Intent intent = new Intent(Funcionario.this, Preco.class);
            startActivity(intent);
        });
        buttonGoHorario.setOnClickListener(v -> {
            Intent intent = new Intent(Funcionario.this, Horario.class);
            startActivity(intent);
        });
        buttonGoListaTurnos.setOnClickListener(v -> {
            Intent intent = new Intent(Funcionario.this, ListaTurnos.class);
            startActivity(intent);
        });


        buttonGoCodigoBarras.setOnClickListener(b -> {
            android.util.Log.d("NAV_DEBUG", "Botao clicado a abrir camara");

            GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                    .setBarcodeFormats(
                            Barcode.FORMAT_EAN_13,
                            Barcode.FORMAT_UPC_A,
                            Barcode.FORMAT_EAN_8
                    )
                    .enableAutoZoom()
                    .build();

            GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this, options);

            scanner.startScan()
                    .addOnSuccessListener(barcode ->{
                        String codigo = JsonHandler.normalizarCodigo(barcode.getRawValue());
                        android.util.Log.d("NAV_DEBUG", "Formato: " + barcode.getFormat() + " | Codigo: " + codigo);
                        procurarProduto(codigo);
                    })
                    .addOnCanceledListener(() ->
                            android.util.Log.d("NAV_DEBUG", "Scan cancelado"))
                    .addOnFailureListener(e -> {
                        android.util.Log.e("NAV_DEBUG", "Erro no scanner", e);
                        Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });
        buttonGoListaFuncionarios.setVisibility(View.VISIBLE);

        buttonGoListaFuncionarios.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Funcionario.this, ListaFuncionarios.class
            );
            intent.putExtra("USER_ROLE", userRole);
            startActivity(intent);
        });

    }

    private void configurarOpcoesGerente(String userRole) {
        buttonGoListaFuncionarios.setVisibility(View.VISIBLE);

        buttonGoListaFuncionarios.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Funcionario.this, ListaFuncionarios.class
            );
            intent.putExtra("USER_ROLE", userRole);
            startActivity(intent);
        });
    }

    private void configurarOpcoesDono(){


    }

}
