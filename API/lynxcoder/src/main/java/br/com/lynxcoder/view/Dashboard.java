package br.com.lynxcoder.view;

import br.com.lynxcoder.DAO.LeituraDAO;
import br.com.lynxcoder.DAO.LogDAO;
import br.com.lynxcoder.DAO.MaquinaDAO;
import br.com.lynxcoder.DAO.ProcessosDAO;
import br.com.lynxcoder.integration.slack.DAO.SlackDAO;
import br.com.lynxcoder.model.Leitura;
import br.com.lynxcoder.model.Maquina;
import br.com.lynxcoder.model.Usuario;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.processos.Processo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;
import java.util.Objects;

public class Dashboard extends JFrame implements MouseListener {

    Looca looca = new Looca();
    SlackDAO slackDAO = new SlackDAO();
    private final int hardwareThreadSleep = 5_000;
    private final int processThreadSleep = 15_000;

    List<Volume> listVolume = looca.getGrupoDeDiscos().getVolumes();

    MaquinaDAO maqDao;
    LeituraDAO leituraDao;
    ProcessosDAO processDao;

    Usuario user;
    LogDAO logDAO;
    Maquina maquinaUser;

    private final String COLOR_BACKGROUND = "#e7e5f0";
    private final String COLOR_NAVBAR = "#43318f";

    private final String FONT = "Poppins";

    private final String COLOR_DARK_TITLE = "#43318f";
    private final String COLOR_DARK_TEXT = "#333";
    private final String COLOR_LIGHT_TEXT = "#f3f3f3";

    private int screenX;
    private int screenWidth;
    private int screenHeight;

    private final int pnlX = 25;
    private final int hardwarePnlWidth = 350;
    private final int hardwarePnlHeight = 175;

    // Navbar
    JPanel pnlNavbar;

    JLabel lblNavUsuario;
    JLabel lblNavHardwareIcon;
    JLabel lblNavHardware;
    JLabel lblNavProcessosIcon;
    JLabel lblNavProcessos;

    // Icons
    ImageIcon hardwareIcon;
    ImageIcon processosIcon;

    ImageIcon hardwareUnselectedIcon;
    ImageIcon processosUnselectedIcon;

    // Hardware Screen
    JPanel pnlHardwareScreen;

    JPanel pnlSistema;
    JLabel lblSistema;
    JLabel lblComputador;
    JLabel lblPermissao;

    JPanel pnlVolumes;
    JLabel lblVolumes;
    JLabel lblVolumesTotal;
    JLabel lblVolumesEmUso;
    JProgressBar pgbVolumes;

    JPanel pnlRAM;
    JLabel lblRAM;
    JLabel lblRAMTotal;
    JLabel lblRAMEmUso;
    JProgressBar pgbRAM;

    JPanel pnlCPU;
    JLabel lblCPU;
    JLabel lblNomeCPU;
    JProgressBar pgbCPU;

    // Processos Screen
    JPanel pnlProcessosScreen;
    String orderBy;

    JPanel pnlProcessos;
    JLabel lblProcessos;

    JLabel lblOrderBy;
    JRadioButton rbtOrderByCPU;
    JLabel lblOrderByCPU;
    JRadioButton rbtOrderByRAM;
    JLabel lblOrderByRAM;

    JScrollPane spnListaProcessos;

    public Dashboard(Usuario user, LogDAO logDAO) {

        this.user = user;
        this.logDAO = logDAO;

        initDashboard();
        initIcons();
        initNavbar();

        initHardwareScreen();
        initProcessesScreen();

        maqDao = new MaquinaDAO();

        if(!maqDao.hasMaquina(user)) {

            int response = JOptionPane.showConfirmDialog(null, "Essa é sua máquina principal?");
            System.out.println(response);

            switch (response) {
                case 0:
                    Maquina maquina = new Maquina();

                    maquina.setTipoCPU(looca.getProcessador().getNome());
                    maquina.setTotalMemoria(byteCountConvert(looca.getMemoria().getTotal()));

                    long totalDisco = 0;
                    for (Volume v : listVolume) {
                        totalDisco += v.getTotal();
                    }
                    maquina.setTotalDisco(byteCountConvert(totalDisco));
                    maquina.setSistemaOperacional(
                        looca.getSistema().getFabricante() + " " +
                        looca.getSistema().getSistemaOperacional() + " "+
                        looca.getSistema().getArquitetura()
                    );
                    maquina.setUsuario(user);

                    maqDao.adicionarMaquina(user, maquina, logDAO);

                    this.maquinaUser = maqDao.findMaquina(user);

                    initMonitoradorDeHardware();
                    initMonitoradorDeProcessos();

                    break;
                case 2:
                    dispose();
                    break;
                default:
                    break;
            }

        } else {

            this.maquinaUser = maqDao.findMaquina(user);

            initMonitoradorDeHardware();
            initMonitoradorDeProcessos();

        }

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
        hardwareIcon = new ImageIcon(getClass().getClassLoader().getResource("hardware-nav-icon.png"));
        hardwareIcon.setImage(hardwareIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

        processosIcon = new ImageIcon(getClass().getClassLoader().getResource("processos-nav-icon.png"));
        processosIcon.setImage(processosIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

        // Unselected Icons
        hardwareUnselectedIcon = new ImageIcon(getClass().getClassLoader().getResource("hardware-nav-unselected-icon.png"));
        hardwareUnselectedIcon.setImage(hardwareUnselectedIcon.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));

        processosUnselectedIcon = new ImageIcon(getClass().getClassLoader().getResource("processos-nav-unselected-icon.png"));
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

        lblNavUsuario = new JLabel();
        lblNavUsuario.setBounds(
                0, 25,
                pnlNavbar.getWidth(), 25
        );
        lblNavUsuario.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavUsuario.setForeground(Color.decode(COLOR_LIGHT_TEXT));
        lblNavUsuario.setFont(new Font(FONT, Font.BOLD, 20));
        lblNavUsuario.setText(user.getNome());

        lblNavHardwareIcon = new JLabel();
        lblNavHardwareIcon.setBounds(
                0, lblNavUsuario.getY() + lblNavUsuario.getHeight() + 50,
                pnlNavbar.getWidth(), 75
        );
        lblNavHardwareIcon.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavHardwareIcon.setOpaque(true);
        lblNavHardwareIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblNavHardwareIcon.setBackground(new Color(0, 0, 0, 0));
        lblNavHardwareIcon.setIcon(hardwareIcon);
        lblNavHardwareIcon.addMouseListener(this);

        lblNavHardware = new JLabel();
        lblNavHardware.setBounds(
                0, lblNavHardwareIcon.getY() + lblNavHardwareIcon.getHeight() + 5,
                pnlNavbar.getWidth(), 25
        );
        lblNavHardware.setHorizontalAlignment(SwingConstants.CENTER);
        lblNavHardware.setForeground(Color.decode(COLOR_LIGHT_TEXT));
        lblNavHardware.setFont(new Font(FONT, Font.PLAIN, 16));
        lblNavHardware.setText("Hardware & Sistema");

        lblNavProcessosIcon = new JLabel();
        lblNavProcessosIcon.setBounds(
                0, lblNavHardware.getY() + lblNavHardware.getHeight() + 50,
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
        lblNavProcessos.setForeground(newColorWithAlpha(Color.decode(COLOR_LIGHT_TEXT), 150));
        lblNavProcessos.setFont(new Font(FONT, Font.PLAIN, 16));
        lblNavProcessos.setText("Processos");

        this.getContentPane().setBackground(new Color(38, 24, 71));

    }

    private void initHardwareScreen() {
        pnlHardwareScreen = new JPanel();
        pnlHardwareScreen.setBounds(
                screenX, 25, screenWidth, screenHeight
        );
        pnlHardwareScreen.setBackground(Color.decode(COLOR_BACKGROUND));
        pnlHardwareScreen.setLayout(null);

        // Sistema
        pnlSistema = new JPanel();
        pnlSistema.setBounds(
                25, 25,
                hardwarePnlWidth - 75, hardwarePnlHeight
        );
        pnlSistema.setBackground(Color.WHITE);
        pnlSistema.setLayout(null);

        lblSistema = new JLabel();
        lblSistema.setBounds(
                pnlX, 25, hardwarePnlWidth - 100, 25
        );
        lblSistema.setForeground(Color.decode(COLOR_DARK_TITLE));
        lblSistema.setFont(new Font(FONT, Font.BOLD, 20));
        lblSistema.setText("Sistema");

        lblComputador = new JLabel();
        lblComputador.setBounds(
                pnlX, lblSistema.getY() + lblSistema.getHeight() + 10,
                hardwarePnlWidth - 100, 25
        );
        lblComputador.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblComputador.setFont(new Font(FONT, Font.PLAIN, 12));
        lblComputador.setText(String.format(
                "Nome: %s %s",
                looca.getSistema().getFabricante(),
                looca.getSistema().getSistemaOperacional()
        ));

        lblPermissao = new JLabel();
        lblPermissao.setBounds(
                pnlX, lblComputador.getY() + lblComputador.getHeight() + 5,
                hardwarePnlWidth - 100, 25
        );
        lblPermissao.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblPermissao.setFont(new Font(FONT, Font.PLAIN, 12));
        lblPermissao.setText(String.format(
                "Permissão: %s",
                looca.getSistema().getPermissao() ? "Admin" : "Usuário padrão"
        ));

        // Volumes
        pnlVolumes = new JPanel();
        pnlVolumes.setBounds(
                pnlSistema.getX() + pnlSistema.getWidth() + 25,
                pnlSistema.getY(), hardwarePnlWidth + 50, hardwarePnlHeight
        );
        pnlVolumes.setBackground(Color.WHITE);
        pnlVolumes.setLayout(null);

        lblVolumes = new JLabel();
        lblVolumes.setBounds(
                pnlX, 25, hardwarePnlWidth + 100, 25
        );
        lblVolumes.setForeground(Color.decode(COLOR_DARK_TITLE));
        lblVolumes.setFont(new Font(FONT, Font.BOLD, 20));
        lblVolumes.setText("Volumes");

        lblVolumesTotal = new JLabel();
        lblVolumesTotal.setBounds(
                pnlX, lblVolumes.getY() + lblVolumes.getHeight() + 10,
                hardwarePnlWidth + 100, 25
        );
        lblVolumesTotal.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblVolumesTotal.setFont(new Font(FONT, Font.PLAIN, 12));
        lblVolumesTotal.setText("Total: ");

        for (Volume v : listVolume) {
            lblVolumesTotal.setText(String.format(
                    "%s %s: %s     ",
                    lblVolumesTotal.getText(),
                    v.getPontoDeMontagem(),
                    byteCountConvert(v.getTotal())
            ));
        }

        lblVolumesEmUso = new JLabel();
        lblVolumesEmUso.setBounds(
                pnlX, lblVolumesTotal.getY() + lblVolumesTotal.getHeight() + 5,
                hardwarePnlWidth + 100, 25
        );
        lblVolumesEmUso.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblVolumesEmUso.setFont(new Font(FONT, Font.PLAIN, 12));
        lblVolumesEmUso.setText("Em uso: ");

        pgbVolumes = new JProgressBar();
        pgbVolumes.setBounds(
                pnlX, lblVolumesEmUso.getY() + lblVolumesEmUso.getHeight() + 5,
                350, 25
        );
        pgbVolumes.setStringPainted(true);
        pgbVolumes.setMaximum(100);

        // CPU
        pnlCPU = new JPanel();
        pnlCPU.setBounds(
                25, pnlSistema.getY() + pnlSistema.getHeight() + 25,
                hardwarePnlWidth, hardwarePnlHeight
        );
        pnlCPU.setBackground(Color.WHITE);
        pnlCPU.setLayout(null);

        lblCPU = new JLabel();
        lblCPU.setBounds(
                pnlX, 25, hardwarePnlWidth, 25
        );
        lblCPU.setForeground(Color.decode(COLOR_DARK_TITLE));
        lblCPU.setFont(new Font(FONT, Font.BOLD, 20));
        lblCPU.setText("Processador");

        lblNomeCPU = new JLabel();
        lblNomeCPU.setBounds(
                pnlX, lblCPU.getY() + lblCPU.getHeight() + 10,
                300, 25
        );
        lblNomeCPU.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblNomeCPU.setFont(new Font(FONT, Font.PLAIN, 12));
        lblNomeCPU.setText(String.format(
                "Nome: %s",
                looca.getProcessador().getNome()
        ));

        pgbCPU = new JProgressBar();
        pgbCPU.setBounds(
                pnlX, lblNomeCPU.getY() + lblNomeCPU.getHeight() + 5,
                300, 25
        );
        pgbCPU.setStringPainted(true);
        pgbCPU.setMaximum(100);

        // RAM
        pnlRAM = new JPanel();
        pnlRAM.setBounds(
                pnlCPU.getX() + pnlCPU.getWidth() + 25,
                pnlCPU.getY(), hardwarePnlWidth - 25, hardwarePnlHeight
        );
        pnlRAM.setBackground(Color.WHITE);
        pnlRAM.setLayout(null);

        lblRAM = new JLabel();
        lblRAM.setBounds(
                pnlX, 25, hardwarePnlWidth, 25
        );
        lblRAM.setForeground(Color.decode(COLOR_DARK_TITLE));
        lblRAM.setFont(new Font(FONT, Font.BOLD, 20));
        lblRAM.setText("Memória RAM");

        lblRAMTotal = new JLabel();
        lblRAMTotal.setBounds(
                pnlX, lblRAM.getY() + lblRAM.getHeight() + 10,
                hardwarePnlWidth, 25
        );
        lblRAMTotal.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblRAMTotal.setFont(new Font(FONT, Font.PLAIN, 12));
        lblRAMTotal.setText(String.format(
                "Total: %s",
                byteCountConvert(looca.getMemoria().getTotal())
        ));

        lblRAMEmUso = new JLabel();
        lblRAMEmUso.setBounds(
                pnlX, lblRAMTotal.getY() + lblRAMTotal.getHeight() + 5,
                hardwarePnlWidth, 25
        );
        lblRAMEmUso.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblRAMEmUso.setFont(new Font(FONT, Font.PLAIN, 12));
        lblRAMEmUso.setText("Em uso: ");

        pgbRAM = new JProgressBar();
        pgbRAM.setBounds(
                pnlX, lblRAMEmUso.getY() + lblRAMEmUso.getHeight() + 5,
                275, 25
        );
        pgbRAM.setStringPainted(true);
        pgbRAM.setMaximum(100);

    }

    private void initProcessesScreen() {
        pnlProcessosScreen = new JPanel();
        pnlProcessosScreen.setBounds(
                screenX, 25, screenWidth, screenHeight
        );
        pnlProcessosScreen.setBackground(Color.decode(COLOR_BACKGROUND));
        pnlProcessosScreen.setLayout(null);
        pnlProcessosScreen.setVisible(false);

        pnlProcessos = new JPanel();
        pnlProcessos.setBounds(
                0, 25,
                screenWidth, 625
        );
        pnlProcessos.setBackground(Color.WHITE);
        pnlProcessos.setLayout(null);

        lblProcessos = new JLabel();
        lblProcessos.setBounds(
                pnlX, 25, 200, 25
        );
        lblProcessos.setForeground(Color.decode(COLOR_DARK_TITLE));
        lblProcessos.setFont(new Font(FONT, Font.BOLD, 20));
        lblProcessos.setText("Processos");

        lblOrderBy = new JLabel();
        lblOrderBy.setBounds(
                lblProcessos.getX() + lblProcessos.getWidth() + 25,
                25, 75, 25
        );
        lblOrderBy.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblOrderBy.setFont(new Font(FONT, Font.BOLD, 12));
        lblOrderBy.setText("Top 10 por: ");

        rbtOrderByRAM = new JRadioButton();
        rbtOrderByRAM.setBackground(Color.WHITE);
        rbtOrderByRAM.addMouseListener(this);
        rbtOrderByRAM.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rbtOrderByRAM.setBounds(
                lblOrderBy.getX() + lblOrderBy.getWidth() + 10,
                25, 25, 25
        );
        rbtOrderByRAM.setSelected(true);
        orderBy = "RAM";

        lblOrderByRAM = new JLabel();
        lblOrderByRAM.setBounds(
                rbtOrderByRAM.getX() + rbtOrderByRAM.getWidth(),
                25, 75, 25
        );
        lblOrderByRAM.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblOrderByRAM.setFont(new Font(FONT, Font.PLAIN, 12));
        lblOrderByRAM.setText("Uso RAM");

        rbtOrderByCPU = new JRadioButton();
        rbtOrderByCPU.setBackground(Color.WHITE);
        rbtOrderByCPU.addMouseListener(this);
        rbtOrderByCPU.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        rbtOrderByCPU.setBounds(
                lblOrderByRAM.getX() + lblOrderByRAM.getWidth() + 5,
                25, 25, 25
        );

        lblOrderByCPU = new JLabel();
        lblOrderByCPU.setBounds(
                rbtOrderByCPU.getX() + rbtOrderByCPU.getWidth(),
                25, 75, 25
        );
        lblOrderByCPU.setForeground(Color.decode(COLOR_DARK_TEXT));
        lblOrderByCPU.setFont(new Font(FONT, Font.PLAIN, 12));
        lblOrderByCPU.setText("Uso CPU");

        spnListaProcessos = new JScrollPane();
        spnListaProcessos.setBounds(
                pnlX, 100, pnlProcessos.getWidth() - 50,
                pnlProcessos.getHeight() - 200
        );
        spnListaProcessos.setVerticalScrollBar(new JScrollBar());
        spnListaProcessos.setLayout(null);
    }

    private void initMonitoradorDeHardware() {

        boolean primeiraVez = true;
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(hardwareThreadSleep);

                    // RAM
                    Long usoRAM = looca.getMemoria().getEmUso();
                    Double percentUsoRAM = (usoRAM.doubleValue() / looca.getMemoria().getTotal().doubleValue()) * 100;

                    // CPU
                    Double percentUsoCPU = looca.getProcessador().getUso();

                    // Volumes
                    Double total = 0.0;
                    Double totalDisponivel = 0.0;

                    for (Volume v : listVolume) {
                        total += v.getTotal();
                        totalDisponivel += v.getDisponivel();
                    }

                    Double percentUsoVolumes = ((total - totalDisponivel) / total) * 100;

                    slackDAO.showSlackData(percentUsoVolumes, percentUsoCPU, percentUsoRAM);
                    showHardwareInfo(usoRAM, percentUsoVolumes, percentUsoCPU, percentUsoRAM);
                    //  insertHardwareInfo(percentUsoRAM, percentUsoCPU, percentUsoVolumes);

                }
            } catch (Exception e) {
                logDAO.escreverLog(e.toString());
                e.printStackTrace();
            }
        }).start();
    }

    private void showHardwareInfo(Long usoRAM, Double percentUsoVolumes, Double percentUsoCPU, Double percentUsoRAM) {
        lblVolumesEmUso.setText("Em uso: ");
        for (Volume v : listVolume) {
            lblVolumesEmUso.setText(String.format(
                    "%s %s: %s     ",
                    lblVolumesEmUso.getText(),
                    v.getPontoDeMontagem(),
                    byteCountConvert(v.getTotal() - v.getDisponivel())
            ));
        }
        pgbVolumes.setValue(percentUsoVolumes.intValue());
        pgbVolumes.setString(percentUsoVolumes.intValue() + " %");

        pgbCPU.setValue(percentUsoCPU.intValue());
        pgbCPU.setString(percentUsoCPU.intValue() + " %");

        lblRAMEmUso.setText("Em uso: " + byteCountConvert(usoRAM));
        pgbRAM.setValue(percentUsoRAM.intValue());
        pgbRAM.setString(percentUsoRAM.intValue() + " %");
    }

    private void insertHardwareInfo(Double percentUsoRAM, Double percentUsoCPU, Double percentUsoVolumes) {

        Leitura dado = new Leitura();
        try {
            dado.setPorcentagemUsoMemoria(percentUsoRAM);
            dado.setPorcentagemUsoCPU(percentUsoCPU);
            dado.setPorcentagemUsoDisco(percentUsoVolumes);
            dado.setMaquina(maquinaUser);

            logDAO.escreverLog( " Dados inseridos no banco com sucesso!");
            leituraDao = new LeituraDAO();
            leituraDao.save(dado);

        } catch (Exception e) {
            logDAO.escreverLog(e.toString());
            e.printStackTrace();
        }

    }

    private void initMonitoradorDeProcessos() {

        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(processThreadSleep);

                    lblProcessos.setText(String.format(
                            "Processos (%d)", looca.getGrupoDeProcessos().getTotalProcessos()
                    ));

                    List<Processo> listProcessos = looca.getGrupoDeProcessos().getProcessos();

                    listProcessos.sort((p1, p2) -> {
                        if (Objects.equals(orderBy, "RAM")) {
                            return p2.getUsoMemoria().compareTo(p1.getUsoMemoria());
                        } else {
                            return p2.getUsoCpu().compareTo(p1.getUsoCpu());
                        }
                    });

                    showProcessInfo(listProcessos);
//                    insertProcessInfo(listProcessos);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void showProcessInfo(List<Processo> listProcessos) {
        spnListaProcessos.removeAll();
        Integer indexY = 0;
        for (int i=0; i <10; i++) {
            Processo p = listProcessos.get(i);
            if (p != null) {
                spnListaProcessos.add(newLabelNome(p, indexY));
                spnListaProcessos.add(newLabelRAM(p, indexY));
                spnListaProcessos.add(newLabelCPU(p, indexY));
                indexY += 25;
            }
        }
    }

    private void insertProcessInfo(List<Processo> listProcessos) {
        int count = 0;
        for (Processo p: listProcessos) {
            br.com.lynxcoder.model.Processo processo = new br.com.lynxcoder.model.Processo();
            processo.setPID(p.getPid().toString());
            processo.setNome(p.getNome());
            processo.setMaquina(maqDao.findMaquina(user));
            processDao = new ProcessosDAO();
            processDao.save(processo);
            count++;
            if (count == 5) break;
        }
    }

    public void add() {
        // Navbar
        add(pnlNavbar);

        pnlNavbar.add(lblNavUsuario);
        pnlNavbar.add(lblNavHardwareIcon);
        pnlNavbar.add(lblNavHardware);
        pnlNavbar.add(lblNavProcessosIcon);
        pnlNavbar.add(lblNavProcessos);

        // Hardware Screen
        add(pnlHardwareScreen);

        pnlHardwareScreen.add(pnlSistema);
        pnlSistema.add(lblSistema);
        pnlSistema.add(lblComputador);
        pnlSistema.add(lblPermissao);

        pnlHardwareScreen.add(pnlVolumes);
        pnlVolumes.add(lblVolumes);
        pnlVolumes.add(lblVolumesTotal);
        pnlVolumes.add(lblVolumesEmUso);
        pnlVolumes.add(pgbVolumes);

        pnlHardwareScreen.add(pnlCPU);
        pnlCPU.add(lblCPU);
        pnlCPU.add(lblNomeCPU);
        pnlCPU.add(pgbCPU);

        pnlHardwareScreen.add(pnlRAM);
        pnlRAM.add(lblRAM);
        pnlRAM.add(lblRAMTotal);
        pnlRAM.add(lblRAMEmUso);
        pnlRAM.add(pgbRAM);

        // Processos Screen
        add(pnlProcessosScreen);

        pnlProcessosScreen.add(pnlProcessos);
        pnlProcessos.add(lblProcessos);

        pnlProcessos.add(lblOrderBy);
        pnlProcessos.add(rbtOrderByRAM);
        pnlProcessos.add(lblOrderByRAM);
        pnlProcessos.add(rbtOrderByCPU);
        pnlProcessos.add(lblOrderByCPU);

        pnlProcessos.add(spnListaProcessos);
    }

    public static Color newColorWithAlpha(Color color, Integer alpha) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha);
    }

    public static String byteCountConvert(long bytes) {
        long absB = bytes == Long.MIN_VALUE ? Long.MAX_VALUE : Math.abs(bytes);
        if (absB < 1024) {
            return bytes + " B";
        }
        long value = absB;
        CharacterIterator ci = new StringCharacterIterator("KMGTPE");
        for (int i = 40; i >= 0 && absB > 0xfffccccccccccccL >> i; i -= 10) {
            value >>= 10;
            ci.next();
        }
        value *= Long.signum(bytes);
        return String.format("%.1f %ciB", value / 1024.0, ci.current());
    }

    private JLabel newLabelNome(Processo p, Integer indexY) {
        JLabel lbl = new JLabel();
        lbl.setBounds(10, indexY + 5, 300,25);
        lbl.setText("Nome: " + p.getNome());
        lbl.setForeground(Color.decode(COLOR_DARK_TEXT));
        lbl.setFont(new Font(FONT, Font.PLAIN, 12));
        return lbl;
    }

    private JLabel newLabelRAM(Processo p, Integer indexY) {
        JLabel lbl = new JLabel();
        lbl.setBounds(300, indexY + 5, 200,25);
        lbl.setText(String.format(
                "RAM: %.2f",
                p.getUsoMemoria()
        ) + "%");
        lbl.setForeground(Color.decode(COLOR_DARK_TEXT));
        lbl.setFont(new Font(FONT, Font.PLAIN, 12));
        return lbl;
    }

    private JLabel newLabelCPU(Processo p, Integer indexY) {
        JLabel lbl = new JLabel();
        lbl.setBounds(500, indexY + 5, 200,25);
        lbl.setText(String.format(
                "CPU: %.2f",
                p.getUsoCpu()
        ) + "%");
        lbl.setForeground(Color.decode(COLOR_DARK_TEXT));
        lbl.setFont(new Font(FONT, Font.PLAIN, 12));
        return lbl;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Object source = e.getSource();

        if (lblNavHardwareIcon.equals(source) && !pnlHardwareScreen.isVisible()) {
            pnlHardwareScreen.setVisible(true);
            pnlProcessosScreen.setVisible(false);

            lblNavHardwareIcon.setIcon(hardwareIcon);
            lblNavHardware.setForeground(Color.decode(COLOR_LIGHT_TEXT));

            lblNavProcessosIcon.setIcon(processosUnselectedIcon);
            lblNavProcessos.setForeground(newColorWithAlpha(Color.decode(COLOR_LIGHT_TEXT), 150));
        } else if (lblNavProcessosIcon.equals(source) && !pnlProcessosScreen.isVisible()) {
            pnlHardwareScreen.setVisible(false);
            pnlProcessosScreen.setVisible(true);

            lblNavProcessosIcon.setIcon(processosIcon);
            lblNavProcessos.setForeground(Color.decode(COLOR_LIGHT_TEXT));

            lblNavHardwareIcon.setIcon(hardwareUnselectedIcon);
            lblNavHardware.setForeground(newColorWithAlpha(Color.decode(COLOR_LIGHT_TEXT), 150));
        } else if (rbtOrderByRAM.equals(source)) {
            rbtOrderByCPU.setSelected(false);
            orderBy = "RAM";
        } else if (rbtOrderByCPU.equals(source)) {
            rbtOrderByRAM.setSelected(false);
            orderBy = "CPU";
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
