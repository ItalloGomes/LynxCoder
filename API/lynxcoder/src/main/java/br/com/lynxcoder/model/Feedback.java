package br.com.lynxcoder.model;

import java.util.Objects;

public class Feedback {

    private Integer id;
    private String tipo;
    private String mensagem;
    private Double aproveitamento;
    private Usuario usuario;
    private Sprint sprint;

    public Feedback() {}

    public Feedback(String tipo, String mensagem, Double aproveitamento, Usuario usuario, Sprint sprint) {
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.aproveitamento = aproveitamento;
        this.usuario = usuario;
        this.sprint = sprint;
    }

    public Feedback(Integer id, String tipo, String mensagem, Double aproveitamento, Usuario usuario, Sprint sprint) {
        this(tipo, mensagem, aproveitamento, usuario, sprint);
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

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
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
                ", sprint=" + sprint +
                '}';
    }
}
