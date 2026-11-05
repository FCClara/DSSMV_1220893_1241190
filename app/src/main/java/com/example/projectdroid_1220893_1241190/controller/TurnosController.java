package com.example.projectdroid_1220893_1241190.controller;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.projectdroid_1220893_1241190.dto.DiaDTO;
import com.example.projectdroid_1220893_1241190.dto.HorarioDTO;
import com.example.projectdroid_1220893_1241190.dto.LinhaHorario;
import com.example.projectdroid_1220893_1241190.dto.TurnoDTO;
import com.example.projectdroid_1220893_1241190.model.Dia;
import com.example.projectdroid_1220893_1241190.service.HorarioService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TurnosController {

    public interface TurnoCallback{
        void onSucesso(List<TurnoDTO> turnos);
        void onErro(String mensagem);
    }
    public interface DiasCallback {
        void onSucesso(List<DiaDTO> dias);
        void onErro(String mensagem);
    }

    public interface CriarCallback{
        void onSucesso();
        void onErro(String mensagem);
    }

    public interface HorarioFuncCallback{
        void onSucesso(List<LinhaHorario> resultado);
        void onErro(String mensagem);
    }



    private static final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    private final Context ctx;

    public TurnosController(Context context ){this.ctx = context.getApplicationContext();}


    public void obterDias(DiasCallback cb) {
        executor.execute(() -> {
            try {
                List<DiaDTO> dias = HorarioService.obterDias(ctx);
                mainHandler.post(() -> cb.onSucesso(dias));
            } catch (Exception e) {
                mainHandler.post(() -> cb.onErro("Erro a carregar dias"));
            }
        });
    }
    public void obterTurnos(String nomeDia, TurnoCallback cb){

        if (nomeDia == null || nomeDia.trim().isEmpty()) {
            cb.onErro("Dia invalido");
            return;
        }

        executor.execute(() -> {
            try {
                List<TurnoDTO> todos = HorarioService.obterTurnos(ctx);
                Log.d("TurnosController", "todos=" + todos.size() + " | filtro='" + nomeDia + "'");

                List<TurnoDTO> filtrados = new ArrayList<>();
                for(TurnoDTO d : todos){
                    Log.d("TurnosController", "turno dia=" + (d.getDia() != null ? "'" + d.getDia().getDia() + "'" : "null"));
                    if(d.getDia() !=null && nomeDia.trim().equalsIgnoreCase(d.getDia().getDia() == null? ""
                            : d.getDia().getDia().trim())){
                        filtrados.add(d);
                    }
                }
                Log.d("TurnosController", "filtrados=" + filtrados.size());
                mainHandler.post(() -> cb.onSucesso(filtrados));
            } catch (Exception e) {
                mainHandler.post(() -> cb.onErro("Erro a carregar turnos"));
                Log.e("TurnosController", "erro" + e.getMessage(), e);
            }
        });
    }

    public void createTurnos(String id, String horaEntrada, String horaSaida, CriarCallback cb ){

        if (id == null || id.trim().isEmpty()) {
            cb.onErro("Dia invalido");
            return;
        }

        if (horaEntrada == null || horaEntrada.trim().isEmpty()) {
            cb.onErro("Hora Entrada invalida");
            return;
        }
        if (horaSaida == null || horaSaida.trim().isEmpty()) {
            cb.onErro("Hora Saida invalido");
            return;
        }

        // o restdb guarda as horas desta forma, ent temos de mandar com o formato correto
        String horaEntradaApi = "1970-01-01T" + horaEntrada + ":00.000Z";
        String horaSaidaApi = "1970-01-01T" + horaSaida + ":00.000Z";

        DiaDTO dia = new DiaDTO();
        dia.setId(id);

        TurnoDTO turno = new TurnoDTO();
        turno.setDia(dia);
        turno.setHoraEntrada(horaEntradaApi);
        turno.setHoraSaida(horaSaidaApi);


        executor.execute(()->  {
            try{
                HorarioService.adicionarTurno(ctx,turno);
                Log.d("LOGONERRO", "LO" + dia);
                mainHandler.post(() -> cb.onSucesso());
            } catch (Exception e) {
                mainHandler.post(()-> cb.onErro("Erro:" + e.getMessage()));
            }
        });
    }

    private static final String[] ORDEM_DIAS = {
            "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado", "Domingo"
    };

    public void obterHorarioFuncionario(String funcId, HorarioFuncCallback cb) {

        if (funcId == null || funcId.trim().isEmpty()) {
            cb.onErro("Funcionario id invalido");
            return;
        }
        String id = funcId.trim();

        executor.execute(() -> {
            try {
                List<TurnoDTO> turnos = HorarioService.obterTurnos(ctx);
                List<HorarioDTO> horarios = HorarioService.obterHorario(ctx);

                Log.d("wowowow", "wo" + horarios);

                // turnoId -> TurnoDTO
                Map<String, TurnoDTO> turnoPorId = new HashMap<>();
                for (TurnoDTO t : turnos) {
                    if (t != null) turnoPorId.put(t.getId(), t);
                }
                Log.d("wowowow", "wo" + funcId);

                List<LinhaHorario> linhas = new ArrayList<>();

                for (HorarioDTO h : horarios) {
                    String hid = h.getFuncionario() != null ? h.getFuncionario().getId() : "null";
                    Log.d("wowowow", "horario func id = '" + hid + "' | nome = "
                            + (h.getFuncionario() != null ? h.getFuncionario().getNome() : "null"));

                    if (h.getFuncionario() == null || h.getFuncionario().getId() == null) continue;
                    if (!id.equalsIgnoreCase(h.getFuncionario().getId().trim())) continue;

                    // Dias pela ordem da semana
                    for (String dia : ORDEM_DIAS) {
                        String turnoId = h.getTurnoDoDia(dia);
                        if (turnoId == null || turnoId.isEmpty()) continue;

                        TurnoDTO t = turnoPorId.get(turnoId);
                        if (t == null) continue;

                        linhas.add(new LinhaHorario(dia, t.getHoraEntrada(), t.getHoraSaida()));

                    }
                    Log.d("wowowow", "wo" + linhas);


                }

                new Handler(Looper.getMainLooper()).post(() -> cb.onSucesso(linhas));

            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> cb.onErro(e.getMessage()));
            }
        });
    }
}
