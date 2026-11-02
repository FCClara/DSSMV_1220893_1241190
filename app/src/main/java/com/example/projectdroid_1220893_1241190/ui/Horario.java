package com.example.projectdroid_1220893_1241190.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.projectdroid_1220893_1241190.adapter.HorarioAdapter;
import com.example.projectdroid_1220893_1241190.dto.DiaDTO;
import com.example.projectdroid_1220893_1241190.dto.HorarioDTO;
import com.example.projectdroid_1220893_1241190.dto.LinhaHorario;
import com.example.projectdroid_1220893_1241190.dto.TurnoDTO;
import com.example.projectdroid_1220893_1241190.service.HorarioService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Horario extends AppCompatActivity {

    private TextView tvHorario;
    private Spinner spinnerDia;
    private RecyclerView rv;

    private ArrayList<DiaDTO> listaDias = new ArrayList<>();
    private List<HorarioDTO> listaHorarios = new ArrayList<>();
    private final Map<String, TurnoDTO> turnoPorId = new HashMap<>();

    private final List<LinhaHorario> linhas = new ArrayList<>();
    private HorarioAdapter horarioAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_horario);

        spinnerDia = findViewById(R.id.spinnerDia);
        tvHorario = findViewById(R.id.tvHorario);
        rv = findViewById(R.id.rvHorario);

        horarioAdapter = new HorarioAdapter(linhas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(horarioAdapter);

        carregarDados();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Carrega tudo uma vez; depois o spinner só filtra em memória
    private void carregarDados() {
        new Thread(() -> {
            try {

                ArrayList<DiaDTO> dias = HorarioService.obterDias(Horario.this);
                Log.d("HORARIO_DEBUG", "yes" + dias);
                List<TurnoDTO> turnos = HorarioService.obterTurnos(Horario.this);
                Log.d("HORARIO_DEBUG", "2. Turnos recebidos: " + turnos);
                List<HorarioDTO> horarios = HorarioService.obterHorario(Horario.this);
                Log.d("HORARIO_DEBUG", "3. Horarios recebidos: " + horarios);

                runOnUiThread(() -> {
                    listaDias = dias != null ? dias : new ArrayList<>();
                    listaHorarios = horarios != null ? horarios : new ArrayList<>();

                    turnoPorId.clear();
                    if (turnos != null) {
                        for (TurnoDTO t : turnos) {
                            if (t != null) turnoPorId.put(t.getId(), t);
                        }
                    }
                    configSpinner();
                    Log.d("passou", "passou");
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(Horario.this, "Erro ao carregar horário", Toast.LENGTH_SHORT).show());
            }
        }).start();
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
        spinnerDia.setAdapter(spinnerAdapter);

        spinnerDia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // a posição do spinner coincide com a da listaDias (só se filtrou nulls)
                String nomeDia = (String) parent.getItemAtPosition(position);
                mostrarDia(nomeDia);
                Log.d("mostra o dia", "mostra dia" + nomeDia);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void mostrarDia(String nomeDia) {
        linhas.clear();
        Log.d("HORARIO_DEBUG", "mostrarDia '" + nomeDia + "' | horarios=" + listaHorarios.size()
                + " | turnos no mapa=" + turnoPorId.size());

        for (HorarioDTO h : listaHorarios) {
            String func = h.getFuncionario() != null ? h.getFuncionario().getNome() : "null";
            String turnoId = h.getTurnoDoDia(nomeDia);

            Log.d("HORARIO_DEBUG", "func=" + func + " | chaves=" + h.getId()
                    + " | turnoId='" + turnoId + "'");

            if (turnoId == null || turnoId.isEmpty()) {
                Log.w("HORARIO_DEBUG", "  -> SALTOU: sem turnoId para este dia");
                continue;
            }

            TurnoDTO t = turnoPorId.get(turnoId);
            if (t == null) {
                Log.w("HORARIO_DEBUG", "  -> SALTOU: turno '" + turnoId + "' nao existe no mapa. Ids no mapa: "
                        + turnoPorId.keySet());
                continue;
            }

            if (h.getFuncionario() == null) {
                Log.w("HORARIO_DEBUG", "  -> SALTOU: funcionario null");
                continue;
            }

            linhas.add(new LinhaHorario(func, t.getHoraEntrada(), t.getHoraSaida()));
        }

        Log.d("HORARIO_DEBUG", "Linhas finais: " + linhas.size());
        horarioAdapter.notifyDataSetChanged();
    }
}