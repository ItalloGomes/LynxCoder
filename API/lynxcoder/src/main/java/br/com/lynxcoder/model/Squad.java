package br.com.lynxcoder.model;

import java.util.Objects;

public class Squad {

    private Integer id;
    private String nome;
    private String descricao;

    public Squad(){};

    public Squad(Integer id){
        this.id = id;
        hashCode();
    }

    public Squad(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Squad(Integer id, String nome, String descricao) {

        this(nome, descricao);
        this.id = id;

    }

    public Integer getId() {
        return id;
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
        return Objects.equals(id, squad.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
