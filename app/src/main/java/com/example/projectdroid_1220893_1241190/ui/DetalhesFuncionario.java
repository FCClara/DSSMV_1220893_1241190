package com.example.projectdroid_1220893_1241190.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
import com.example.projectdroid_1220893_1241190.adapter.HorarioFuncAdapter;
import com.example.projectdroid_1220893_1241190.controller.TurnosController;
import com.example.projectdroid_1220893_1241190.dto.FuncionarioDTO;
import com.example.projectdroid_1220893_1241190.dto.LinhaHorario;

public class DetalhesFuncionario extends AppCompatActivity {

    private TextView tvNome, tvFuncao, tvEmail, tvMorada, tvNIF, tvnCartao, tvSalario;
    private RecyclerView rv;
    private final List<LinhaHorario> linhas = new ArrayList<>();
    private HorarioFuncAdapter horarioFuncAdapter;
    private TurnosController controller;
    private Button buttonGoGo;
    private String funcId;
    private String userRole;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalhes_funcionario);

        userRole = getIntent().getStringExtra("USER_ROLE");

        tvNome = findViewById(R.id.nomeFuncionario);
        tvFuncao = findViewById(R.id.funcaoFuncionario);
        tvEmail = findViewById(R.id.emailFuncionario);
        tvMorada = findViewById(R.id.moradaFuncionario);
        tvNIF = findViewById(R.id.nifFuncionario);
        tvnCartao = findViewById(R.id.nCartaoFuncionario);
        tvSalario = findViewById(R.id.salarioFuncionario);
        buttonGoGo = findViewById(R.id.buttonGoGo);

        rv = findViewById(R.id.rvDetalhesHorarioFunc);

        horarioFuncAdapter = new HorarioFuncAdapter(linhas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(horarioFuncAdapter);

        controller = new TurnosController(this);
        funcId = getIntent().getStringExtra("FUNC_ID");

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
        funcId = f.getId();

        preencherDetalhes(f);

        if("GERENTE".equals(userRole) || "DONO".equals(userRole)){
            buttonGoGo.setText("Criar Turno");
            buttonGoGo.setOnClickListener( v -> {
                Intent intent = new Intent(
                        DetalhesFuncionario.this, CriarTurnos.class
                );
                intent.putExtra("FUNC_ID", funcId);
                startActivity(intent);

        });} else if ("FUNCIONARIO".equals(userRole)){
            buttonGoGo.setText("Pedir mudanca de Turno");
            buttonGoGo.setOnClickListener( v -> {
                Intent intent = new Intent(
                        DetalhesFuncionario.this, ListaTurnos.class
                );
                intent.putExtra("FUNC_ID", funcId);
                startActivity(intent);
            });
        }
    }
    //da reload quando volta a esta pagina depois de associar o novo turno
    @Override
    protected void onResume(){
        super.onResume();
        if (funcId != null) preencherHorario();
    }
    private void preencherDetalhes(FuncionarioDTO f) {
        tvNome.setText(f.getNome());
        tvFuncao.setText("Função: " + f.getFuncao().getFuncao());
        tvEmail.setText("Email: " + f.getEmail());
        tvMorada.setText("Morada: " + f.getMorada());
        tvNIF.setText("NIF: " + f.getNif());
        tvnCartao.setText("Número Cartão: " + f.getnCartao());
        tvSalario.setText(String.format(Locale.getDefault(), "Salário: %.2f €", f.getSalario()));
    }


    private void preencherHorario() {
        controller.obterHorarioIndividualFuncionario(funcId, new TurnosController.HorarioFuncIndividualCallback() {
            @Override
            //ele foi busacr o horario do funcionario
            //estas linhas sao as "linhas" do recycler, que tem a info do horario
            //dia, horaEntrada, horaSaida
            //este clear é pra limpar, depois adiiconar o novo resultado, e dizer ao adapter pra dar reload
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
