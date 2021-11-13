package br.com.lynxcoder.model;

import java.util.Objects;

public class Sprint {

    private Integer id;
    private String idTrello;
    private String descricao;
    private Boolean ativa;
    private Squad squad;

    public Sprint(Integer id, String idTrello, String descricao, Boolean ativa, Squad squad) {
        this.id = id;
        this.idTrello = idTrello;
        this.descricao = descricao;
        this.ativa = ativa;
        this.squad = squad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdTrello() {
        return idTrello;
    }

    public void setIdTrello(String idTrello) {
        this.idTrello = idTrello;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
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
        return Objects.equals(id, sprint.id) && Objects.equals(idTrello, sprint.idTrello) && Objects.equals(descricao, sprint.descricao) && Objects.equals(ativa, sprint.ativa) && Objects.equals(squad, sprint.squad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idTrello, descricao, ativa, squad);
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "id=" + id +
                ", idTrello='" + idTrello + '\'' +
                ", descricao='" + descricao + '\'' +
                ", ativa=" + ativa +
                ", squad=" + squad +
                '}';
    }
}
