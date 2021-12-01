package br.com.lynxcoder.model;

import java.util.Objects;

public class Squad {

    private Integer id;
    private Double idTrello;
    private String nome;
    private String descricao;

    public Squad(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getIdTrello() {
        return idTrello;
    }

    public void setIdTrello(Double idTrello) {
        this.idTrello = idTrello;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Squad squad = (Squad) o;
        return Objects.equals(id, squad.id) && Objects.equals(idTrello, squad.idTrello) && Objects.equals(nome, squad.nome) && Objects.equals(descricao, squad.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idTrello, nome, descricao);
    }

    @Override
    public String toString() {
        return "Squad{" +
                "id=" + id +
                ", idTrello=" + idTrello +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
