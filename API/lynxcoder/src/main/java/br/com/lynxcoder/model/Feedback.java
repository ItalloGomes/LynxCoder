package br.com.lynxcoder.model;

import java.util.Objects;

public class Feedback {

    private Integer id;
    private String tipo;
    private String mensagem;
    private Double aproveitamento;
    private Usuario usuario;
    private Tarefa tarefa;

    public Feedback() {}

    public Feedback(String tipo, String mensagem, Double aproveitamento, Usuario usuario, Tarefa tarefa) {
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.aproveitamento = aproveitamento;
        this.usuario = usuario;
        this.tarefa = tarefa;
    }

    public Feedback(Integer id, String tipo, String mensagem, Double aproveitamento, Usuario usuario, Tarefa tarefa) {
        this(tipo, mensagem, aproveitamento, usuario, tarefa);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Double getAproveitamento() {
        return aproveitamento;
    }

    public void setAproveitamento(Double aproveitamento) {
        this.aproveitamento = aproveitamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return id.equals(feedback.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", mensagem='" + mensagem + '\'' +
                ", aproveitamento=" + aproveitamento +
                ", usuario=" + usuario +
                ", tarefa=" + tarefa +
                '}';
    }
}
