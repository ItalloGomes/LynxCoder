package br.com.lynxcoder.view;

import br.com.lynxcoder.DAO.UsuarioDAO;
import br.com.lynxcoder.model.Usuario;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class LoginScreen extends JFrame {

    JLabel lblLogo;

    JLabel lblUser;
    JTextField jtfUserName;
    JLabel lblPass;
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
        lblLogo.setBounds(((this.getWidth()/2)-83),50,150,100);
        lblLogo.setOpaque(true);
        lblLogo.setBackground(new Color(0,0,0,0));
        ImageIcon background = new ImageIcon("src/assets/logo-type3.png");
        background.setImage(background.getImage().getScaledInstance(lblLogo.getWidth(),100,Image.SCALE_SMOOTH));
        lblLogo.setIcon(background);

        lblUser = new JLabel();
        lblUser.setText("Usuário");
        lblUser.setBounds(160, (lblLogo.getY()+lblLogo.getHeight()+60), 100, 35);
        lblUser.setForeground(new Color(194, 194, 194));
        lblUser.setFont(new Font( "Arial", Font.ROMAN_BASELINE , 19));

        jtfUserName = new JTextField();
        jtfUserName.setBounds(40, (lblUser.getY()+lblUser.getHeight()), 300, 35);
        jtfUserName.setOpaque(true);
        jtfUserName.setFont(new Font( "Arial", Font.BOLD , 13));
        jtfUserName.setForeground(new Color(217, 217, 217));
        jtfUserName.setBackground(new Color(41, 72, 131));
        jtfUserName.setBorder(new LineBorder(new Color(22, 95, 192), 3));
        jtfUserName.setHorizontalAlignment(SwingConstants.CENTER);

        lblPass = new JLabel();
        lblPass.setText("Senha");
        lblPass.setBounds(164, (jtfUserName.getY()+jtfUserName.getHeight()+5), 100, 35);
        lblPass.setForeground(new Color(194, 194, 194));
        lblPass.setFont(new Font( "Arial", Font.ROMAN_BASELINE , 19));

        jpfPassword = new JPasswordField();
        jpfPassword.setBounds(jtfUserName.getX(), (lblPass.getY()+lblPass.getHeight()), 300, 35);
        jpfPassword.setOpaque(true);
        jpfPassword.setFont(new Font( "Arial", Font.BOLD , 13));
        jpfPassword.setForeground(new Color(217, 217, 217));
        jpfPassword.setBackground(new Color(41, 72, 131));
        jpfPassword.setBorder(new LineBorder(new Color(22, 95, 192), 3));
        jpfPassword.setHorizontalAlignment(SwingConstants.CENTER);

        btnLogin = new JButton();
        btnLogin.setText("Log-in");
        btnLogin.setBounds(jtfUserName.getX(), (jpfPassword.getY()+jpfPassword.getHeight()+20), 100, 30);
        btnLogin.setOpaque(true);
        btnLogin.setBackground(new Color(22, 95, 192));
        btnLogin.setFocusPainted(false);
        btnLogin.setFont(new Font( "Arial", Font.BOLD , 13));
        btnLogin.setForeground(new Color(194, 194, 194));
        btnLogin.setBorder(null);
        btnLogin.addMouseListener(logar());

    }

    public MouseListener logar(){

        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                UsuarioDAO userDAO = new UsuarioDAO();

                System.out.println(jtfUserName.getText() +" "+ new String(jpfPassword.getPassword()));

                Usuario user = userDAO.logar(jtfUserName.getText(), new String(jpfPassword.getPassword()));

                if( user != null ){
                    System.out.println("Cebesta, ta logado guri!");
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Este usuário não está cadastrado");
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
                btnLogin.setBackground(new Color(8, 104, 232));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                btnLogin.setBackground(new Color(22, 95, 192));
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
        this.getContentPane().setBackground(new Color(41, 72, 131));
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
