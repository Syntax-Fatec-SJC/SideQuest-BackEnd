package com.syntax.api_gateway.configuracao;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Propriedades de configuração dos microserviços
 */
@ConfigurationProperties(prefix = "microservices")
public class PropriedadesMicroservicos {

    private Servico usuario = new Servico();
    private Servico projetos = new Servico();
    private Servico tarefas = new Servico();
    private Servico avisos = new Servico();

    public static class Servico {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public Servico getUsuario() {
        return usuario;
    }

    public void setUsuario(Servico usuario) {
        this.usuario = usuario;
    }

    public Servico getProjetos() {
        return projetos;
    }

    public void setProjetos(Servico projetos) {
        this.projetos = projetos;
    }

    public Servico getTarefas() {
        return tarefas;
    }

    public void setTarefas(Servico tarefas) {
        this.tarefas = tarefas;
    }

    public Servico getAvisos() {
        return avisos;
    }

    public void setAvisos(Servico avisos) {
        this.avisos = avisos;
    }
}
