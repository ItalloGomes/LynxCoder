package br.com.lynxcoder.DAO;

import br.com.lynxcoder.controller.Conexao;
import br.com.lynxcoder.model.Processo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProcessosDAO {

    public void inserProcessos(Processo processo) {

        String sql = "insert into tb_processo values ( null, ?, ?, ?, ?, ?, ?)";

        Connection conn = Conexao.getConnection();

        try {

            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, processo.getPID());
            pstm.setString(2, processo.getNome());
            pstm.setString(3, processo.getStatus());
            //pstm.setDate(4, processo.getDataHorarioInicio());
            //pstm.setDate(5, processo.getDataHorarioFim());
            pstm.setInt(6, processo.getMaquina().getId());


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
