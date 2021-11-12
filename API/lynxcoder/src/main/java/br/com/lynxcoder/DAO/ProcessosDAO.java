package br.com.lynxcoder.DAO;

import br.com.lynxcoder.controller.Conexao;
import br.com.lynxcoder.model.Processo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class ProcessosDAO {

<<<<<<< HEAD
    public void insertProcessos(Processo processo) {
=======
    public void insertProcessos(List<Processo> processos) {
>>>>>>> 71c76560366283c62ea17451b8f08f67a0a24031

        String sql = "insert into tb_processo values ( null, ?, ?, ?, ?, ?, ?)";

        Connection conn = Conexao.getConnection();

        try {

            for (Processo p: processos) {
                String now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

<<<<<<< HEAD
            pstm.execute();

            Conexao.closeConnection(conn, pstm);

=======
                PreparedStatement pstm = conn.prepareStatement(sql);
                pstm.setString(1, p.getPID());
                pstm.setString(2, p.getNome());
                pstm.setString(3, p.getStatus());
                pstm.setString(4, now);
                pstm.setInt(5, p.getMaquina().getId());

                pstm.execute();

                System.out.println("Processo cadastrado!");
            }
>>>>>>> 71c76560366283c62ea17451b8f08f67a0a24031

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
