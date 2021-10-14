package br.com.lynxcoder.DAO;

import br.com.lynxcoder.controller.Conexao;
import br.com.lynxcoder.model.Maquina;
import br.com.lynxcoder.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MaquinaDAO {

    public boolean hasMaquina(Usuario user){

        String sql = "select * from tb_maquina where fk_usuario = ?";

        Connection conn = Conexao.getConnection();
        try {

            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, user.getId());

            ResultSet rs = pstm.executeQuery();

            if (!rs.next()) {
                Conexao.closeConnection(conn, pstm);
                return false;
            }

            Conexao.closeConnection(conn, pstm);
            return true;

        }catch (SQLException e){
            Conexao.closeConnection(conn);
            e.printStackTrace();
        }

        return true;

    }

    public void adicionarMaquina(Usuario user, Maquina maquina){

        if(!hasMaquina(user)){

            String sql = "insert into tb_maquina values ( null ,?,?,?,?,?,?,?)";

            Connection conn = Conexao.getConnection();

            try {

                PreparedStatement pstm = conn.prepareStatement(sql);
                pstm.setString(1, maquina.getMarca());
                pstm.setString(2, maquina.getModelo());
                pstm.setString(3, maquina.getTipoCPU());
                pstm.setDouble(4, maquina.getTotalMemoria());
                pstm.setDouble(5, maquina.getTotalDisco());
                pstm.setString(6, maquina.getSistemaOperacional());
                pstm.setInt(7, user.getId());

                pstm.executeQuery();

                System.out.println("Máquina Cadastrada");

                Conexao.closeConnection(conn, pstm);

            } catch (SQLException e) {
                Conexao.closeConnection(conn);
                e.printStackTrace();
            }

        }

    }

}
