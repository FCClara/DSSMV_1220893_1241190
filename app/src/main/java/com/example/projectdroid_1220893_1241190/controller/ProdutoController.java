package com.example.projectdroid_1220893_1241190.controller;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.projectdroid_1220893_1241190.dto.HistoricoDTO;
import com.example.projectdroid_1220893_1241190.dto.ProdutoDTO;
import com.example.projectdroid_1220893_1241190.service.HistoricoService;
import com.example.projectdroid_1220893_1241190.service.ProdutoService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProdutoController {

    // ---------- Callbacks ----------
    //um callback é basicamente ele a dizer tipo, chama-me outra vez quando acabares
    public interface MoverCallback {
        void onSucesso(int novoArmazem, int novaMontra);
        void onErro(String mensagem);
    }

    public interface novoPrecoCallback {
        void onSucesso (double novoPreco);
        void onErro(String mensagem);
    }

    public interface ProdutosCallback {
        void onSucesso(List<ProdutoDTO> produtos);
        void onErro(String mensagem);
    }

    // ---------- Shared threading ----------

    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    // handler está ligado à fila de tarefas da main thread (Looper.getMainLooper()).
    // com handler.post() uma thread em background mete código nessa fila,
    // e a main thread executa quando puder, tudo o que mexe na UI tem de passar por aqui.
    private static final Handler mainHandler = new Handler(Looper.getMainLooper());

    private final Context ctx;

    public ProdutoController(Context context) {
        this.ctx = context.getApplicationContext();
    }

    // ---------- Listar produtos ----------

    public void obterProdutos(ProdutosCallback cb) {
        executor.execute(() -> {
            try {
                List<ProdutoDTO> lista = ProdutoService.obterProdutos(ctx);
                mainHandler.post(() -> cb.onSucesso(lista));
            } catch (Exception e) {
                android.util.Log.e("PRODUTOS", "Falhou: " + e.getMessage(), e);
                mainHandler.post(() -> cb.onErro("Erro ao carregar produtos"));
            }
        });
    }

    // ---------- Mover produto ----------

    public void moverArmazemParaMontra(String produtoId, int armazem, int montra,
                                       int qtd, MoverCallback cb) {
        if (qtd > armazem) {
            cb.onErro("So tem " + armazem + " no armazem");
            return;
        }
        executarMovimento(produtoId, armazem - qtd, montra + qtd, qtd, "Armazém -> Montra", cb);
    }

    public void moverMontraParaLixo(String produtoId, int armazem, int montra,
                                    int qtd, MoverCallback cb) {
        if (qtd > montra) {
            cb.onErro("So tem " + montra + " na montra");
            return;
        }
        executarMovimento(produtoId, armazem, montra - qtd, qtd, "Montra -> Lixo", cb);
    }

    private void executarMovimento(String produtoId, int novoArmazem, int novaMontra, int qtd, String descricao, MoverCallback cb) {

        if (produtoId == null || produtoId.isEmpty()) {
            cb.onErro("Produto invalido");
            return;
        }
        if (qtd <= 0) {
            cb.onErro("A quantidade tem de ser superior a 0");
            return;
        }

        ProdutoDTO produto = new ProdutoDTO();
        produto.setId(produtoId);

        HistoricoDTO historico = new HistoricoDTO();
        historico.setProduto(produto);
        historico.setQuantidade(qtd);
        historico.setDescricao(descricao);

        executor.execute(() -> {
            try {
                ProdutoService.atualizarQuantidades(ctx, produtoId, novoArmazem, novaMontra);
                HistoricoService.adicionarHistorico(ctx, historico);
                mainHandler.post(() -> cb.onSucesso(novoArmazem, novaMontra));
            } catch (Exception e) {
                android.util.Log.e("MOVER", "Falhou: " + e.getMessage(), e);
                mainHandler.post(() -> cb.onErro("Erro: " + e.getMessage()));
            }
        });
    }

    public void mudarPreco(String produtoId, double preco,double novoPreco, novoPrecoCallback cb){

        if (preco < 0){
            cb.onErro("Tem de ser superior a zero");
            return;
        }
        android.util.Log.d("INFO_NO_PRECO", "PRECO" + produtoId +" " + preco);

        executor.execute(() ->{
            try {
                ProdutoService.atualizarPreco(ctx, produtoId,preco);
                android.util.Log.d("Preco", "Fallho mas mandou");
                mainHandler.post(() -> cb.onSucesso(novoPreco));

            } catch (Exception e){
                android.util.Log.d("Preco", "Fallho" + e.getMessage(), e);
                mainHandler.post(() -> cb.onErro("Erro " + e.getMessage()));
            }
        });

    }

    // ---------- Antigo (mantido para nao partir outros ecras) ----------

    @Deprecated
    public static void carregarSpinnerProdutos(Activity activity, Spinner spinner) {
        new Thread(() -> {
            try {
                ArrayList<ProdutoDTO> listaProdutos = ProdutoService.obterProdutos(activity);

                ArrayList<String> nomesProdutos = new ArrayList<>();
                if (listaProdutos != null) {
                    for (ProdutoDTO p : listaProdutos) {
                        if (p != null && p.getNome() != null) {
                            nomesProdutos.add(p.getNome());
                        }
                    }
                }

                activity.runOnUiThread(() -> {
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            activity,
                            android.R.layout.simple_spinner_item,
                            nomesProdutos
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}