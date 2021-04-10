package br.com.mac.projetoacacia.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import br.com.mac.projetoacacia.model.Usuario;
import br.com.mac.projetoacacia.repository.LoginRepository;
import br.com.mac.projetoacacia.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewmodel extends ViewModel {
    private LoginRepository _repository;

    public LoginViewmodel(final LoginRepository repository) {
        _repository = repository;
    }

    public void validadeAccess(final Usuario user, final CallbackLogin callbackLogin) {
        _repository.isValidateUser(user).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    final Usuario userRet = response.body();
                    callbackLogin.returned(true,"", userRet);
                }
                callbackLogin.returned(false,Utils.getErrorMessage(response.code()), null);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callbackLogin.returned(false,t.getMessage(), null);
            }
        });
    }

    public void registerUser(final Usuario user, final CallbackLoginNoUser callbackLogin) {
        _repository.registerUser(user).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.isSuccessful()){
                    callbackLogin.returned(true,"Usuario Registrado com sucesso! Tente Fazer Login!");
                }
                callbackLogin.returned(false, Utils.getErrorMessage(response.code()));
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callbackLogin.returned(false,t.getMessage());
            }
        });
    }

    public String getMessage(final String message) {
        switch (message){
           case "Unauthorized":
                return "Usuário não reconhecido!";
            default:
                return message;
        }
    }

    public interface CallbackLogin{
        boolean returned(final boolean isSucess, final String message, final Usuario user);
    }

    public interface CallbackLoginNoUser{
        boolean returned(final boolean isSucess, final String message);
    }
}
