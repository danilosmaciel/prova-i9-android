package br.com.mac.projetoacacia.repository;

import android.content.Context;


import java.util.List;

import br.com.mac.projetoacacia.asynctask.BaseAsyncTask;
import br.com.mac.projetoacacia.database.ProjetoDatabase;
import br.com.mac.projetoacacia.database.dao.NotaDAO;
import br.com.mac.projetoacacia.model.Nota;
import br.com.mac.projetoacacia.retrofit.ProjetoRetrofit;
import br.com.mac.projetoacacia.retrofit.callback.CallbackDefault;
import br.com.mac.projetoacacia.retrofit.callback.CallbackVoid;
import br.com.mac.projetoacacia.retrofit.service.NotaService;
import retrofit2.Call;

public class NotaRepository{

    private final NotaDAO _dao;
    private final NotaService _service = new ProjetoRetrofit().getNotaService();
    private final String _token;
    private final String _idUser;

    public NotaRepository(final Context context, final String token, final String idUser) {
        final ProjetoDatabase db = ProjetoDatabase.getInstance(context);
        _dao = db.getNotaoDAO();
        _token = token;
        _idUser = idUser;
    }

    public void getNotas(final CallbackNotas<List<Nota>> callback) {
        getNotasFromDB(callback);
    }

    private void getNotasFromDB(CallbackNotas<List<Nota>> callback) {
        new BaseAsyncTask<>(_dao::getAll,
                resultado -> {
                    callback.success(resultado);
                    getNotasFromApi(callback);
                }).execute();
    }

    private void getNotasFromApi(CallbackNotas<List<Nota>> callback) {
        Call<List<Nota>> call = _service.buscaTodos(_token);
        call.enqueue(new CallbackDefault<>(
                new CallbackDefault.CallBackDefault<List<Nota>>() {
                    @Override
                    public void success(List<Nota> produtosNovos) {
                        atualizaInterno(produtosNovos, callback);
                    }

                    @Override
                    public void fail(String erro) {
                        callback.fail(erro);
                    }
                }));
    }

    private void atualizaInterno(List<Nota> notas, CallbackNotas<List<Nota>> callback) {
        new BaseAsyncTask<>(() -> {
            _dao.removeAll();
            _dao.saveAll(notas);
            return _dao.getAll();
        }, callback::success)
                .execute();
    }

    public void salva(final Nota nota, CallbackNotas<Nota> callback) {
        apiSave(nota, callback);
    }

    private void apiSave(final Nota nota, CallbackNotas<Nota> callback) {
        Call<Nota> call = _service.salva(_token,new Nota(nota.getTitle(), nota.getContent()));
        call.enqueue(new CallbackDefault<>(
                new CallbackDefault.CallBackDefault<Nota>() {
                    @Override
                    public void success(final Nota nota) {
                        salvaInterno(nota, callback);
                    }

                    @Override
                    public void fail(String erro) {
                        callback.fail(erro);
                    }
                }));
    }

    private void salvaInterno(final Nota nota, CallbackNotas<Nota> callback) {
        new BaseAsyncTask<>(() -> {
            //String id = String.valueOf(_dao.save(nota));
            _dao.save(nota);
            final Nota nota1 = _dao.getNota(nota.getId());
            return nota1;
        }, callback::success).execute();
    }

    public void edita(final Nota nota, CallbackNotas<Nota> callback) {
        final String id = nota.getId();
        Call<Nota> call = _service.edita(_token, id, new Nota(nota.getTitle(),nota.getContent()));
        call.enqueue(new CallbackDefault<>(
                new CallbackDefault.CallBackDefault<Nota>() {
                    @Override
                    public void success(final Nota notaRet) {
                        new BaseAsyncTask<>(() -> {
                            if(notaRet.getUserId() == null){
                                notaRet.setUserId(_idUser);
                            }

                            _dao.update(notaRet);
                            return notaRet;
                        }, callback::success).execute();
                    }

                    @Override
                    public void fail(String erro) {
                        callback.fail(erro);
                    }
                }));
    }

    public void remove(final Nota nota, CallbackNotas<Void> callback) {
        removeNaApi(nota, callback);
    }

    private void removeNaApi(final Nota nota, CallbackNotas<Void> callback) {
        Call<Void> call = _service.remove(_token, nota.getId());
        call.enqueue(new CallbackVoid(
                new CallbackVoid.RespostaCallback() {
                    @Override
                    public void success() {
                        removeInterno(nota, callback);
                    }

                    @Override
                    public void fail(String erro) {
                        callback.fail(erro);
                    }
                }));
    }

    private void removeInterno(final Nota nota, CallbackNotas<Void> callback) {
        new BaseAsyncTask<>(() -> {
            _dao.remove(nota);
            return null;
        }, callback::success)
                .execute();
    }

    public interface CallbackNotas<T> {
        void success(final T resultado);
        void fail(final String erro);
    }

}
