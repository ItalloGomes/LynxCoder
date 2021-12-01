package br.com.lynxcoder.model;

import java.util.Date;
import java.util.Objects;

public class Tarefa {

    private Integer id;
    private String nome;
    private Integer pontos;
    private Double totalConcluido;
    private Date prazo;
    private Sprint sprint;
    private Usuario usuario;

    public Tarefa() {
    }

    public Tarefa(String nome, Integer pontos, Double totalConcluido, Date prazo, Sprint sprint, Usuario usuario) {
        this.nome = nome;
        this.pontos = pontos;
        this.totalConcluido = totalConcluido;
        this.prazo = prazo;
        this.sprint = sprint;
        this.usuario = usuario;
    }

    public Tarefa(Integer id, String nome, Integer pontos, Double totalConcluido, Date prazo, Sprint sprint, Usuario usuario) {
        this(nome, pontos, totalConcluido, prazo, sprint, usuario);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public Double getTotalConcluido() {
        return totalConcluido;
    }

    public void setTotalConcluido(Double totalConcluido) {
        this.totalConcluido = totalConcluido;
    }

    public Date getPrazo() {
        return prazo;
    }

    public void setPrazo(Date prazo) {
        this.prazo = prazo;
    }

    public Sprint getSprint() {
        return sprint;
    }

    public void setSprint(Sprint sprint) {
        this.sprint = sprint;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return id.equals(tarefa.id) && nome.equals(tarefa.nome) && pontos.equals(tarefa.pontos) && totalConcluido.equals(tarefa.totalConcluido) && prazo.equals(tarefa.prazo) && sprint.equals(tarefa.sprint) && usuario.equals(tarefa.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, pontos, totalConcluido, prazo, sprint, usuario);
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", pontos=" + pontos +
                ", totalConcluido=" + totalConcluido +
                ", prazo=" + prazo +
                ", sprint=" + sprint +
                ", usuario=" + usuario +
                '}';
    }
}
