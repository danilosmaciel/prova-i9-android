package br.com.mac.projetoacacia.ui.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.List;

import br.com.mac.projetoacacia.model.Nota;
import br.com.mac.projetoacacia.repository.NotaRepository;

public class NotaViewModel  extends ViewModel {
    private final NotaRepository _repository;

    public NotaViewModel(final NotaRepository repository) {
        _repository = repository;
    }

    public void getAllNotes(final NotaRepository.CallbackNotas<List<Nota>> listCallbackNotas) {
        _repository.getNotas(listCallbackNotas);
    }

    public void salva(final Nota nota, final NotaRepository.CallbackNotas<Nota> notaCallbackNotas) {
        _repository.salva(nota, notaCallbackNotas);
    }

    public void edita(final Nota nota, final NotaRepository.CallbackNotas<Nota> notaCallbackNotas) {
        _repository.edita(nota, notaCallbackNotas);
    }

    public void remove(Nota nota, NotaRepository.CallbackNotas<Void> voidCallbackNotas) {
        _repository.remove(nota, voidCallbackNotas);
    }
}
