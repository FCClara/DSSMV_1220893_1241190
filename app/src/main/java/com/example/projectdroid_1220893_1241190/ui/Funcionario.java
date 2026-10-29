package com.example.projectdroid_1220893_1241190.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    private Button buttonGoListProdutos;
    private Button buttonGoCodigoBarras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_funcionario);

        String userRole = getIntent().getStringExtra("USER_ROLE");

        editTextTipo = findViewById(R.id.editTextTipo);
        buttonAdicionarTipo = findViewById(R.id.buttonAdicionarTipo);
        buttonGoListProdutos = findViewById(R.id.buttonGoListProdutos);
        buttonGoCodigoBarras = findViewById(R.id.buttonGoCodigoBarras);

        // Regra de acúmulo de permissões/botões:
        if ("DONO".equals(userRole)) {
             configurarOpcoesFuncionario();
            // configurarOpcoesGerente();
            // configurarOpcoesDono();

        } else if ("GERENTE".equals(userRole)) {
             configurarOpcoesFuncionario();
            // configurarOpcoesGerente();
        } else {
             configurarOpcoesFuncionario();
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

    /*private void mostrarPaginaTipo(Context ctx) {

        editTextTipo.setVisibility(View.VISIBLE);
        buttonAdicionarTipo.setVisibility(View.VISIBLE);

        buttonAdicionarTipo.setOnClickListener(v -> {

            String nomeTipo =
                    editTextTipo.getText().toString().trim();

            if (nomeTipo.isEmpty()) {
                editTextTipo.setError("Escreva um tipo");
                return;
            }

            Tipo tipo = new Tipo(nomeTipo);

            new Thread(() -> {

                try {

                    if (TipoService.existeTipo(nomeTipo, ctx)) {
                        runOnUiThread(() -> {
                            editTextTipo.setError("Esse tipo de produto já existe!");
                        });
                        return;
                    }

                    TipoService.adicionarTipo(tipo, ctx);

                    runOnUiThread(() -> {
                        editTextTipo.setText("");
                        Toast.makeText(Funcionario.this, "Tipo adicionado com sucesso!", Toast.LENGTH_SHORT).show(); //Toast é a library que permite que usemos "pop-ups" de erro ou de sucesso
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        Toast.makeText(Funcionario.this, "Erro ao processar o pedido", Toast.LENGTH_SHORT).show(); //
                    });
                }

            }).start();
        });
    }*/


    /*private void mostrarPaginaProduto() {

        // Por enquanto, esconder os elementos de Tipo

        editTextTipo.setVisibility(View.GONE);
        buttonAdicionarTipo.setVisibility(View.GONE);
        // Aqui vais colocar os elementos
        // relacionados com Produto
    }*/

    private void configurarOpcoesFuncionario() {

        buttonGoListProdutos.setVisibility(View.VISIBLE);
        buttonGoListProdutos.setOnClickListener( v -> {
            android.util.Log.d("NAV_DEBUG", "Botao clicado. A iniciar intent para ListaProdutos");
            Intent intent = new Intent(
                    Funcionario.this, ListaProdutos.class
            );
            startActivity(intent);
            android.util.Log.d("NAV_DEBUG", "startActivity chamado com sucesso.");
        });

        //buttonGoCodigoBarras.setVisibility(View.VISIBLE);
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
                        Toast.makeText(this, "Código: "+ codigo, Toast.LENGTH_LONG).show();
                        //placeholder para procurar produto com o codigo lido
                    })
                    .addOnCanceledListener(() ->
                            android.util.Log.d("NAV_DEBUG", "Scan cancelado"))
                    .addOnFailureListener(e -> {
                        android.util.Log.e("NAV_DEBUG", "Erro no scanner", e);
                        Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });

    }

    private void configurarOpcoesGerente() {

    }

    private void configurarOpcoesDono(){


    }

}
