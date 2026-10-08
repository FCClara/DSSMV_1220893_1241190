package com.example.projectdroid_1220893_1241190;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectdroid_1220893_1241190.model.Tipo;
import com.example.projectdroid_1220893_1241190.service.TipoService;

public class Funcionario extends AppCompatActivity {

    private EditText editTextTipo;
    private Button buttonAdicionarTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_funcionario);

        editTextTipo = findViewById(R.id.editTextTipo);
        buttonAdicionarTipo = findViewById(R.id.buttonAdicionarTipo);

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

                    TipoService.adicionarTipo(tipo);

                    runOnUiThread(() -> {
                        editTextTipo.setText("");
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();
        });


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
}