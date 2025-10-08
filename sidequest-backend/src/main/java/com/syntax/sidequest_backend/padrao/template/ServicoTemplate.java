package com.syntax.sidequest_backend.padrao.template;

import java.util.List;

public abstract class ServicoTemplate<E, D> {
    
    public final E criar(D dto) {
        validarAntesDeGravar(dto);
        E entidade = converterParaEntidade(dto);
        entidade = executarOperacaoEspecificaCriacao(entidade, dto);
        E resultado = salvar(entidade);
        executarAposCriacao(resultado);
        return resultado;
    }
    
    public final E atualizar(D dto) {
        validarAntesDeGravar(dto);
        E entidade = converterParaEntidade(dto);
        entidade = executarOperacaoEspecificaAtualizacao(entidade, dto);
        E resultado = salvar(entidade);
        executarAposAtualizacao(resultado);
        return resultado;
    }
    
    public final void excluir(String id) {
        validarAntesDeExcluir(id);
        executarExclusao(id);
        executarAposExclusao(id);
    }
    
    public final List<E> listarTodos() {
        return buscarTodos();
    }
    
    protected abstract void validarAntesDeGravar(D dto);
    protected abstract E converterParaEntidade(D dto);
    protected abstract E salvar(E entidade);
    protected abstract void validarAntesDeExcluir(String id);
    protected abstract void executarExclusao(String id);
    protected abstract List<E> buscarTodos();
    
    protected E executarOperacaoEspecificaCriacao(E entidade, D dto) {
        return entidade;
    }
    
    protected E executarOperacaoEspecificaAtualizacao(E entidade, D dto) {
        return entidade;
    }
    
    protected void executarAposCriacao(E entidade) {
    }
    
    protected void executarAposAtualizacao(E entidade) {
    }
    
    protected void executarAposExclusao(String id) {
    }
}
