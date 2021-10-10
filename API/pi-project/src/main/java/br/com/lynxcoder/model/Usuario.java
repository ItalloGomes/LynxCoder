package br.com.lynxcoder.model;

import java.util.Objects;

public class Usuario {

    private Integer id;
    private String nome;
    private String cargo;
    private String login;
    private String senha;
    private Empresa empresa;
    private Squad squad;

    private Boolean isGestor;

    public Usuario(){}

    public Usuario(Integer id, String nome, String cargo, String login, String senha,
                   Empresa empresa, Squad squad, Boolean isGestor) {

        this(nome, cargo, login, senha, empresa, squad, isGestor);
        this.id = id;

    }

    public Usuario(String nome, String cargo, String login, String senha,
                   Empresa empresa, Squad squad, Boolean isGestor) {

        this.nome = nome;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.empresa = empresa;
        this.squad = squad;
        this.isGestor = isGestor;

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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Squad getSquad() {
        return squad;
    }

    public void setSquad(Squad squad) {
        this.squad = squad;
    }

    public Boolean getGestor() {
        return isGestor;
    }

    public void setGestor(Boolean gestor) {
        isGestor = gestor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
