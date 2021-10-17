package br.com.lynxcoder.view;

import br.com.lynxcoder.model.Usuario;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class Dashboard extends JFrame implements MouseListener {

    Looca looca = new Looca();
    List<Volume> listVolume = looca.getGrupoDeDiscos().getVolumes();

    Usuario user;

    private final String COLOR_BACKGROUND = "#e7e5f0";
    private final String COLOR_NAVBAR = "#43318f";

    private final String FONT = "Poppins";

    private final String COLOR_DARK_TITLE = "#43318f";
    private final String COLOR_DARK_TEXT = "#333";
    private final String COLOR_LIGHT_TEXT = "#f3f3f3";

    private int screenX;
    private int screenWidth;
    private int screenHeight;

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

    // Icons
    ImageIcon infoIcon;
    ImageIcon graficosIcon;
    ImageIcon processosIcon;

    ImageIcon infoUnselectedIcon;
    ImageIcon graficosUnselectedIcon;
    ImageIcon processosUnselectedIcon;

    // Info Screen
    JPanel pnlInfoScreen;

    JLabel lblNomeComputador;
    JLabel lblRamTotal;
    JLabel lblNomeProcessador;
    JLabel lblVolumes;

    // Charts Screen
    JPanel pnlChartsScreen;

    JProgressBar pgbRAM;
    JProgressBar pgbCPU;
    JProgressBar pgbDisco;

    // Processes Screen
    JPanel pnlProcessesScreen;

    public Dashboard(Usuario user) {
        initDashboard();
        initIcons();
        initNavbar();

        initInfoScreen();
        initChartsScreen();
        initProcessesScreen();

        initInfoGetter();

        add();
        setVisible(true);

        this.user = user;
    }

    public Dashboard() {
        initDashboard();
        initIcons();
        initNavbar();

        initInfoScreen();
        initChartsScreen();
        initProcessesScreen();

        initInfoGetter();

        add();
        setVisible(true);
    }

    private void initDashboard() {
        setTitle("Dashboard");
        setSize(1044, 700);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        this.getContentPane().setBackground(Color.decode(COLOR_BACKGROUND));
    }

    private void initIcons() {
        // Standard Icons
        infoIcon = new ImageIcon("src/assets/info-nav-icon.png");
        infoIcon.setImage(infoIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

        graficosIcon = new ImageIcon("src/assets/grafico-nav-icon.png");
        graficosIcon.setImage(graficosIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

        processosIcon = new ImageIcon("src/assets/processos-nav-icon.png");
        processosIcon.setImage(processosIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

        // Unselected Icons
        infoUnselectedIcon = new ImageIcon("src/assets/info-nav-unselected-icon.png");
        infoUnselectedIcon.setImage(infoUnselectedIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

        graficosUnselectedIcon = new ImageIcon("src/assets/grafico-nav-unselected-icon.png");
        graficosUnselectedIcon.setImage(graficosUnselectedIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

        processosUnselectedIcon = new ImageIcon("src/assets/processos-nav-unselected-icon.png");
        processosUnselectedIcon.setImage(processosUnselectedIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
    }

    private void initNavbar() {
        pnlNavbar = new JPanel();
        pnlNavbar.setBounds(0, 0, 200, 700);
        pnlNavbar.setBackground(Color.decode(COLOR_NAVBAR));
        pnlNavbar.setLayout(null);

        screenX = pnlNavbar.getX() + pnlNavbar.getWidth() + 50;
        screenWidth = 1044 - (screenX + 50);
        screenHeight = 600;

        lblNavBemVindo = new JLabel();
        lblNavBemVindo.setBounds(
                0, 25, pnlNavbar.getWidth(), 25
        );
        lblNavBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavBemVindo.setForeground(Color.decode(COLOR_LIGHT_TEXT));
        lblNavBemVindo.setFont(new Font(FONT, Font.PLAIN, 16));
        lblNavBemVindo.setText("Bem-Vindo(a)");

        lblNavUsuario = new JLabel();
        lblNavUsuario.setBounds(
                0, lblNavBemVindo.getY() + lblNavBemVindo.getHeight(),
                pnlNavbar.getWidth(), 25
        );
        lblNavUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavUsuario.setForeground(Color.decode(COLOR_LIGHT_TEXT));
        lblNavUsuario.setFont(new Font(FONT, Font.BOLD, 20));
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
        lblNavInfoIcon.setBackground(new Color(0, 0, 0, 0));
        lblNavInfoIcon.setIcon(infoIcon);
        lblNavInfoIcon.addMouseListener(this);

        lblNavInfo = new JLabel();
        lblNavInfo.setBounds(
                0, lblNavInfoIcon.getY() + lblNavInfoIcon.getHeight() + 5,
                pnlNavbar.getWidth(), 25
        );
        lblNavInfo.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavInfo.setForeground(Color.decode(COLOR_LIGHT_TEXT));
        lblNavInfo.setFont(new Font(FONT, Font.PLAIN, 16));
        lblNavInfo.setText("Info");

        lblNavGraficosIcon = new JLabel();
        lblNavGraficosIcon.setBounds(
                0, lblNavInfo.getY() + lblNavInfo.getHeight() + 50,
                pnlNavbar.getWidth(), 75
        );
        lblNavGraficosIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavGraficosIcon.setOpaque(true);
        lblNavGraficosIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblNavGraficosIcon.setBackground(new Color(0, 0, 0, 0));
        lblNavGraficosIcon.setIcon(graficosUnselectedIcon);
        lblNavGraficosIcon.addMouseListener(this);

        lblNavGraficos = new JLabel();
        lblNavGraficos.setBounds(
                0, lblNavGraficosIcon.getY() + lblNavGraficosIcon.getHeight() + 5,
                pnlNavbar.getWidth(), 25
        );
        lblNavGraficos.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavGraficos.setForeground(Color.decode(COLOR_LIGHT_TEXT));
        lblNavGraficos.setFont(new Font(FONT, Font.PLAIN, 16));
        lblNavGraficos.setText("Gráficos");

        lblNavProcessosIcon = new JLabel();
        lblNavProcessosIcon.setBounds(
                0, lblNavGraficos.getY() + lblNavGraficos.getHeight() + 50,
                pnlNavbar.getWidth(), 75
        );
        lblNavProcessosIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavProcessosIcon.setOpaque(true);
        lblNavProcessosIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblNavProcessosIcon.setBackground(new Color(0, 0, 0, 0));
        lblNavProcessosIcon.setIcon(processosUnselectedIcon);
        lblNavProcessosIcon.addMouseListener(this);

        lblNavProcessos = new JLabel();
        lblNavProcessos.setBounds(
                0, lblNavProcessosIcon.getY() + lblNavProcessosIcon.getHeight() + 5,
                pnlNavbar.getWidth(), 25
        );
        lblNavProcessos.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavProcessos.setForeground(Color.decode(COLOR_LIGHT_TEXT));
        lblNavProcessos.setFont(new Font(FONT, Font.PLAIN, 16));
        lblNavProcessos.setText("Processos");

    }

    private void initInfoScreen() {
        pnlInfoScreen = new JPanel();
        pnlInfoScreen.setBounds(
                screenX, 25, screenWidth, screenHeight
        );
        pnlInfoScreen.setBackground(Color.decode(COLOR_BACKGROUND));
        pnlInfoScreen.setLayout(null);

        lblNomeComputador = new JLabel();
        lblNomeComputador.setBounds(
                0, 0, 700, 25
        );
        lblNomeComputador.setForeground(Color.decode(COLOR_DARK_TITLE));
        lblNomeComputador.setFont(new Font(FONT, Font.BOLD, 20));
        lblNomeComputador.setText(String.format(
                "%s %s - Executando como %s",
                looca.getSistema().getFabricante(),
                looca.getSistema().getSistemaOperacional(),
                looca.getSistema().getPermissao() ? "admin" : "usuário padrão"
        ));

        lblRamTotal = new JLabel();
        lblRamTotal.setBounds(
                0, lblNomeComputador.getY() + lblNomeComputador.getHeight() + 5,
                700, 25
        );
        lblRamTotal.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblRamTotal.setFont(new Font(FONT, Font.PLAIN, 12));
        lblRamTotal.setText(String.format(
                "Total RAM: %s",
                FileUtils.byteCountToDisplaySize(looca.getMemoria().getTotal())
        ));

        lblNomeProcessador = new JLabel();
        lblNomeProcessador.setBounds(
                0, lblRamTotal.getY() + lblRamTotal.getHeight() + 5,
                700, 25
        );
        lblNomeProcessador.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblNomeProcessador.setFont(new Font(FONT, Font.PLAIN, 12));
        lblNomeProcessador.setText(String.format(
                "Processador: %s",
                looca.getProcessador().getNome()
        ));

        lblVolumes = new JLabel();
        lblVolumes.setBounds(
                0, lblNomeProcessador.getY() + lblNomeProcessador.getHeight() + 5,
                700, 25
        );
        lblVolumes.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblVolumes.setFont(new Font(FONT, Font.PLAIN, 12));

        for (Volume v : listVolume) {
            lblVolumes.setText(String.format(
                    "%sDisco %s: %s     ",
                    lblVolumes.getText(),
                    v.getPontoDeMontagem(),
                    FileUtils.byteCountToDisplaySize(v.getTotal())
            ));
        }
    }

    private void initChartsScreen() {
        pnlChartsScreen = new JPanel();
        pnlChartsScreen.setBounds(
                screenX, 25, screenWidth, screenHeight
        );
        pnlChartsScreen.setBackground(Color.decode(COLOR_BACKGROUND));
        pnlChartsScreen.setLayout(null);
        pnlChartsScreen.setVisible(false);

        pgbRAM = new JProgressBar();
        pgbRAM.setBounds(
                0, lblVolumes.getY() + lblVolumes.getHeight() + 5,
                700, 25
        );
        pgbRAM.setStringPainted(true);
        pgbRAM.setMaximum(100);

        pgbCPU = new JProgressBar();
        pgbCPU.setBounds(
                0, pgbRAM.getY() + pgbRAM.getHeight() + 5,
                700, 25
        );
        pgbCPU.setStringPainted(true);
        pgbCPU.setMaximum(100);

        pgbDisco = new JProgressBar();
        pgbDisco.setBounds(
                0, pgbCPU.getY() + pgbCPU.getHeight() + 5,
                700, 25
        );
        pgbDisco.setStringPainted(true);
        pgbDisco.setMaximum(100);
    }

    private void initProcessesScreen() {
        pnlProcessesScreen = new JPanel();
        pnlProcessesScreen.setBounds(
                screenX, 25, screenWidth, screenHeight
        );
        pnlProcessesScreen.setBackground(Color.decode(COLOR_BACKGROUND));
        pnlProcessesScreen.setLayout(null);
        pnlProcessesScreen.setVisible(false);
    }

    private void initInfoGetter() {

        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000);

                    Double usoRAM = (looca.getMemoria().getEmUso().doubleValue() / looca.getMemoria().getTotal().doubleValue()) * 100;
                    Double usoCPU = looca.getProcessador().getUso();

                    Double total = 0.0;
                    Double totalDisponivel = 0.0;

                    for (Volume v : listVolume) {
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
        }).start();
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

        // Info Screen
        add(pnlInfoScreen);

        pnlInfoScreen.add(lblNomeComputador);
        pnlInfoScreen.add(lblRamTotal);
        pnlInfoScreen.add(lblNomeProcessador);
        pnlInfoScreen.add(lblVolumes);

        // Charts Screen
        add(pnlChartsScreen);

        pnlChartsScreen.add(pgbRAM);
        pnlChartsScreen.add(pgbCPU);
        pnlChartsScreen.add(pgbDisco);

        // Processes Screen
        add(pnlProcessesScreen);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();

        if (lblNavInfoIcon.equals(source) && !pnlInfoScreen.isVisible()) {
            pnlInfoScreen.setVisible(true);
            pnlChartsScreen.setVisible(false);
            pnlProcessesScreen.setVisible(false);

            lblNavInfoIcon.setIcon(infoIcon);
            lblNavGraficosIcon.setIcon(graficosUnselectedIcon);
            lblNavProcessosIcon.setIcon(processosUnselectedIcon);
        } else if (lblNavGraficosIcon.equals(source) && !pnlChartsScreen.isVisible()) {
            pnlInfoScreen.setVisible(false);
            pnlChartsScreen.setVisible(true);
            pnlProcessesScreen.setVisible(false);

            lblNavInfoIcon.setIcon(infoUnselectedIcon);
            lblNavGraficosIcon.setIcon(graficosIcon);
            lblNavProcessosIcon.setIcon(processosUnselectedIcon);
        } else if (lblNavProcessosIcon.equals(source) && !pnlProcessesScreen.isVisible()) {
            pnlInfoScreen.setVisible(false);
            pnlChartsScreen.setVisible(false);
            pnlProcessesScreen.setVisible(true);

            lblNavInfoIcon.setIcon(infoUnselectedIcon);
            lblNavGraficosIcon.setIcon(graficosUnselectedIcon);
            lblNavProcessosIcon.setIcon(processosIcon);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
