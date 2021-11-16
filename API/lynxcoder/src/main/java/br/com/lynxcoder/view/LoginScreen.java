package br.com.lynxcoder.view;

import br.com.lynxcoder.DAO.LogDAO;
import br.com.lynxcoder.DAO.UsuarioDAO;
import br.com.lynxcoder.integration.slack.DAO.SlackDAO;
import br.com.lynxcoder.model.Usuario;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class LoginScreen extends JFrame {

    JLabel lblLogo;

    JLabel lblUser;
    JTextField jtfUserName;
    JLabel lblPass;
    JPasswordField jpfPassword;

    JButton btnLogin;

    private final String COLOR_BACKGROUND = "#43318f";

    public LoginScreen(){
        initJFrame();
        initComponents();

        add();
        this.setVisible(true);
    }

    private void initComponents() {
        lblLogo = new JLabel();
        lblLogo.setBounds(((this.getWidth()/2)-67),50,120,100);
        lblLogo.setOpaque(true);
        lblLogo.setBackground(new Color(0,0,0,0));
        ImageIcon background = new ImageIcon(getClass().getClassLoader().getResource("logo-type3.1.png"));
        background.setImage(background.getImage().getScaledInstance(lblLogo.getWidth(),100,Image.SCALE_SMOOTH));
        lblLogo.setIcon(background);

        lblUser = new JLabel();
        lblUser.setText("Usuário");
        lblUser.setBounds(41, (lblLogo.getY()+lblLogo.getHeight()+50), 100, 35);
        lblUser.setForeground(new Color(229, 229, 229));
        lblUser.setFont(new Font( "Century Gothic", Font.ROMAN_BASELINE , 19));

        jtfUserName = new JTextField();
        jtfUserName.setBounds(40, (lblUser.getY()+lblUser.getHeight()-3), 300, 35);
        jtfUserName.setOpaque(true);
        jtfUserName.setFont(new Font( "Century Gothic", Font.BOLD , 13));
        jtfUserName.setForeground(new Color(229, 229, 229));
        jtfUserName.setCaretColor(Color.WHITE);
        jtfUserName.setBackground(new Color(34, 5, 108));
        jtfUserName.setBorder(new LineBorder(new Color(34, 5, 108), 2));
        jtfUserName.setHorizontalAlignment(SwingConstants.CENTER);

        lblPass = new JLabel();
        lblPass.setText("Senha");
        lblPass.setBounds(41, (jtfUserName.getY()+jtfUserName.getHeight()+5), 100, 35);
        lblPass.setForeground(new Color(229, 229, 229));
        lblPass.setFont(new Font( "Century Gothic", Font.ROMAN_BASELINE , 19));

        jpfPassword = new JPasswordField();
        jpfPassword.setBounds(jtfUserName.getX(), (lblPass.getY()+lblPass.getHeight()-3), 300, 35);
        jpfPassword.setOpaque(true);
        jpfPassword.setFont(new Font( "Century Gothic", Font.BOLD , 13));
        jpfPassword.setForeground(new Color(229, 229, 229));
        jpfPassword.setCaretColor(Color.WHITE);
        jpfPassword.setBackground(new Color(34, 5, 108));
        jpfPassword.setBorder(new LineBorder(new Color(34, 5, 108), 2));
        jpfPassword.setHorizontalAlignment(SwingConstants.CENTER);

        btnLogin = new JButton();
        btnLogin.setText("Log-in");
        btnLogin.setBounds(jtfUserName.getX(), (jpfPassword.getY()+jpfPassword.getHeight()+20), 300, 35);
        btnLogin.setOpaque(true);
        btnLogin.setBackground(new Color(240, 240, 240));
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font( "Century Gothic", Font.BOLD , 13));
        btnLogin.setForeground(Color.decode(COLOR_BACKGROUND));
        btnLogin.setBorder(null);
        btnLogin.addMouseListener(logar());

    }

    public MouseListener logar(){

        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                UsuarioDAO userDAO = new UsuarioDAO();
                SlackDAO slackDAO = new SlackDAO();
                LogDAO logDAO = new LogDAO();

                Usuario user = userDAO.logar(jtfUserName.getText(), new String(jpfPassword.getPassword()));

                if( user != null ){
                    System.out.println("Logando...");

                    Dashboard dashboard = new Dashboard(user);

                    Date data = new Date();
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                    String dataFormatada = dateFormat.format(data);

                    logDAO.criarLog(dataFormatada);

                    slackDAO.welcomeMessage(user);

                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Usuário e/ou senha incorreto(s)!");
                    jtfUserName.setText("");
                    jpfPassword.setText("");
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnLogin.setBackground(new Color(59, 11, 131));
                btnLogin.setForeground(new Color(240, 240, 240));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btnLogin.setBackground(new Color(240, 240, 240));
                btnLogin.setForeground(new Color(59, 11, 131));
            }
        };

    }

    private void initJFrame() {
        this.setTitle("Monitore");
        this.setSize(400, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(Color.decode(COLOR_BACKGROUND));
        this.setFocusable(true);
    }

    public void add(){
        this.add(lblLogo);
        this.add(lblUser);
        this.add(jtfUserName);
        this.add(lblPass);
        this.add(jpfPassword);
        this.add(btnLogin);
    }

}
