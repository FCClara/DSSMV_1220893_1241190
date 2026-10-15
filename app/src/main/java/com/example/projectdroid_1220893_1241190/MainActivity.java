package com.example.projectdroid_1220893_1241190;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.projectdroid_1220893_1241190.controller.ProdutoController;
import com.example.projectdroid_1220893_1241190.controller.TipoController;
import com.example.projectdroid_1220893_1241190.model.Produto;
import com.example.projectdroid_1220893_1241190.service.*;

import java.util.ArrayList;




public class MainActivity extends AppCompatActivity {
    //spinner é um controlo giratório, uma
    //forma rápida de selecionar um valor de um conjunto,
    //que neste caso vai ser os nossos tipos de produtos
    //isto é o nosso main activity, mas temos de adicionar ao layout

    private Spinner spinnerTipo;

    private Spinner spinnerProduto;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        configUI();

        // carregar para aparecer como as opcoes no dropdown
        carregarTipos();

        carregarProdutos();
    }

    private void configUI(){

        //vamos buscar o spinner do xml no layout
        spinnerTipo = findViewById(R.id.spinnerTipo);
        spinnerProduto = findViewById(R.id.spinnerProduto);

        //butao para ir pra pagina do funcionario
        Button buttonTipos = findViewById(R.id.goTipos);
        Button buttonProdutos = findViewById(R.id.goProduto);

        buttonTipos.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    Funcionario.class
            );

            intent.putExtra("pagina", "tipo");

            startActivity(intent);
        });

        buttonProdutos.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    Funcionario.class
            );

            intent.putExtra("pagina", "produto");

            startActivity(intent);
        });

        // deteta o espaço ocupado pelas barras do sistema, no nosso caso um sistema de android telemovel,
        // e ajusta o conteúdo da aplicação

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );
                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );
                    return insets;
                }
        );
    }

    private void carregarTipos() {
        TipoController.carregarSpinnerTipos(this, spinnerTipo);
    }

    private void carregarProdutos() {
        ProdutoController.carregarSpinnerProdutos(this, spinnerProduto);
    }
}