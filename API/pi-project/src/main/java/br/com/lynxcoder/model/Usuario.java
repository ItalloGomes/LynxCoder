package br.com.lynxcoder.model;

import java.util.Objects;

public class Usuario {

    private Integer id;
    private String nome;
    private String cargo;
    private String login;
    private String senha;
    private Integer idEmpresa;
    private Integer idSquad;

    private Boolean isGestor;

    public Usuario(){

    }
    public Usuario(Integer id, String nome, String cargo, String login, String senha,
                   Empresa empresa, Squad squad, Boolean isGestor) {

        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.idEmpresa = empresa.getId();
        this.idSquad = squad.getId();
        this.isGestor = isGestor;

    }

    public Usuario(String nome, String cargo, String email, String senha,
                   Empresa empresa, Squad squad, Boolean isGestor) {

        this.nome = nome;
        this.cargo = cargo;
        this.login = email;
        this.senha = senha;
        this.idEmpresa = empresa.getId();
        this.idSquad = squad.getId();
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

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Integer getIdSquad() {
        return idSquad;
    }

    public void setIdSquad(Integer idSquad) {
        this.idSquad = idSquad;
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
