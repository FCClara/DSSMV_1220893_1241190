package com.example.projectdroid_1220893_1241190.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.adapter.TurnosAdapter;
import com.example.projectdroid_1220893_1241190.controller.TurnosController;
import com.example.projectdroid_1220893_1241190.dto.DiaDTO;
import com.example.projectdroid_1220893_1241190.dto.HorarioDTO;
import com.example.projectdroid_1220893_1241190.dto.TurnoDTO;

import java.util.ArrayList;
import java.util.List;

public class ListaTurnos extends AppCompatActivity {

    private TextView tvTurnos;
    private Spinner spinnerDiaTurnos;

    private RecyclerView rv;

    private ArrayList<DiaDTO> listaDias = new ArrayList<>();
    private List<TurnoDTO> listaTurnos= new ArrayList<>();

    private Button buttonCriarTurnos;

    private String funcId;

    private TurnosAdapter turnosAdapter;
    private String diaSelecionado;

    private TurnosController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_turnos);

        funcId = getIntent().getStringExtra("FUNC_ID");
        controller = new TurnosController(this);


        tvTurnos = findViewById(R.id.tvTurnos);
        spinnerDiaTurnos = findViewById(R.id.spinnerDiaTurnos);

        buttonCriarTurnos= findViewById(R.id.buttonCriarTurnos);
        rv = findViewById(R.id.rvTurnosLista);
        rv.setLayoutManager(new LinearLayoutManager(this));
        turnosAdapter = new TurnosAdapter(listaTurnos, this::confirmarTroca);
        rv.setAdapter(turnosAdapter);

        controller.obterDias(new TurnosController.DiasCallback() {
            @Override
            public void onSucesso(List<DiaDTO> dias) {
                listaDias.clear();
                listaDias.addAll(dias);
                configSpinner();   // now there is data
            }

            @Override
            public void onErro(String mensagem) {
                Toast.makeText(ListaTurnos.this, mensagem, Toast.LENGTH_LONG).show();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void configSpinner() {
        ArrayList<String> nomesDias = new ArrayList<>();
        for (DiaDTO d : listaDias) {
            if (d != null && d.getDia() != null) {
                nomesDias.add(d.getDia());
            }
        }
        Log.d("dias","dias" + nomesDias);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, nomesDias);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiaTurnos.setAdapter(spinnerAdapter);

        spinnerDiaTurnos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // a posição do spinner coincide com a da listaDias (só se filtrou nulls)
                String nomeDia = (String) parent.getItemAtPosition(position);
                diaSelecionado = nomeDia;
                controller.obterTurnos(nomeDia, new TurnosController.TurnoCallback() {
                    @Override
                    public void onSucesso(List<TurnoDTO> turnos) {
                        listaTurnos.clear();
                        listaTurnos.addAll(turnos);
                        turnosAdapter.notifyDataSetChanged();

                        if(turnos.isEmpty()){
                            Toast.makeText(ListaTurnos.this,
                                    "Sem turnos para este dia",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onErro(String mensagem){
                        Toast.makeText(ListaTurnos.this, mensagem,
                                Toast.LENGTH_LONG).show();
                    }
                        });
                Log.d("mostra o dia", "mostra dia" + nomeDia);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void confirmarTroca(TurnoDTO turno) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Trocar turno")
                .setMessage("Queres trocar o teu turno de " + diaSelecionado
                        + " por este (" + turno.getHoraEntrada() + " - " + turno.getHoraSaida() + ")?")
                .setPositiveButton("Trocar", (d, w) -> trocar(turno))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void trocar(TurnoDTO turno) {
        controller.trocarTurno(funcId, diaSelecionado, turno.getId(),
                new TurnosController.AssociarCallback() {
                    @Override
                    public void onSucesso() {
                        Toast.makeText(ListaTurnos.this, "Turno trocado", Toast.LENGTH_SHORT).show();
                        finish(); // a detalhesFuncionario recarrega no onResume
                    }

                    @Override
                    public void onErro(String mensagem) {
                        Toast.makeText(ListaTurnos.this, mensagem, Toast.LENGTH_LONG).show();
                    }
                });
    }

}