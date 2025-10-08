package com.syntax.sidequest_backend.service.interfaces;

import java.util.List;

public interface ServicoBase<E, D> {
    E criar(D dto);
    E atualizar(D dto);
    void excluir(String id);
    List<E> listarTodos();
}
