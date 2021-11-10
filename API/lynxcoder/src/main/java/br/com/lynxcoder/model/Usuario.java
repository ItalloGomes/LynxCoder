package br.com.lynxcoder.model;

import java.util.Objects;

public class Usuario {

    private Integer id;
    private String nome;
    private String foto;
    private String cargo;
    private String login;
    private String email;
    private String senha;
    private Boolean isGestor;
    private Usuario supervisor;
    private Empresa empresa;
    private Squad squad;

    public Usuario(){}

    public Usuario(Integer id){
        this.id = id;
        hashCode();
    }

    public Usuario(String nome, String foto, String cargo, String login, String email, String senha,
                   Boolean isGestor, Usuario supervisor, Empresa empresa, Squad squad) {
        this.nome = nome;
        this.foto = foto;
        this.cargo = cargo;
        this.login = login;
        this.email = email;
        this.senha = senha;
        this.isGestor = isGestor;
        this.supervisor = supervisor;
        this.empresa = empresa;
        this.squad = squad;
    }

    public Usuario(Integer id, String nome, String foto, String cargo, String login, String email, String senha,
                   Boolean isGestor, Usuario supervisor, Empresa empresa, Squad squad) {

        this(nome, foto,cargo, login, email, senha, isGestor, supervisor, empresa, squad);
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getGestor() {
        return isGestor;
    }

    public void setGestor(Boolean gestor) {
        isGestor = gestor;
    }

    public Usuario getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Usuario supervisor) {
        this.supervisor = supervisor;
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

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", foto='" + foto + '\'' +
                ", cargo='" + cargo + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", isGestor=" + isGestor +
                ", supervisor=" + supervisor +
                ", empresa=" + empresa +
                ", squad=" + squad +
                '}';
    }
}
