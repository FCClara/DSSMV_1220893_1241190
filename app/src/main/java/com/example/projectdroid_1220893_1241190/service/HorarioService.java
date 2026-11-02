package com.example.projectdroid_1220893_1241190.service;

import android.content.Context;

import com.example.projectdroid_1220893_1241190.dto.DiaDTO;
import com.example.projectdroid_1220893_1241190.dto.HorarioDTO;
import com.example.projectdroid_1220893_1241190.dto.TurnoDTO;
import com.example.projectdroid_1220893_1241190.handler.JsonHandler;
import com.example.projectdroid_1220893_1241190.handler.NetworkHandler;
import com.example.projectdroid_1220893_1241190.model.Dia;

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
}
