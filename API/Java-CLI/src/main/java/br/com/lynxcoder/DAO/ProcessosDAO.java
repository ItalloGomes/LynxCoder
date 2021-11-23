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

    public void save(Processo processo) {

        String sql = "insert into tb_processo values ( null, ?, ?, ?, ?)";
        String sqlServer = "insert into tb_processo values ( ?, ?, ?, ?)";

        Connection conn = Conexao.getConnection();

        try {

            String now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

            PreparedStatement pstm = conn.prepareStatement(sqlServer);
            pstm.setString(1, processo.getPID());
            pstm.setString(2, processo.getNome());
            pstm.setString(3, now);
            pstm.setInt(4, processo.getMaquina().getId());

            pstm.execute();

            System.out.println("Processo cadastrado!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
