package com.example.projectdroid_1220893_1241190.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.dto.ProdutoDTO;
import com.example.projectdroid_1220893_1241190.ui.MudarPreco;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrecoAdapter extends RecyclerView.Adapter<PrecoAdapter.ViewHolder> {

    private final List<ProdutoDTO> produtos;
    private final Map<String, Double> precosMedios = new HashMap<>();
    private final Map<String, Boolean> semPreco = new HashMap<>();

    private final OnPrecoClickListener listener;

    public interface OnPrecoClickListener {
        void onPrecoClick(ProdutoDTO produto);
    }


    public PrecoAdapter(List<ProdutoDTO> produtos, OnPrecoClickListener listener) {
        this.produtos = produtos;
        this.listener = listener;
    }

    public void setPrecoMedio(int position, double media) {
        precosMedios.put(produtos.get(position).getId(), media);
        notifyItemChanged(position);
    }

    public void setSemPreco(int position) {
        semPreco.put(produtos.get(position).getId(), true);
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_preco_produto, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            ProdutoDTO p = produtos.get(position);
            holder.tvNome.setText(p.getNome());
            holder.tvPrecoAtual.setText(String.format("Atual: %.2f €", p.getPreco()));

            Double media = precosMedios.get(p.getId());
            if (media != null) {
                holder.tvPrecoMedio.setText(String.format("%.2f €", media));
            } else if (semPreco.containsKey(p.getId())) {
                holder.tvPrecoMedio.setText("Sem dados");
            } else {
                holder.tvPrecoMedio.setText("--");
            }
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(
                        v.getContext(),
                        MudarPreco.class
                );

                intent.putExtra("produtoId", p.getId());
                intent.putExtra("preco", p.getPreco());

                v.getContext().startActivity(intent);
            });

    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome, tvPrecoMedio, tvPrecoAtual;

        ViewHolder(View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNome);
            tvPrecoMedio = itemView.findViewById(R.id.tvPrecoMedio);
            tvPrecoAtual = itemView.findViewById(R.id.tvPrecoAtual);
        }
    }


}