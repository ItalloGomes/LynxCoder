package br.com.lynxcoder.view;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Dashboard extends JFrame {
    Looca looca = new Looca();
    List<Volume> listVolume = looca.getGrupoDeDiscos().getVolumes();

    // Labels
    JLabel lblNomeComputador;
    JLabel lblRamTotal;
    JLabel lblNomeProcessador;
    JLabel lblVolumes;

    // Charts
    JProgressBar pgbRAM;
    JProgressBar pgbCPU;
    JProgressBar pgbDisco;

    public Dashboard() {
        initDashboard();
        initLabels();
        initCharts();
        populateCharts();

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
        this.getContentPane().setBackground(new Color(38, 24, 71));
    }

    private void initLabels() {
        lblNomeComputador = new JLabel();
        lblNomeComputador.setBounds(5, 10, 700, 25);
        lblNomeComputador.setForeground(Color.WHITE);
        lblNomeComputador.setFont(new Font("TimesNewRoman", Font.BOLD,20));
        lblNomeComputador.setText(String.format(
            "%s %s - Executando como %s",
            looca.getSistema().getFabricante(),
            looca.getSistema().getSistemaOperacional(),
            looca.getSistema().getPermissao() ? "admin" : "usuário padrão"
        ));

        lblRamTotal = new JLabel();
        lblRamTotal.setBounds(lblNomeComputador.getX(), lblNomeComputador.getY() + lblNomeComputador.getHeight() + 5, 700, 25);
        lblRamTotal.setForeground(Color.WHITE);
        lblRamTotal.setFont(new Font("TimesNewRoman", Font.BOLD,12));
        lblRamTotal.setText(String.format(
            "Total RAM: %s",
                FileUtils.byteCountToDisplaySize(looca.getMemoria().getTotal())
        ));

        lblNomeProcessador = new JLabel();
        lblNomeProcessador.setBounds(lblNomeComputador.getX(), lblRamTotal.getY() + lblRamTotal.getHeight() + 5, 700, 25);
        lblNomeProcessador.setForeground(Color.WHITE);
        lblNomeProcessador.setFont(new Font("TimesNewRoman", Font.BOLD,12));
        lblNomeProcessador.setText(String.format(
            "Processador: %s",
            looca.getProcessador().getNome()
        ));

        lblVolumes = new JLabel();
        lblVolumes.setBounds(lblNomeComputador.getX(), lblNomeProcessador.getY() + lblNomeProcessador.getHeight() + 5, 700, 25);
        lblVolumes.setForeground(Color.WHITE);
        lblVolumes.setFont(new Font("TimesNewRoman", Font.BOLD,12));

        for (Volume v: listVolume) {
            lblVolumes.setText(String.format(
                "%sDisco %s: %s     ", lblVolumes.getText(), v.getPontoDeMontagem(), FileUtils.byteCountToDisplaySize(v.getTotal())
            ));
        }
    }

    private void initCharts() {
        pgbRAM = new JProgressBar();
        pgbRAM.setBounds(5, lblVolumes.getY() + lblVolumes.getHeight() + 5, 700, 25);
        pgbRAM.setStringPainted(true);
        pgbRAM.setMaximum(100);

        pgbCPU = new JProgressBar();
        pgbCPU.setBounds(5, pgbRAM.getY() + pgbRAM.getHeight() + 5, 700, 25);
        pgbCPU.setStringPainted(true);
        pgbCPU.setMaximum(100);

        pgbDisco = new JProgressBar();
        pgbDisco.setBounds(5, pgbCPU.getY() + pgbCPU.getHeight() + 5, 700, 25);
        pgbDisco.setStringPainted(true);
        pgbDisco.setMaximum(100);
    }

    private void populateCharts() {

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

                        pgbRAM.setValue(usoRAM.intValue());
                        pgbCPU.setValue(usoCPU.intValue());
                        pgbDisco.setValue(usoDisco.intValue());

                        pgbRAM.setString(usoRAM.intValue() + " %");
                        pgbCPU.setString(usoCPU.intValue() + " %");
                        pgbDisco.setString(usoDisco.intValue() + " %");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void add() {
        add(lblNomeComputador);
        add(lblRamTotal);
        add(lblNomeProcessador);
        add(lblVolumes);
        add(pgbRAM);
        add(pgbCPU);
        add(pgbDisco);
    }
}
