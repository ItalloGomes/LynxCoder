package br.com.lynxcoder.DAO;

import br.com.lynxcoder.controller.Conexao;
import br.com.lynxcoder.model.Colaborador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ColaboradorDAO {

    public void saveColaborador(Colaborador col) {

    }

    public Colaborador findCollaboratorByLogin(String login){

        String sql = "select * from tb_usuario where login = ?";
        Connection conn = Conexao.getConnection();
        try{
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, login);

            ResultSet rs = pstm.executeQuery();

            if(rs.wasNull()){
                return null;
            }

            Colaborador col = new Colaborador();
            col.setId(rs.getInt("id_usuario"));
            col.setNome(rs.getString("nome_usuario"));
            col.setCargo(rs.getString("cargo"));
            col.setLogin(rs.getString("login"));
            col.setSenha(rs.getString("senha"));
            col.setIdSupervisor(rs.getInt("fk_supervisor"));
            col.setIdSquad(rs.getInt("fk_squad"));
            col.setIdEmpresa(rs.getInt("fk_empresa"));

            return col;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;

    }

}
