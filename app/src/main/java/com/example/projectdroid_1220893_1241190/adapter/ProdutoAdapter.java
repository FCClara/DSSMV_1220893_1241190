package com.example.projectdroid_1220893_1241190.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.dto.ProdutoDTO;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ViewHolder>{

    private final List<ProdutoDTO> lista;
    private final OnProdutoClickListener listener;

    public interface OnProdutoClickListener {
        void onProdutoClick(ProdutoDTO produto);
    }

    public ProdutoAdapter(List<ProdutoDTO> lista, OnProdutoClickListener listener){
        this.lista = lista;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void  onBindViewHolder(@NonNull ViewHolder holder, int position){
        ProdutoDTO p = lista.get(position);
        holder.buttonProduto.setText(p.getNome());
        holder.buttonProduto.setOnClickListener(v -> listener.onProdutoClick(p));
    }

    @Override
    public int getItemCount(){
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        Button buttonProduto;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            buttonProduto = itemView.findViewById(R.id.buttonProduto);
        }
    }
}
