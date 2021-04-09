package br.com.mac.projetoacacia.ui.dialog;

import android.content.Context;
import br.com.mac.projetoacacia.model.Nota;

public class NotaEditDialog extends NotaDialog{

    public NotaEditDialog(final Context context, final Nota nota, ConfirmacaoListener listener) {
        super(context, "Editar", "Editar", listener, nota);
    }
}
