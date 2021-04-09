package br.com.mac.projetoacacia.ui.dialog;

import android.content.Context;

public class NotaSaveDialog extends  NotaDialog{
    public NotaSaveDialog(final Context context, ConfirmacaoListener listener) {
        super(context, "Gravar","Gravar", listener);
    }
}
