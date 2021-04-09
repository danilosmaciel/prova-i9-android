package br.com.mac.projetoacacia.retrofit.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class CallbackDefault<T> implements Callback<T> {

    private final CallBackDefault<T> callback;

    public CallbackDefault(final CallBackDefault<T> callback) {
        this.callback = callback;
    }

    @Override
    @EverythingIsNonNull
    public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()){
            T resultado = response.body();
            if(resultado != null){
                callback.success(resultado);
            }
        } else {
            callback.fail("Falha no retorno");
        }
    }

    @Override
    @EverythingIsNonNull
    public void onFailure(Call<T> call, Throwable t) {
        callback.fail("Falha" + t.getMessage());
    }

    public interface CallBackDefault<T> {
        void success(T resultado);
        void fail(String erro);
    }

}
