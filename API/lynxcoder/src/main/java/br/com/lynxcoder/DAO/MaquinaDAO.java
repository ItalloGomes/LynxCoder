package br.com.lynxcoder.DAO;

import br.com.lynxcoder.controller.Conexao;
import br.com.lynxcoder.model.Maquina;
import br.com.lynxcoder.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MaquinaDAO {

    public Maquina findMaquina(Usuario user){

        String sql = "select * from tb_maquina where fk_usuario = ?";

        Connection conn = Conexao.getConnection();
        try {

            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1, user.getId());

            ResultSet rs = pstm.executeQuery();

            Maquina maq10 = null;
            while (rs.next()){
                maq10 = new Maquina(
                        rs.getInt("id_maquina"),
                        rs.getString("hostname"),
                        rs.getString("tipoCPU"),
                        rs.getString("totalMemoria"),
                        rs.getString("totalDisco"),
                        rs.getString("sistemaOperacional"),
                        new Usuario(rs.getInt("fk_usuario"))
                );
            }

            Conexao.closeConnection(conn, pstm);
            return maq10;

        }catch (SQLException e){
            Conexao.closeConnection(conn);
            e.printStackTrace();
        }

        return null;

    }

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

            String sql = "insert into tb_maquina values (null, null, ?, ?, ?, ?, ?)";
            String sqlServer = "insert into tb_maquina values (null, ?, ?, ?, ?, ?)";

            Connection conn = Conexao.getConnection();

            try {

                PreparedStatement pstm = conn.prepareStatement(sqlServer);
                pstm.setString(1, maquina.getTipoCPU());
                pstm.setString(2, maquina.getTotalMemoria());
                pstm.setString(3, maquina.getTotalDisco());
                pstm.setString(4, maquina.getSistemaOperacional());
                pstm.setInt(5, user.getId());

                pstm.execute();

                System.out.println("Máquina Cadastrada");

                Conexao.closeConnection(conn, pstm);

            } catch (SQLException e) {
                Conexao.closeConnection(conn);
                e.printStackTrace();
            }

        }

    }

}
