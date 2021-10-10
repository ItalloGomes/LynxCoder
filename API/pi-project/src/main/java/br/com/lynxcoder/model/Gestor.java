package br.com.lynxcoder.model;

public class Gestor extends Usuario{

    public Gestor(){}

    public Gestor(Integer id, String nome, String cargo, String login, String senha,
                  Empresa empresa, Squad squad) {

        super(id, nome, cargo, login, senha, empresa, squad, true);
    }

}
