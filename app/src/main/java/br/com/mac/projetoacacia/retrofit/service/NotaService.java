package br.com.mac.projetoacacia.retrofit.service;

import java.util.List;

import br.com.mac.projetoacacia.model.Nota;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NotaService {
    @GET("Notes")
    Call<List<Nota>> buscaTodos(@Header("authorization") String token);

    @POST("Notes")
    Call<Nota> salva(@Header("authorization") String token, @Body Nota nota);

    @PUT("Notes/{id}")
    Call<Nota> edita(@Header("authorization") String token, @Path("id") String id, @Body Nota nota);

    @DELETE("Notes/{id}")
    Call<Void> remove(@Header("authorization") String token, @Path("id") String id);

}

