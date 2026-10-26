package com.example.projectdroid_1220893_1241190.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.dto.HistoricoDTO;

import java.util.List;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoAdapter.ViewHolder> {

    private final List<HistoricoDTO> lista;

    public HistoricoAdapter(List<HistoricoDTO> lista) {
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historico, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoricoDTO h = lista.get(position);
        holder.tvQuantidade.setText(String.valueOf(h.getQuantidade()));
        holder.tvDescricao.setText(h.getDescricao());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuantidade, tvDescricao;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuantidade = itemView.findViewById(R.id.tvQuantidade);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);
        }
    }
}