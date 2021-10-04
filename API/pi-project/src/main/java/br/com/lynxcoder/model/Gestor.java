package br.com.lynxcoder.model;

public class Gestor extends Usuario{

    public Gestor(String nome, String cargo, String login, String senha,
                  Empresa empresa, Squad squad) {

        super(nome, cargo, login, senha, empresa, squad, true);
    }
}
