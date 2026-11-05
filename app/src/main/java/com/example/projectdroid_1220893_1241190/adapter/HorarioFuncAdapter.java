package com.example.projectdroid_1220893_1241190.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.dto.LinhaHorario;
import com.example.projectdroid_1220893_1241190.dto.TurnoDTO;

import java.util.List;

public class HorarioFuncAdapter extends RecyclerView.Adapter<HorarioFuncAdapter.ViewHolder>{

    private final List<LinhaHorario> lista;

    public HorarioFuncAdapter(List<LinhaHorario> lista) {this.lista =lista;}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_turno, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HorarioFuncAdapter.ViewHolder holder, int position) {
        LinhaHorario t = lista.get(position);
        holder.tvDiaHorario.setText(t.getPrimeiraColuna());
        holder.tvHoraEntrada.setText(String.valueOf(t.getHoraEntrada()));
        holder.tvHoraSaida.setText(String.valueOf(t.getHoraSaida()));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDiaHorario, tvHoraEntrada, tvHoraSaida;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            tvDiaHorario = itemView.findViewById(R.id.tvFuncionarioTurno);
            tvHoraEntrada = itemView.findViewById(R.id.tvHoraEntrada);
            tvHoraSaida = itemView.findViewById(R.id.tvHoraSaida);
        }

    }
}
