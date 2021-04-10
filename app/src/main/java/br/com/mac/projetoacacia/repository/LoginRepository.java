package br.com.mac.projetoacacia.repository;

import android.content.Context;

import java.util.Calendar;

import br.com.mac.projetoacacia.model.Usuario;
import br.com.mac.projetoacacia.retrofit.ProjetoRetrofit;
import br.com.mac.projetoacacia.retrofit.service.UsuarioService;
import retrofit2.Call;

public class LoginRepository {

    private final UsuarioService service;

    public LoginRepository(final Context context) {
        service = new ProjetoRetrofit().getUsuarioService();
    }

    public Call<Usuario> isValidateUser(final Usuario user){
        return service.login(new Usuario(user.getUsername(), user.getPassword()));
    }

    public Call<Usuario> registerUser(final Usuario user) {
        return service.register(new Usuario(user.getUsername(), Calendar.getInstance().getTimeInMillis() +"@gmail.com",user.getPassword()));
    }

}