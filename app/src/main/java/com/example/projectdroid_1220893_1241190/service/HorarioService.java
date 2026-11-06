package com.example.projectdroid_1220893_1241190.service;

import android.content.Context;

import com.example.projectdroid_1220893_1241190.dto.DiaDTO;
import com.example.projectdroid_1220893_1241190.dto.HorarioDTO;
import com.example.projectdroid_1220893_1241190.dto.TurnoDTO;
import com.example.projectdroid_1220893_1241190.handler.JsonHandler;
import com.example.projectdroid_1220893_1241190.handler.NetworkHandler;
import com.example.projectdroid_1220893_1241190.model.Dia;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HorarioService {

    public static ArrayList<TurnoDTO> obterTurnos(Context ctx) throws Exception{
        String json = NetworkHandler.get(ctx, "turnos");
        return new ArrayList<>(JsonHandler.deSeralize2JsonTurnoDTO(json));
    }

    public static ArrayList<DiaDTO> obterDias(Context ctx) throws Exception{
        String json = NetworkHandler.get(ctx, "dias");
        return new ArrayList<>(JsonHandler.deSeralize2JsonDiaDTO(json));
    }

    public static ArrayList<HorarioDTO> obterHorario(Context ctx) throws Exception{
        String json = NetworkHandler.get(ctx, "horario");
        return new ArrayList<>(JsonHandler.deSeralize2JsonHorarioDTO(json));
    }

    public static String adicionarTurno(Context ctx, TurnoDTO dto) throws Exception {
        String body = JsonHandler.serialize2JsonTurno(dto);
        String resposta = NetworkHandler.post(ctx, "turnos", body);
        return new JSONObject(resposta).getString("_id");
    }

    // funcionário JÁ tem horário: atualiza só o campo do dia
    public static void atualizarTurnoDoDia(Context ctx, String horarioId,
                                           String nomeDia, String turnoId) throws Exception {
        String campo = JsonHandler.campoDoDia(nomeDia);
        if (campo == null) throw new IllegalArgumentException("Dia invalido: " + nomeDia);

        JSONObject body = new JSONObject();
        body.put(campo, new JSONArray().put(turnoId));
        NetworkHandler.put(ctx, "horario/" + horarioId, body.toString());
    }

    public static void apagarTurno(Context ctx, String turnoId) throws Exception {
        NetworkHandler.delete(ctx, "turnos/" + turnoId);
    }
}
