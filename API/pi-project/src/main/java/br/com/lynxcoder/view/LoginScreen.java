package br.com.lynxcoder.view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginScreen extends JFrame {

    JLabel lblLogo;

    JTextField jtfUserName;
    JPasswordField jpfPassword;

    JButton btnLogin;

    public LoginScreen(){
        initJFrame();
        initComponents();

        add();
        this.setVisible(true);
    }

    private void initComponents() {
        lblLogo = new JLabel();
        lblLogo.setBounds(((this.getWidth()/2)-100),50,200,100);
        lblLogo.setOpaque(true);
        lblLogo.setBackground(new Color(0,0,0,0));
        ImageIcon background = new ImageIcon("src/assets/logo-type5.png");
        background.setImage(background.getImage().getScaledInstance(200,100,Image.SCALE_SMOOTH));
        lblLogo.setIcon(background);

        jtfUserName = new JTextField();
        jtfUserName.setText("Username");
        jtfUserName.setMargin(new Insets(20,20,0,0));
        jtfUserName.setBounds(50, (lblLogo.getY()+lblLogo.getHeight()+80), 300, 35);
        jtfUserName.setOpaque(true);
        jtfUserName.setFont(new Font( "Arial", Font.BOLD , 13));
        jtfUserName.setForeground(new Color(22, 95, 192));
        jtfUserName.setBackground(new Color(38, 24, 71));
        jtfUserName.setBorder(new LineBorder(new Color(22, 95, 192), 3));


        jpfPassword = new JPasswordField();
        jpfPassword.setText("Password");
        jpfPassword.setBounds(jtfUserName.getX(), (jtfUserName.getY()+jtfUserName.getHeight()+20), 300, 35);
        jpfPassword.setOpaque(true);
        jpfPassword.setFont(new Font( "Arial", Font.BOLD , 13));
        jpfPassword.setForeground(new Color(22, 95, 192));
        jpfPassword.setBackground(new Color(38, 24, 71));
        jpfPassword.setBorder(new LineBorder(new Color(22, 95, 192), 3));


        btnLogin = new JButton();
        btnLogin.setText("Log-in");
        btnLogin.setBounds(jtfUserName.getX(), (jpfPassword.getY()+jpfPassword.getHeight()+20), 100, 30);
        btnLogin.setOpaque(true);
        btnLogin.setBackground(Color.MAGENTA);
        btnLogin.setFocusPainted(false);
        btnLogin.setOpaque(true);
        btnLogin.setFont(new Font( "Arial", Font.BOLD , 13));
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setBackground(new Color(22, 95, 192));
        btnLogin.setBorder(null);
    }

    private void initJFrame() {
        this.setTitle("Monitore");
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(38, 24, 71));
    }

    public void add(){
        this.add(lblLogo);
        this.add(jtfUserName);
        this.add(jpfPassword);
        this.add(btnLogin);
    }

}
