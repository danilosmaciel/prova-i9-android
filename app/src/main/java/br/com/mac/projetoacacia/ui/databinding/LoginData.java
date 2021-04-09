package br.com.mac.projetoacacia.ui.databinding;

import androidx.databinding.ObservableField;

import br.com.mac.projetoacacia.model.Usuario;

public class LoginData {

    public Usuario user = new Usuario();
    public ObservableField<String> userName = new ObservableField(user.username);
    public ObservableField<String> password = new ObservableField(user.password);
}
