package br.com.lynxcoder.view;

import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Disco;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Dashboard extends JFrame {
    Looca looca = new Looca();
    List<Disco> listDisco = looca.getGrupoDeDiscos().getDiscos();

    // Labels
    JLabel lblNomeComputador;
    JLabel lblRamTotal;
    JLabel lblNomeProcessador;
    JLabel lblDiscos;

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
            toGB(looca.getMemoria().getTotal().toString())
        ));

        lblNomeProcessador = new JLabel();
        lblNomeProcessador.setBounds(lblNomeComputador.getX(), lblRamTotal.getY() + lblRamTotal.getHeight() + 5, 700, 25);
        lblNomeProcessador.setForeground(Color.WHITE);
        lblNomeProcessador.setFont(new Font("TimesNewRoman", Font.BOLD,12));
        lblNomeProcessador.setText(String.format(
            "Processador: %s",
            looca.getProcessador().getNome()
        ));

        lblDiscos = new JLabel();
        lblDiscos.setBounds(lblNomeComputador.getX(), lblNomeProcessador.getY() + lblNomeProcessador.getHeight() + 5, 700, 25);
        lblDiscos.setForeground(Color.WHITE);
        lblDiscos.setFont(new Font("TimesNewRoman", Font.BOLD,12));

        for (Disco d: listDisco) {
            lblDiscos.setText(String.format(
                "%sDisco %s: %s\n", lblDiscos.getText(), (char) (listDisco.indexOf(d) + 65), toGB(d.getTamanho().toString())
            ));
        }
    }

    private void initCharts() {
        pgbRAM = new JProgressBar();
        pgbRAM.setBounds(5, lblDiscos.getY() + lblDiscos.getHeight() + 5, 700, 25);
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
                        Double usoDisco = 0.0;

                        for (Disco d: listDisco) {
                            usoDisco += d.getTamanhoAtualDaFila();
                            System.out.println(d.getTamanhoAtualDaFila());
                        }

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
        add(lblDiscos);
        add(pgbRAM);
        add(pgbCPU);
        add(pgbDisco);
    }

    public String toGB(String total) {
        String totalGb = "";
        Integer digitos;

        switch(total.length()) {
            case 10:
                digitos = 1;
                break;
            case 11:
                digitos = 2;
                break;
            case 12:
                digitos = 3;
                break;
            default:
                digitos = 4;
                break;
        }

        for (int i = 1; i <= digitos; i++) {
            totalGb += total.charAt(i -1);
            if (i == digitos) totalGb += ",";
        }

        for (int i = 0; i < 3; i++) {
            totalGb += total.charAt(i + digitos);
        }

        return totalGb += " GiB";
    }
}
