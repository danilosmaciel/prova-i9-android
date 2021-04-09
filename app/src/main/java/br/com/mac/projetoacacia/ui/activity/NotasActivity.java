package br.com.mac.projetoacacia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.com.mac.projetoacacia.R;
import br.com.mac.projetoacacia.databinding.NotasLayoutBinding;
import br.com.mac.projetoacacia.model.Nota;
import br.com.mac.projetoacacia.repository.NotaRepository;
import br.com.mac.projetoacacia.ui.dialog.NotaEditDialog;
import br.com.mac.projetoacacia.ui.dialog.NotaSaveDialog;
import br.com.mac.projetoacacia.ui.viewmodel.NotaViewModel;
import br.com.mac.projetoacacia.ui.viewmodel.NotaViewModelFactory;
import br.com.mac.projetoacacia.ui.recyclerview.adapter.NotasAdapter;
import br.com.mac.projetoacacia.utils.Utils;

public class NotasActivity extends AppCompatActivity {
    private NotasAdapter adapter;
    private NotaViewModel _viewModel;
    private ProgressBar _loadingProgressBar;
    private NotasLayoutBinding _viewDataBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _viewDataBinding = DataBindingUtil.setContentView(this, R.layout.notas_layout);

        setTitle("Notas");

        init();
    }

    private void init() {
        final Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        final String token = intent.getStringExtra("token");
        _viewDataBinding.notasAdd.setOnClickListener(addListener);
        _viewModel = new ViewModelProvider(this, new NotaViewModelFactory(new NotaRepository(this, token, id))).get(NotaViewModel.class);
        _loadingProgressBar = findViewById(R.id.notas_loading);
        startRecyclerView();
        buscaProdutos();
    }

    private View.OnClickListener addListener = view -> {
        formSaveNota();
    };


    private void startRecyclerView() {
        final RecyclerView listNotas = findViewById(R.id.notas_lista);
        adapter = new NotasAdapter(this, this::formEditNota);
        listNotas.setAdapter(adapter);
        adapter.setOnItemClickRemoveContextMenuListener(this::remove);
    }

    private void buscaProdutos() {
        _loadingProgressBar.setVisibility(View.VISIBLE);
        _viewModel.getAllNotes(new NotaRepository.CallbackNotas<List<Nota>>() {

            @Override
            public void success(List<Nota> notas) {
                adapter.atualiza(notas);
                _loadingProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void fail(String erro) {
                _loadingProgressBar.setVisibility(View.GONE);
                Utils.showToast(NotasActivity.this, "Não foi possível carregar as notas cadastradas");
            }
        });

    }

    private void salva(final Nota nota) {
        _loadingProgressBar.setVisibility(View.VISIBLE);
        _viewModel.salva(nota, new NotaRepository.CallbackNotas<Nota>() {

            @Override
            public void success(final Nota nota) {
                adapter.adiciona(nota);
                _loadingProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void fail(final String erro) {

                _loadingProgressBar.setVisibility(View.GONE);
                Utils.showToast(NotasActivity.this, "Não foi possível gravar a Nota!");
            }
        });
    }

    private void edita(int posicao, final Nota nota) {
        _loadingProgressBar.setVisibility(View.VISIBLE);
        _viewModel.edita(nota, new NotaRepository.CallbackNotas<Nota>() {
                    @Override
                    public void success(final Nota resultado) {
                        adapter.edita(posicao, nota);
                        _loadingProgressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void fail(final String erro) {
                        _loadingProgressBar.setVisibility(View.GONE);
                        Utils.showToast(NotasActivity.this, "Não foi possível alterar a Nota!");
                    }
                });
    }

    private void remove(int posicao, final Nota nota) {
        _loadingProgressBar.setVisibility(View.VISIBLE);
        _viewModel.remove(nota, new NotaRepository.CallbackNotas<Void>() {
            @Override
            public void success(Void resultado) {
                adapter.remove(posicao);
                _loadingProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void fail(String erro) {
                _loadingProgressBar.setVisibility(View.GONE);
                Utils.showToast(NotasActivity.this, "Não foi possível remover a Nota!");
            }
        });
    }

    private void formEditNota(int posicao, final Nota nota) {
        new NotaEditDialog(this, nota, notaRet -> edita(posicao, notaRet)).mostra();
    }

    private void formSaveNota() {
        new NotaSaveDialog(this, this::salva).mostra();
    }


}
