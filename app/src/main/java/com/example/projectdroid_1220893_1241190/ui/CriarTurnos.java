package com.example.projectdroid_1220893_1241190.ui;

import android.os.Bundle;
import android.util.Log;
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
import com.example.projectdroid_1220893_1241190.dto.HorarioDTO;

import java.util.ArrayList;
import java.util.List;

public class CriarTurnos extends AppCompatActivity {

    private TextView tvHoraEntrada, tvHoraSaida;
    private EditText edtHoraEntrada, edtHoraSaida;
    private Button criarTurno;
    private Spinner spinnerDiaCriar;

    private ArrayList<DiaDTO> listaDias = new ArrayList<>();
    private HorarioDTO horarioFuncionario; // pode ficar null

    private TurnosController controller;

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

        String funcId = getIntent().getStringExtra("FUNC_ID");
        Log.d("FUNC_ID", "SHDJ" + funcId);

        controller = new TurnosController(this);

        controller.obterDias(new TurnosController.DiasCallback() {
            @Override
            public void onSucesso(List<DiaDTO> dias) {
                listaDias.clear();
                for (DiaDTO d : dias) {
                    if (d != null && d.getDia() != null) listaDias.add(d);
                }
                configUI(funcId);
            }

            @Override
            public void onErro(String mensagem) {
                Toast.makeText(CriarTurnos.this, mensagem, Toast.LENGTH_LONG).show();
            }
        });

        controller.obterHorarioDTODoFuncionario(funcId,
                new TurnosController.HorarioDoFuncCallback() {
                    @Override
                    public void onSucesso(HorarioDTO horario) {
                        horarioFuncionario = horario;
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
    private void configUI(String funcId) {
        ArrayList<String> nomesDias = new ArrayList<>();

        for (DiaDTO d : listaDias) {
            nomesDias.add(d.getDia());
        }


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, nomesDias);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDiaCriar.setAdapter(spinnerAdapter);

        criarTurno.setOnClickListener(v -> validarECriar(funcId));
    }

    private void validarECriar(String funcId) {
        String horaEntrada = edtHoraEntrada.getText().toString().trim();
        String horaSaida = edtHoraSaida.getText().toString().trim();

        if (!HORA_PATTERN.matcher(horaEntrada).matches()) {
            edtHoraEntrada.setError("Use o formato HH:MM (ex: 09:30)");
            edtHoraEntrada.requestFocus();
            return;
        }
        if (!HORA_PATTERN.matcher(horaSaida).matches()) {
            edtHoraSaida.setError("Use o formato HH:MM (ex: 17:00)");
            edtHoraSaida.requestFocus();
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
        int pos = spinnerDiaCriar.getSelectedItemPosition();
        if (pos < 0 || pos >= listaDias.size()) {
            Toast.makeText(this, "Escolha um dia", Toast.LENGTH_SHORT).show();
            return;
        }

        //pra ver o dia, mas manda o id
        DiaDTO diaEscolhido = listaDias.get(pos);
        String diaId = diaEscolhido.getId();
        String diaNome = diaEscolhido.getDia();

        criarTurno.setEnabled(false); // evita duplo clique
        //Log.d("TURNODIAYES", dia + " " + horaEntrada + " -> " + horaSaida + ""+id);

        // controller pra criar o turno
        controller.createTurnos(diaId, horaEntrada, horaSaida,
                new TurnosController.CriarCallback() {
                    @Override
                    public void onSucesso(String turnoId) {
                        //criou com sucesso entra agora associa
                        controller.associarTurnoAoHorario(funcId, horarioFuncionario, diaNome, turnoId,
                                new TurnosController.AssociarCallback() {
                                    @Override
                                    public void onSucesso() {
                                        Toast.makeText(CriarTurnos.this,
                                                "Turnos criado e associado", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onErro(String mensagem) {
                                        criarTurno.setEnabled(true);
                                        Toast.makeText(CriarTurnos.this,
                                                "Turno criado mas nao associado", Toast.LENGTH_SHORT).show();

                                    }
                                });

                        //Toast.makeText(CriarTurnos.this, "Turno criado", Toast.LENGTH_SHORT).show();
                        //finish();   // volta ao ecrã anterior
                    }

                    @Override
                    public void onErro(String mensagem) {
                        criarTurno.setEnabled(true);
                        Toast.makeText(CriarTurnos.this, mensagem, Toast.LENGTH_LONG).show();
                        Log.d("LOGDOERRO","WOA" + mensagem);
                    }
                });
    }
}