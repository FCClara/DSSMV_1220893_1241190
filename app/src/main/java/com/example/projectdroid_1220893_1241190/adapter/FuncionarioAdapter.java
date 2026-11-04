package com.example.projectdroid_1220893_1241190.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.dto.FuncionarioDTO;

import java.util.List;

public class FuncionarioAdapter extends RecyclerView.Adapter<FuncionarioAdapter.ViewHolder>{
    private final List<FuncionarioDTO> lista;
    private final OnFuncionarioClickListener listener;

    public interface OnFuncionarioClickListener{
        void onFuncionarioClick(FuncionarioDTO funcionario);
    }

    public FuncionarioAdapter(List<FuncionarioDTO> lista, OnFuncionarioClickListener listener){
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_funcionario, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        FuncionarioDTO f = lista.get(position);
        holder.buttonFuncionario.setText(f.getNome());
        holder.buttonFuncionario.setOnClickListener(v -> listener.onFuncionarioClick(f));
    }

    @Override
    public int getItemCount(){
        return lista.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        Button buttonFuncionario;

        ViewHolder(@NonNull View itemView){
            super(itemView);
            buttonFuncionario = itemView.findViewById(R.id.buttonFuncionario);
        }
    }
}
