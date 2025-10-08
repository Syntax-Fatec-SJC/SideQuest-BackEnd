package com.syntax.sidequest_backend.padrao.observer;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorEventos<T> {
    
    private final List<Observador<T>> observadores;
    
    public GerenciadorEventos() {
        this.observadores = new ArrayList<>();
    }
    
    public void registrarObservador(Observador<T> observador) {
        if (!observadores.contains(observador)) {
            observadores.add(observador);
        }
    }
    
    public void removerObservador(Observador<T> observador) {
        observadores.remove(observador);
    }
    
    public void notificar(String evento, T dados) {
        for (Observador<T> observador : observadores) {
            observador.atualizar(evento, dados);
        }
    }
}
