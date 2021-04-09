package br.com.mac.projetoacacia.ui.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;

import com.google.android.material.textfield.TextInputLayout;

import br.com.mac.projetoacacia.model.Nota;
import br.com.mac.projetoacacia.R;

public class NotaDialog {

    private final String titulo;
    private final String tituloBotaoPositivo;
    private final ConfirmacaoListener listener;
    private final Context _context;
    private Nota _nota;

    NotaDialog(Context context, String titulo, String tituloBotaoPositivo, ConfirmacaoListener listener) {
        this.titulo = titulo;
        this.tituloBotaoPositivo = tituloBotaoPositivo;
        this.listener = listener;
        this._context = context;
    }

    NotaDialog(final Context context, final  String titulo, final String tituloBotaoPositivo, final ConfirmacaoListener listener, final Nota nota) {
        this(context, titulo, tituloBotaoPositivo, listener);
        this._nota = nota;
    }

    public void mostra() {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(_context).inflate(R.layout.notas_form, null);
        tentaPreencherFormulario(view);
        new AlertDialog.Builder(_context)
                .setTitle(titulo)
                .setView(view)
                .setPositiveButton(tituloBotaoPositivo, (dialog, which) -> {
                    final EditText edTitle = getEditText(view, R.id.notas_form_title);
                    final EditText edContent = getEditText(view, R.id.notas_form_content);
                    criarNota(edTitle, edContent);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @SuppressLint("SetTextI18n")
    private void tentaPreencherFormulario(final View view) {
        if (_nota != null) {
            final TextView campoId = view.findViewById(R.id.notas_form_id);
            campoId.setText(String.valueOf(_nota.getId()));
            campoId.setVisibility(View.VISIBLE);
            final EditText edTitle = getEditText(view, R.id.notas_form_title);
            edTitle.setText(_nota.getTitle());
            final EditText edContent = getEditText(view, R.id.notas_form_content);
            edContent.setText(_nota.getContent());
        }
    }

    private void criarNota(final EditText edTitle,final EditText edContent) {
        final String title = edTitle.getText().toString();
        final String content = edContent.getText().toString();
        final String id = preencheId();
        final Nota nota = new Nota(title, content, id );
        listener.quandoConfirmado(nota);
    }

    private String preencheId() {
        if (_nota != null) {
            return _nota.getId();
        }
        return "";
    }

    private EditText getEditText(final View view, int idTextInputLayout) {
        TextInputLayout textInputLayout = view.findViewById(idTextInputLayout);
        return textInputLayout.getEditText();
    }

    public interface ConfirmacaoListener {
        void quandoConfirmado(final Nota nota);
    }

}
