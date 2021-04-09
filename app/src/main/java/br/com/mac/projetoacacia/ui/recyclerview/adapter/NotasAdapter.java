package br.com.mac.projetoacacia.ui.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.mac.projetoacacia.R;
import br.com.mac.projetoacacia.model.Nota;

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.ViewHolder> {

    private final OnItemClickListener onItemClickListener;
    private OnItemClickRemoveContextMenuListener onItemClickRemoveContextMenuListener = (posicao, notaDeletada) -> {};
    private final Context context;
    private final List<Nota> notas = new ArrayList<>();

    public NotasAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }

    public void setOnItemClickRemoveContextMenuListener(OnItemClickRemoveContextMenuListener onItemClickRemoveContextMenuListener) {
        this.onItemClickRemoveContextMenuListener = onItemClickRemoveContextMenuListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.nota_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Nota nota = notas.get(position);
        holder.vincula(nota);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public void atualiza(List<Nota> listaNotas) {
        notifyItemRangeRemoved(0, this.notas.size());
        this.notas.clear();
        this.notas.addAll(listaNotas);
        this.notifyItemRangeInserted(0, this.notas.size());
    }

    public void adiciona(final Nota... arrayNotas) {
        int tamanhoAtual = this.notas.size();
        Collections.addAll(this.notas, arrayNotas);
        int tamanhoNovo = this.notas.size();
        notifyItemRangeInserted(tamanhoAtual, tamanhoNovo);
    }

    public void edita(int posicao, Nota nota) {
        this.notas.set(posicao, nota);
        notifyItemChanged(posicao);
    }

    public void remove(int posicao) {
        this.notas.remove(posicao);
        notifyItemRemoved(posicao);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView _tvId;
        private final TextView _tvTitle;
        private final TextView _tvCotent;
        private final Button _ivDelete;
        private Nota _nota;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            _tvId = itemView.findViewById(R.id.nota_id);
            _tvTitle = itemView.findViewById(R.id.nota_titulo);
            _tvCotent = itemView.findViewById(R.id.nota_content);
            _ivDelete = itemView.findViewById(R.id.nota_delete);
            configuraItemClique(itemView);
            configuraMenuDeContexto(itemView);

            _ivDelete.setOnClickListener(view -> {
                onItemClickRemoveContextMenuListener.onItemClick(getAdapterPosition(), _nota);
            });
        }

        private void configuraMenuDeContexto(@NonNull View itemView) {
            itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
                new MenuInflater(context).inflate(R.menu.notas_menu, menu);
                menu.findItem(R.id.menu_notas_remove).setOnMenuItemClickListener(item -> {
                    final int posicao = getAdapterPosition();
                    onItemClickRemoveContextMenuListener.onItemClick(posicao, _nota);
                    return true;
                });
            });
        }

        private void configuraItemClique(@NonNull View itemView) {
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(getAdapterPosition(), _nota));
        }

        void vincula(Nota nota) {
            this._nota = nota;
            _tvId.setText(String.valueOf(_nota.getId()));
            _tvTitle.setText(_nota.getTitle());
            _tvCotent.setText(_nota.getContent());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int posicao, Nota nota);
    }

    public interface OnItemClickRemoveContextMenuListener {
        void onItemClick(int posicao, Nota nota);
    }
}
