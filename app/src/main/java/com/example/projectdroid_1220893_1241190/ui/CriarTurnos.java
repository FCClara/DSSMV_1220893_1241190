package com.example.projectdroid_1220893_1241190.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.controller.TurnosController;
import com.example.projectdroid_1220893_1241190.dto.DiaDTO;

import java.util.ArrayList;
import java.util.List;

public class CriarTurnos extends AppCompatActivity {

    private TextView tvHoraEntrada, tvHoraSaida;
    private EditText edtHoraEntrada, edtHoraSaida;
    private Button criarTurno;
    private Spinner spinnerDiaCriar;

    private ArrayList<DiaDTO> listaDias = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_criar_turnos);

        tvHoraEntrada = findViewById(R.id.tvHoraEntradaCriar);
        tvHoraSaida = findViewById(R.id.tvHoraSaidaCriar);
        edtHoraEntrada = findViewById(R.id.edtHoraEntrada);
        edtHoraSaida = findViewById(R.id.edtHoraSaida);
        criarTurno = findViewById(R.id.buttonCriarTurnos);
        spinnerDiaCriar = findViewById(R.id.spinnerDiasCriarTurno);

        aplicarMascaraHora(edtHoraEntrada);
        aplicarMascaraHora(edtHoraSaida);

        new TurnosController(this).obterDias(new TurnosController.DiasCallback() {
            @Override
            public void onSucesso(List<DiaDTO> dias) {
                listaDias.clear();
                listaDias.addAll(dias);
                configUI();   // now there is data
            }

            @Override
            public void onErro(String mensagem) {
                Toast.makeText(CriarTurnos.this, mensagem, Toast.LENGTH_LONG).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //pra manter o padrao da hora quando se a introduz
    private static final java.util.regex.Pattern HORA_PATTERN =
            java.util.regex.Pattern.compile("^([01]\\d|2[0-3]):[0-5]\\d$");

    private void aplicarMascaraHora(EditText edt) {
        edt.addTextChangedListener(new android.text.TextWatcher() {
            private boolean editando = false;

            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {}

            @Override
            public void afterTextChanged(android.text.Editable s) {
                if (editando) return;
                editando = true;
                String digitos = s.toString().replaceAll("[^0-9]", "");
                if (digitos.length() > 4) digitos = digitos.substring(0, 4);
                String formatado = digitos.length() > 2
                        ? digitos.substring(0, 2) + ":" + digitos.substring(2)
                        : digitos;
                edt.setText(formatado);
                edt.setSelection(formatado.length());
                editando = false;
            }
        });
    }
    private void configUI() {
        ArrayList<String> nomesDias = new ArrayList<>();
        for (DiaDTO d : listaDias) {
            if (d != null && d.getDia() != null) {
                nomesDias.add(d.getDia());
            }
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, nomesDias);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiaCriar.setAdapter(spinnerAdapter);

        criarTurno.setOnClickListener(v -> validarECriar());
    }

    private void validarECriar() {
        String horaEntrada = edtHoraEntrada.getText().toString().trim();
        String horaSaida = edtHoraSaida.getText().toString().trim();

        if (!HORA_PATTERN.matcher(horaEntrada).matches()) {
            edtHoraEntrada.setError("Use o formato hh:mm (ex: 09:30)");
            return;
        }
        if (!HORA_PATTERN.matcher(horaSaida).matches()) {
            edtHoraSaida.setError("Use o formato hh:mm (ex: 17:00)");
            return;
        }
        if (horaSaida.compareTo(horaEntrada) <= 0) {
            edtHoraSaida.setError("A saída tem de ser depois da entrada");
            return;
        }
        if (spinnerDiaCriar.getSelectedItem() == null) {
            Toast.makeText(this, "Escolha um dia", Toast.LENGTH_SHORT).show();
            return;
        }
        //pra ver o dia, mas manda o id
        String dia = spinnerDiaCriar.getSelectedItem().toString();
        String id = listaDias.get(spinnerDiaCriar.getSelectedItemPosition()).getId();
        Log.d("TURNODIAYES", dia + " " + horaEntrada + " -> " + horaSaida + ""+id);
        // aqui chamas o controller para criar o turno
        new TurnosController(CriarTurnos.this).createTurnos(id, horaEntrada, horaSaida,
                new TurnosController.CriarCallback() {
                    @Override
                    public void onSucesso() {
                        Toast.makeText(CriarTurnos.this, "Turno criado", Toast.LENGTH_SHORT).show();
                        finish();   // volta ao ecrã anterior
                    }

                    @Override
                    public void onErro(String mensagem) {
                        Toast.makeText(CriarTurnos.this, mensagem, Toast.LENGTH_LONG).show();
                        Log.d("LOGDOERRO","WOA" + mensagem);
                    }
                });
    }
}