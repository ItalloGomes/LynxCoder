package br.com.lynxcoder.view;

import br.com.lynxcoder.model.Usuario;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Dashboard extends JFrame {

    Looca looca = new Looca();
    List<Volume> listVolume = looca.getGrupoDeDiscos().getVolumes();

    Usuario user;

    private final String COLOR_BACKGROUND = "#e7e5f0";
    private final String COLOR_NAVBAR = "#43318f";

    private final String FONT = "Poppins";

    private final String COLOR_DARK_TITLE = "#43318f";
    private final String COLOR_DARK_TEXT = "#333";
    private final String COLOR_LIGHT_TEXT = "#f3f3f3";

    private int mainScreenX;
    private Double iconAlpha = 0.5;

    // Navbar
    JPanel pnlNavbar;

    JLabel lblNavBemVindo;
    JLabel lblNavUsuario;

    JLabel lblNavInfoIcon;
    JLabel lblNavInfo;

    JLabel lblNavGraficosIcon;
    JLabel lblNavGraficos;

    JLabel lblNavProcessosIcon;
    JLabel lblNavProcessos;

    // Computer labels
    JLabel lblNomeComputador;
    JLabel lblRamTotal;
    JLabel lblNomeProcessador;
    JLabel lblVolumes;

    // Charts
    JProgressBar pgbRAM;
    JProgressBar pgbCPU;
    JProgressBar pgbDisco;

    public Dashboard(Usuario user) {
        initDashboard();
        initNavbar();
        initLabels();
        initCharts();
        initInfoGetter();

        add();
        setVisible(true);

        this.user = user;
    }

    public Dashboard() {
        initDashboard();
        initNavbar();
        initLabels();
        initCharts();
        initInfoGetter();

        add();
        setVisible(true);
    }

    private void initDashboard() {
        setTitle("Dashboard");
        setSize(1044,700);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        this.getContentPane().setBackground(Color.decode(COLOR_BACKGROUND));
    }

    private void initNavbar() {
        pnlNavbar = new JPanel();
        pnlNavbar.setBounds(0, 0, 200, 700);
        pnlNavbar.setBackground(Color.decode(COLOR_NAVBAR));
        pnlNavbar.setLayout(null);
        mainScreenX = pnlNavbar.getX() + pnlNavbar.getWidth() + 50;

        lblNavBemVindo = new JLabel();
        lblNavBemVindo.setBounds(
                0, 25, pnlNavbar.getWidth(), 25
        );
        lblNavBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavBemVindo.setForeground(Color.decode(COLOR_LIGHT_TEXT));
        lblNavBemVindo.setFont(new Font(FONT, Font.PLAIN,16));
        lblNavBemVindo.setText("Bem-Vindo(a)");

        lblNavUsuario = new JLabel();
        lblNavUsuario.setBounds(
                0, lblNavBemVindo.getY() + lblNavBemVindo.getHeight(),
                pnlNavbar.getWidth(), 25
        );
        lblNavUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavUsuario.setForeground(Color.decode(COLOR_LIGHT_TEXT));
        lblNavUsuario.setFont(new Font(FONT, Font.BOLD,20));
        lblNavUsuario.setText("DANIEL");
//        lblNavbarUsuario.setText(user.getNome());

        lblNavInfoIcon = new JLabel();
        lblNavInfoIcon.setBounds(
                0, lblNavUsuario.getY() + lblNavUsuario.getHeight() + 50,
                pnlNavbar.getWidth(), 75
        );
        lblNavInfoIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavInfoIcon.setOpaque(true);
        lblNavInfoIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblNavInfoIcon.setBackground(new Color(0,0,0,0));
        ImageIcon infoIcon = new ImageIcon("src/assets/info-nav-icon.png");
        infoIcon.setImage(infoIcon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH));
        lblNavInfoIcon.setIcon(infoIcon);

        lblNavInfo = new JLabel();
        lblNavInfo.setBounds(
                0, lblNavInfoIcon.getY() + lblNavInfoIcon.getHeight() + 5,
                pnlNavbar.getWidth(), 25
        );
        lblNavInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavInfo.setForeground(Color.decode(COLOR_LIGHT_TEXT));
        lblNavInfo.setFont(new Font(FONT, Font.PLAIN,16));
        lblNavInfo.setText("Info");

        lblNavGraficosIcon = new JLabel();
        lblNavGraficosIcon.setBounds(
                0, lblNavInfo.getY() + lblNavInfo.getHeight() + 50,
                pnlNavbar.getWidth(), 75
        );
        lblNavGraficosIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavGraficosIcon.setOpaque(true);
        lblNavGraficosIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblNavGraficosIcon.setBackground(new Color(0,0,0,0));
        ImageIcon graficosIcon = new ImageIcon("src/assets/grafico-nav-unselected-icon.png");
        graficosIcon.setImage(graficosIcon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH));
        lblNavGraficosIcon.setIcon(graficosIcon);

        lblNavGraficos = new JLabel();
        lblNavGraficos.setBounds(
                0, lblNavGraficosIcon.getY() + lblNavGraficosIcon.getHeight() + 5,
                pnlNavbar.getWidth(), 25
        );
        lblNavGraficos.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavGraficos.setForeground(Color.decode(COLOR_LIGHT_TEXT));
        lblNavGraficos.setFont(new Font(FONT, Font.PLAIN,16));
        lblNavGraficos.setText("Gráficos");

        lblNavProcessosIcon = new JLabel();
        lblNavProcessosIcon.setBounds(
                0, lblNavGraficos.getY() + lblNavGraficos.getHeight() + 50,
                pnlNavbar.getWidth(), 75
        );
        lblNavProcessosIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavProcessosIcon.setOpaque(true);
        lblNavProcessosIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblNavProcessosIcon.setBackground(new Color(0,0,0,0));
        ImageIcon processosIcon = new ImageIcon("src/assets/processos-nav-unselected-icon.png");
        processosIcon.setImage(processosIcon.getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH));
        lblNavProcessosIcon.setIcon(processosIcon);

        lblNavProcessos = new JLabel();
        lblNavProcessos.setBounds(
                0, lblNavProcessosIcon.getY() + lblNavProcessosIcon.getHeight() + 5,
                pnlNavbar.getWidth(), 25
        );
        lblNavProcessos.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavProcessos.setForeground(Color.decode(COLOR_LIGHT_TEXT));
        lblNavProcessos.setFont(new Font(FONT, Font.PLAIN,16));
        lblNavProcessos.setText("Processos");

    }

    private void initLabels() {
        lblNomeComputador = new JLabel();
        lblNomeComputador.setBounds(
                mainScreenX,
                25, 700, 25
        );
        lblNomeComputador.setForeground(Color.decode(COLOR_DARK_TITLE));
        lblNomeComputador.setFont(new Font(FONT, Font.BOLD,20));
        lblNomeComputador.setText(String.format(
            "%s %s - Executando como %s",
            looca.getSistema().getFabricante(),
            looca.getSistema().getSistemaOperacional(),
            looca.getSistema().getPermissao() ? "admin" : "usuário padrão"
        ));

        lblRamTotal = new JLabel();
        lblRamTotal.setBounds(
                mainScreenX,
                lblNomeComputador.getY() + lblNomeComputador.getHeight() + 5,
                700, 25
        );
        lblRamTotal.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblRamTotal.setFont(new Font(FONT, Font.PLAIN,12));
        lblRamTotal.setText(String.format(
            "Total RAM: %s",
                FileUtils.byteCountToDisplaySize(looca.getMemoria().getTotal())
        ));

        lblNomeProcessador = new JLabel();
        lblNomeProcessador.setBounds(
                mainScreenX,
                lblRamTotal.getY() + lblRamTotal.getHeight() + 5,
                700, 25
        );
        lblNomeProcessador.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblNomeProcessador.setFont(new Font(FONT, Font.PLAIN,12));
        lblNomeProcessador.setText(String.format(
            "Processador: %s",
            looca.getProcessador().getNome()
        ));

        lblVolumes = new JLabel();
        lblVolumes.setBounds(
                mainScreenX,
                lblNomeProcessador.getY() + lblNomeProcessador.getHeight() + 5,
                700, 25
        );
        lblVolumes.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblVolumes.setFont(new Font(FONT, Font.PLAIN,12));

        for (Volume v: listVolume) {
            lblVolumes.setText(String.format(
                "%sDisco %s: %s     ",
                    lblVolumes.getText(),
                    v.getPontoDeMontagem(),
                    FileUtils.byteCountToDisplaySize(v.getTotal())
            ));
        }
    }

    private void initCharts() {
        pgbRAM = new JProgressBar();
        pgbRAM.setBounds(
                mainScreenX,
                lblVolumes.getY() + lblVolumes.getHeight() + 5,
                700, 25
        );
        pgbRAM.setStringPainted(true);
        pgbRAM.setMaximum(100);

        pgbCPU = new JProgressBar();
        pgbCPU.setBounds(
                mainScreenX,
                pgbRAM.getY() + pgbRAM.getHeight() + 5,
                700, 25
        );
        pgbCPU.setStringPainted(true);
        pgbCPU.setMaximum(100);

        pgbDisco = new JProgressBar();
        pgbDisco.setBounds(
                mainScreenX,
                pgbCPU.getY() + pgbCPU.getHeight() + 5,
                700, 25
        );
        pgbDisco.setStringPainted(true);
        pgbDisco.setMaximum(100);
    }

    private void initInfoGetter() {

        new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);

                        Double usoRAM = (looca.getMemoria().getEmUso().doubleValue() / looca.getMemoria().getTotal().doubleValue()) * 100;
                        Double usoCPU = looca.getProcessador().getUso();

                        Double total = 0.0;
                        Double totalDisponivel = 0.0;

                        for (Volume v: listVolume) {
                            total += v.getTotal();
                            totalDisponivel += v.getDisponivel();
                        }

                        Double usoDisco = ((total - totalDisponivel) / total) * 100;

                        populateCharts(usoRAM, usoCPU, usoDisco);
                        insertInDatabase(usoRAM, usoCPU, usoDisco);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void populateCharts(Double usoRAM, Double usoCPU, Double usoDisco) {
        pgbRAM.setValue(usoRAM.intValue());
        pgbCPU.setValue(usoCPU.intValue());
        pgbDisco.setValue(usoDisco.intValue());

        pgbRAM.setString(usoRAM.intValue() + " %");
        pgbCPU.setString(usoCPU.intValue() + " %");
        pgbDisco.setString(usoDisco.intValue() + " %");
    }

    private void insertInDatabase(Double usoRAM, Double usoCPU, Double usoDisco) {
       //
    }

    public void add() {
        // Navbar
        add(pnlNavbar);

        pnlNavbar.add(lblNavBemVindo);
        pnlNavbar.add(lblNavUsuario);

        pnlNavbar.add(lblNavInfoIcon);
        pnlNavbar.add(lblNavInfo);

        pnlNavbar.add(lblNavGraficosIcon);
        pnlNavbar.add(lblNavGraficos);

        pnlNavbar.add(lblNavProcessosIcon);
        pnlNavbar.add(lblNavProcessos);

        // Computer labels
        add(lblNomeComputador);
        add(lblRamTotal);
        add(lblNomeProcessador);
        add(lblVolumes);

        // Charts
        add(pgbRAM);
        add(pgbCPU);
        add(pgbDisco);
    }
}
