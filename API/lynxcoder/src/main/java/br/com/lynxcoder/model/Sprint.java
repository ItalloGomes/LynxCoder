package br.com.lynxcoder.model;

import java.util.Objects;

public class Sprint {

    private Integer id;
    private String descricao;
    private Squad squad;

    public Sprint() {}

    public Sprint(Integer id, String descricao, Squad squad) {
        this.id = id;
        this.descricao = descricao;
        this.squad = squad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Squad getSquad() {
        return squad;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sprint sprint = (Sprint) o;
        return id.equals(sprint.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", squad=" + squad +
                '}';
    }
}
