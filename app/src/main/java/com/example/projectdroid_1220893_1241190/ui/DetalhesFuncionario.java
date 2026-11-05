package com.example.projectdroid_1220893_1241190.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.adapter.HorarioAdapter;
import com.example.projectdroid_1220893_1241190.adapter.HorarioFuncAdapter;
import com.example.projectdroid_1220893_1241190.controller.TurnosController;
import com.example.projectdroid_1220893_1241190.dto.FuncionarioDTO;
import com.example.projectdroid_1220893_1241190.dto.HorarioDTO;
import com.example.projectdroid_1220893_1241190.dto.LinhaHorario;
import com.example.projectdroid_1220893_1241190.dto.TurnoDTO;

public class DetalhesFuncionario extends AppCompatActivity {

    private TextView tvNome, tvFuncao, tvEmail, tvMorada, tvNIF, tvnCartao, tvSalario;
    private RecyclerView rv;
    private final List<LinhaHorario> linhas = new ArrayList<>();
    private HorarioFuncAdapter horarioFuncAdapter;
    private TurnosController controller;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes_funcionario);

        tvNome = findViewById(R.id.nomeFuncionario);
        tvFuncao = findViewById(R.id.funcaoFuncionario);
        tvEmail = findViewById(R.id.emailFuncionario);
        tvMorada = findViewById(R.id.moradaFuncionario);
        tvNIF = findViewById(R.id.nifFuncionario);
        tvnCartao = findViewById(R.id.nCartaoFuncionario);
        tvSalario = findViewById(R.id.salarioFuncionario);

        rv = findViewById(R.id.rvDetalhesHorarioFunc);

        horarioFuncAdapter = new HorarioFuncAdapter(linhas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(horarioFuncAdapter);

        controller = new TurnosController(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets sb = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sb.left, sb.top, sb.right, sb.bottom);
            return insets;
    });

        FuncionarioDTO f = (FuncionarioDTO) getIntent().getSerializableExtra("FUNCIONARIO");
        if (f == null) {
            Toast.makeText(this, "Funcionário não encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        preencherDetalhes(f);
        preencherHorario(f);
}
    private void preencherDetalhes(FuncionarioDTO f) {
        tvNome.setText(f.getNome());
        tvFuncao.setText("Função: " + f.getFuncao());
        tvEmail.setText("Email: " + f.getEmail());
        tvMorada.setText("Morada: " + f.getMorada());
        tvNIF.setText("NIF: " + f.getNif());
        tvnCartao.setText("Número Cartão: " + f.getnCartao());
        tvSalario.setText(String.format(Locale.getDefault(), "Salário: %.2f €", f.getSalario()));
    }


    private void preencherHorario(FuncionarioDTO f) {
        controller.obterHorarioFuncionario(f.getId(), new TurnosController.HorarioFuncCallback() {
            @Override
            public void onSucesso(List<LinhaHorario> resultado) {
                linhas.clear();
                linhas.addAll(resultado);
                horarioFuncAdapter.notifyDataSetChanged();
            }

            @Override
            public void onErro(String msg) {
                Toast.makeText(DetalhesFuncionario.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
