package br.com.lynxcoder.DAO;

import br.com.lynxcoder.controller.Conexao;
import br.com.lynxcoder.model.Empresa;
import br.com.lynxcoder.model.Squad;
import br.com.lynxcoder.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public Usuario logar(String usuario, String senha){

        String sql = "SELECT * FROM tb_usuario WHERE login = ? and senha = ?;";

        try (Connection conn = Conexao.getConnection()) {

            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, usuario);
            pstm.setString(2, senha);

            ResultSet rs = pstm.executeQuery();

            Usuario user = new Usuario();
            while (rs.next()) {
                user.setId(rs.getInt("id_usuario"));
                user.setNome(rs.getString("nome_usuario"));
                user.setCargo(rs.getString("cargo"));
                user.setLogin(rs.getString("login"));
                user.setSenha(rs.getString("senha"));
                user.setGestor(rs.getBoolean("isGestor"));
                user.setSupervisor(new Usuario(rs.getInt("fk_supervisor")));
                user.setSquad(new Squad(rs.getInt("fk_squad")));
                user.setEmpresa(new Empresa(rs.getInt("fk_empresa")));
            }

            Conexao.closeConnection(conn, pstm);

            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

}
