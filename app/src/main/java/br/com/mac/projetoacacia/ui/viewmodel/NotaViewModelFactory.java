package br.com.mac.projetoacacia.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.mac.projetoacacia.repository.NotaRepository;

public class NotaViewModelFactory  implements ViewModelProvider.Factory {

    private final NotaRepository repository;

    public NotaViewModelFactory(final NotaRepository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NotaViewModel(repository);
    }
}
