package br.com.lynxcoder;

import br.com.lynxcoder.DAO.LogDAO;
import br.com.lynxcoder.DAO.UsuarioDAO;
import br.com.lynxcoder.integration.slack.DAO.SlackDAO;
import br.com.lynxcoder.model.Usuario;
import br.com.lynxcoder.view.Dashboard;

import javax.swing.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

//        LoginScreen screen = new LoginScreen();

        UsuarioDAO userDAO = new UsuarioDAO();
        SlackDAO slackDAO = new SlackDAO();
        LogDAO logDAO = new LogDAO();

        System.out.println("Login:");
        String login = in.next();
        System.out.println("Senha:");
        String password = in.next();

        Usuario user = userDAO.logar(login, password);

        if (user != null) {
            System.out.println("Logando...");

            Date data = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String dataFormatada = dateFormat.format(data);

            logDAO.criarLog(dataFormatada);
            logDAO.escreverLog(" Logou com sucesso");

            Dashboard dashboard = new Dashboard(user, logDAO);
            slackDAO.welcomeMessage(user);
        } else {
            JOptionPane.showMessageDialog(null, "Usuário e/ou senha incorreto(s)!");
        }

    }
}
