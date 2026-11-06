package com.example.projectdroid_1220893_1241190.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.dto.HistoricoDTO;
import com.example.projectdroid_1220893_1241190.dto.TurnoDTO;
import com.example.projectdroid_1220893_1241190.model.Turno;
import com.example.projectdroid_1220893_1241190.ui.ListaProdutos;

import java.util.List;

public class TurnosAdapter extends RecyclerView.Adapter<TurnosAdapter.ViewHolder> {

    private final List<TurnoDTO> lista;
    private final OnTurnoClickListener listener;

    public interface OnTurnoClickListener{
        void onTurnoClick(TurnoDTO turno);
    }
    public TurnosAdapter(List<TurnoDTO> lista, OnTurnoClickListener listener) {
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lista_turno, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TurnoDTO d = lista.get(position);
        holder.tvHoraEntrada.setText(String.valueOf(d.getHoraEntrada()));
        holder.tvHoraSaida.setText(d.getHoraSaida());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onTurnoClick(d);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHoraEntrada, tvHoraSaida;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHoraEntrada = itemView.findViewById(R.id.tvHoraEntradaTurnos);
            tvHoraSaida= itemView.findViewById(R.id.tvHoraSaidaTurno);
        }
    }

}