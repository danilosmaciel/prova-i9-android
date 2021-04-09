package br.com.mac.projetoacacia.retrofit.service;

import br.com.mac.projetoacacia.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsuarioService {

    @GET("Users/{id}")
    Call<Usuario> edita(@Path("id") long id, @Body Usuario usuario);

    @POST("Users/login")
    Call<Usuario> login(@Body Usuario usuario);

    @POST("Users")
    Call<Usuario> register(@Body Usuario usuario);
}
