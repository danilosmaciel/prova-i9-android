package br.com.mac.projetoacacia.ui.viewmodel;


import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import br.com.mac.projetoacacia.repository.LoginRepository;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final LoginRepository repository;

    public LoginViewModelFactory(final LoginRepository repository){
        this.repository = repository;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginViewmodel(repository);
    }
}
