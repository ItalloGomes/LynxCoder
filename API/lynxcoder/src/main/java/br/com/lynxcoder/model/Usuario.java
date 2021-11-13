package br.com.lynxcoder.model;

import java.util.Objects;

public class Usuario {

    private Integer id;
    private String idTrello;
    private String nome;
    private String foto;
    private String login;
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

    public Usuario(Integer id, String idTrello, String nome, String foto, String login, String senha, Boolean isGestor, Usuario supervisor, Empresa empresa, Squad squad) {
        this.id = id;
        this.idTrello = idTrello;
        this.nome = nome;
        this.foto = foto;
        this.login = login;
        this.senha = senha;
        this.isGestor = isGestor;
        this.supervisor = supervisor;
        this.empresa = empresa;
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
        return Objects.equals(id, usuario.id) && Objects.equals(idTrello, usuario.idTrello) && Objects.equals(nome, usuario.nome) && Objects.equals(foto, usuario.foto) && Objects.equals(login, usuario.login) && Objects.equals(senha, usuario.senha) && Objects.equals(isGestor, usuario.isGestor) && Objects.equals(supervisor, usuario.supervisor) && Objects.equals(empresa, usuario.empresa) && Objects.equals(squad, usuario.squad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idTrello, nome, foto, login, senha, isGestor, supervisor, empresa, squad);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", idTrello='" + idTrello + '\'' +
                ", nome='" + nome + '\'' +
                ", foto='" + foto + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", isGestor=" + isGestor +
                ", supervisor=" + supervisor +
                ", empresa=" + empresa +
                ", squad=" + squad +
                '}';
    }
}
