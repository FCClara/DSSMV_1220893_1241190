package com.example.projectdroid_1220893_1241190.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectdroid_1220893_1241190.R;
import com.example.projectdroid_1220893_1241190.controller.FuncionarioController;
import com.example.projectdroid_1220893_1241190.dto.FuncaoDTO;

import java.util.ArrayList;
import java.util.List;

public class AdicionarFuncionario extends AppCompatActivity {

    private EditText editTextNomeFuncionario, editTextEmailFuncionario, editTextMoradaFuncionario,
    editTextNifFuncionario, editTextNCartaoFuncionario, editTextSalarioFuncionario;
    private Spinner spinnerFuncionarioAdicionar;
    private Button buttonConfirmarFuncionario;
    private FuncionarioController controller;

    private List<FuncaoDTO> funcoes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar_funcionario);

        editTextNomeFuncionario = findViewById(R.id.editTextNomeFuncionario);
        editTextEmailFuncionario = findViewById(R.id.editTextEmailFuncionario);
        editTextMoradaFuncionario = findViewById(R.id.editTextMoradaFuncionario);
        editTextNifFuncionario = findViewById(R.id.editTextNifFuncionario);
        editTextNCartaoFuncionario = findViewById(R.id.editTextNCartaoFuncionario);
        editTextSalarioFuncionario = findViewById(R.id.editTextSalarioFuncionario);
        spinnerFuncionarioAdicionar = findViewById(R.id.spinnerFuncionarAdicionar);
        buttonConfirmarFuncionario = findViewById(R.id.buttonConfirmarFuncionario);


        controller = new FuncionarioController(this);

        configSpinner();
        buttonConfirmarFuncionario.setOnClickListener(v -> configUI());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private ArrayAdapter<String> criarAdapter(List<String> itens) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, itens);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void configSpinner() {
        controller.obterFuncao(new FuncionarioController.FuncaoCallback() {
            @Override
            public void onSucesso(List<FuncaoDTO> listaFuncao) {
                funcoes = listaFuncao;

                List<String> nomesFuncao = new ArrayList<>();
                for (FuncaoDTO f : listaFuncao) {
                    nomesFuncao.add(f.getFuncao());
                }
                spinnerFuncionarioAdicionar.setAdapter(criarAdapter(nomesFuncao));
                Log.d("NAV_ADICIONAR_FUNC", "FUNC" + listaFuncao);
            }

            @Override
            public void onErro(String mensagem) {
                Toast.makeText(AdicionarFuncionario.this, mensagem, Toast.LENGTH_LONG).show();

            }
        });
    }

    private void configUI(){
        String nome = editTextNomeFuncionario.getText().toString().trim();
        String email = editTextEmailFuncionario.getText().toString().trim();
        String morada = editTextMoradaFuncionario.getText().toString();
        String textNif = editTextNifFuncionario.getText().toString().trim();
        String textNCartao = editTextNCartaoFuncionario.getText().toString().trim();
        String textSalario = editTextSalarioFuncionario.getText().toString().trim();


        if (funcoes.isEmpty()) {
            Toast.makeText(this, "Nao ha funcoes", Toast.LENGTH_SHORT).show();
        }

        if(nome.isEmpty()){
            editTextNomeFuncionario.setError("Indique o nome");
            editTextNomeFuncionario.requestFocus();

        }
        if (email.isEmpty()) {
            editTextEmailFuncionario.setError("Indique o email");
            editTextEmailFuncionario.requestFocus();

        }
        if(morada.isEmpty()){
            editTextMoradaFuncionario.setError("Indique a morada");
            editTextMoradaFuncionario.requestFocus();
        }
        if(textNif.isEmpty()){
            editTextNifFuncionario.setError("Indique o NIF");
            editTextNifFuncionario.requestFocus();
        }
        if(textNCartao.isEmpty()){
            editTextNCartaoFuncionario.setError("Indique o numero de cartao");
            editTextNCartaoFuncionario.requestFocus();
        }
        if(textSalario.isEmpty()){
            editTextSalarioFuncionario.setError("Indique o salario");
            editTextSalarioFuncionario.requestFocus();
        }
        double salario;
        try{
            salario = Double.parseDouble(textSalario.replace(',', '.'));
        }catch (NumberFormatException e){
            return;
        }

        int nif;
        try{
            nif = Integer.parseInt(textNif);
        }catch (NumberFormatException e){
            return;
        }

        int nCartao;
        try{
            nCartao = Integer.parseInt(textNCartao);
        } catch (NumberFormatException e) {
            return;
        }

        FuncaoDTO funcao = funcoes.get(spinnerFuncionarioAdicionar.getSelectedItemPosition());

        controller.criarFuncionarios(nome, email, morada, nif, nCartao, salario, funcao,
                new FuncionarioController.FuncionarioCriarCallback() {
                    @Override
                    public void onSucesso(String funcId) {
                        Toast.makeText(AdicionarFuncionario.this, "Funcionario adicionado com sucasso", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onErro(String mensagem) {
                        buttonConfirmarFuncionario.setEnabled(true);
                        Toast.makeText(AdicionarFuncionario.this, mensagem, Toast.LENGTH_LONG).show();
                        Log.d("ONERRO_ADDFUNC", "Erro" + mensagem);

                    }
                });

    }
}