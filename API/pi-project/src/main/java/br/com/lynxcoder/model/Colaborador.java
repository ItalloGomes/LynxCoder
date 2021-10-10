package br.com.lynxcoder.model;

public class Colaborador extends Usuario {

    private int idSupervisor;

    public Colaborador(){}

    public Colaborador(Integer id, String nome, String cargo, String login, String senha,
                       Empresa empresa, Squad squad, Gestor supervisor) {

        super(id, nome, cargo, login, senha, empresa, squad, false);
        this.idSupervisor = supervisor.getId();
    }

    public int getIdSupervisor() {
        return idSupervisor;
    }

    public void setIdSupervisor(int idSupervisor) {
        this.idSupervisor = idSupervisor;
    }
}
