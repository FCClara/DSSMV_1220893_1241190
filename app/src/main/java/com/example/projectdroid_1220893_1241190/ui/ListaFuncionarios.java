package com.example.projectdroid_1220893_1241190.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.adapter.FuncionarioAdapter;
import com.example.projectdroid_1220893_1241190.controller.FuncionarioController;
import com.example.projectdroid_1220893_1241190.dto.FuncionarioDTO;

import java.util.ArrayList;
import java.util.List;

public class ListaFuncionarios extends AppCompatActivity {

    private FuncionarioAdapter adapter;
    private final List<FuncionarioDTO> lista = new ArrayList<>();

    private String userRole;

    private FuncionarioController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_funcionarios);

        userRole = getIntent().getStringExtra("USER_ROLE");

        controller = new FuncionarioController(this);

        RecyclerView rv = findViewById(R.id.recyclerFuncionarios);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FuncionarioAdapter(lista, f -> {
                Intent intent = new Intent(this, DetalhesFuncionario.class);
                intent.putExtra("FUNCIONARIO", f);
                intent.putExtra("USER_ROLE", userRole);
                startActivity(intent);
    });
        rv.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        carregarFuncionarios();
    }

    private void carregarFuncionarios(){
        controller.obterFuncionarios(
                new FuncionarioController.FuncionariosCallback() {
                    @Override
                    public void onSucesso(List<FuncionarioDTO> funcionarios) {
                        lista.clear();
                        lista.addAll(funcionarios);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onErro(String mensagem) {
                        Toast.makeText(ListaFuncionarios.this, mensagem, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
