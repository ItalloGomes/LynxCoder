package br.com.lynxcoder.DAO;

import br.com.lynxcoder.controller.Conexao;
import br.com.lynxcoder.model.Leitura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LeituraDAO {


    public void save(Leitura leitura){

        LogDAO logDao = new LogDAO();

        String sql = "insert into tb_leitura values ( null, ?, ?, ?, ?, ?)";
        String sqlServer = "insert into tb_leitura values ( ?, ?, ?, ?, ?)";

        Connection conn = Conexao.getConnection();

        try {

            String now = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

            PreparedStatement pstm = conn.prepareStatement(sqlServer);
            pstm.setDouble(1, leitura.getPorcentagemUsoCPU());
            pstm.setDouble(2, leitura.getPorcentagemUsoMemoria());
            pstm.setDouble(3, leitura.getPorcentagemUsoDisco());
            pstm.setObject(4, now);
            pstm.setInt(5, leitura.getMaquina().getId());

            pstm.execute();
            logDao.escreverLog("Leitura cadastrada!");
            System.out.println("Leitura cadastrada!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
