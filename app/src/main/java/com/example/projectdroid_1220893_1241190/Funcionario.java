package com.example.projectdroid_1220893_1241190;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectdroid_1220893_1241190.model.Tipo;
import com.example.projectdroid_1220893_1241190.service.TipoService;

//a divisao entre tipo e o produto so existe neste momento para o teste

public class Funcionario extends AppCompatActivity {

    private EditText editTextTipo;
    private Button buttonAdicionarTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_funcionario);

        String pagina = getIntent().getStringExtra("pagina");

        editTextTipo = findViewById(R.id.editTextTipo);
        buttonAdicionarTipo = findViewById(R.id.buttonAdicionarTipo);

        if ("tipo".equals(pagina)) {

            mostrarPaginaTipo(this);

        } else if ("produto".equals(pagina)) {

            mostrarPaginaProduto();
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
    private void mostrarPaginaTipo(Context ctx) {

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

                    TipoService.adicionarTipo(tipo, this);

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
    }


    private void mostrarPaginaProduto() {

        // Por enquanto, esconder os elementos de Tipo

        editTextTipo.setVisibility(View.GONE);
        buttonAdicionarTipo.setVisibility(View.GONE);

        // Aqui vais colocar os elementos
        // relacionados com Produto
    }
}



