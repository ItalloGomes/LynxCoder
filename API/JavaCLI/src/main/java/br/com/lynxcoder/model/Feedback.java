package br.com.lynxcoder.model;

import java.util.Objects;

public class Feedback {

    private Integer id;
    private String mensagem;
    private Double aproveitamento;
    private Double facilidade;
    private Usuario usuario;
    private Sprint sprint;

    public Feedback(Integer id, String mensagem, Double aproveitamento, Double facilidade, Usuario usuario, Sprint sprint) {
        this.id = id;
        this.mensagem = mensagem;
        this.aproveitamento = aproveitamento;
        this.facilidade = facilidade;
        this.usuario = usuario;
        this.sprint = sprint;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getFacilidade() {
        return facilidade;
    }

    public void setFacilidade(Double facilidade) {
        this.facilidade = facilidade;
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
        return Objects.equals(id, feedback.id) && Objects.equals(mensagem, feedback.mensagem) && Objects.equals(aproveitamento, feedback.aproveitamento) && Objects.equals(facilidade, feedback.facilidade) && Objects.equals(usuario, feedback.usuario) && Objects.equals(sprint, feedback.sprint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mensagem, aproveitamento, facilidade, usuario, sprint);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", mensagem='" + mensagem + '\'' +
                ", aproveitamento=" + aproveitamento +
                ", facilidade=" + facilidade +
                ", usuario=" + usuario +
                ", sprint=" + sprint +
                '}';
    }
}
