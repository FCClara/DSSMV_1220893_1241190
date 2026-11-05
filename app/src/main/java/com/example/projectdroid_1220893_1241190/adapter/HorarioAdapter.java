package com.example.projectdroid_1220893_1241190.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.dto.LinhaHorario;

import java.util.List;

public class HorarioAdapter extends RecyclerView.Adapter<HorarioAdapter.ViewHolder> {

    private final List<LinhaHorario> lista;

    public HorarioAdapter(List<LinhaHorario> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_turno, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LinhaHorario l = lista.get(position);
        holder.tvFuncionario.setText(l.getPrimeiraColuna());
        holder.tvHoraEntrada.setText(l.getHoraEntrada());
        holder.tvHoraSaida.setText(l.getHoraSaida());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFuncionario, tvHoraEntrada, tvHoraSaida;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFuncionario = itemView.findViewById(R.id.tvFuncionarioTurno);
            tvHoraEntrada = itemView.findViewById(R.id.tvHoraEntrada);
            tvHoraSaida = itemView.findViewById(R.id.tvHoraSaida);
        }
    }
}