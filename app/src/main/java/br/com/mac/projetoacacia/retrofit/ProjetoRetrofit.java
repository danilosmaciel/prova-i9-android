package br.com.mac.projetoacacia.retrofit;


import br.com.mac.projetoacacia.retrofit.service.NotaService;
import br.com.mac.projetoacacia.retrofit.service.UsuarioService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProjetoRetrofit {

    private static final String URL_BASE_TESTE = "http://192.168.20.249:8080/";
    private static final String URL_BASE = "https://prova-i9.herokuapp.com/api/";
    private final UsuarioService usuarioService;
    private final NotaService notaService;

    public ProjetoRetrofit() {
        OkHttpClient client = configuraClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        usuarioService = retrofit.create(UsuarioService.class);
        notaService = retrofit.create(NotaService.class);
    }

    private OkHttpClient configuraClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    public UsuarioService getUsuarioService() {
        return usuarioService;
    }

    public NotaService getNotaService() {return notaService;}
}
