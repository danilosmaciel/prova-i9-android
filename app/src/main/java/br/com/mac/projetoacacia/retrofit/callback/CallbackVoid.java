package br.com.mac.projetoacacia.retrofit.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class CallbackVoid implements Callback<Void> {

    private final RespostaCallback callback;

    public CallbackVoid(final RespostaCallback callback) {
        this.callback = callback;
    }

    @Override
    @EverythingIsNonNull
    public void onResponse(final Call<Void> call, final Response<Void> response) {
        if(response.isSuccessful()){
            callback.success();
        } else {
            callback.fail("Falha ao tentar");
        }
    }

    @Override
    @EverythingIsNonNull
    public void onFailure(final Call<Void> call, final Throwable t) {
        callback.fail("Falha na comunicação" + t.getMessage());
    }

    public interface RespostaCallback {
        void success();
        void fail(final String erro);
    }

}
