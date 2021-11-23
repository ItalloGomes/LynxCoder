package br.com.lynxcoder.view;

import br.com.lynxcoder.DAO.LeituraDAO;
import br.com.lynxcoder.DAO.LogDAO;
import br.com.lynxcoder.DAO.MaquinaDAO;
import br.com.lynxcoder.DAO.ProcessosDAO;
import br.com.lynxcoder.model.Leitura;
import br.com.lynxcoder.model.Maquina;
import br.com.lynxcoder.model.Usuario;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.discos.Volume;
import com.github.britooo.looca.api.group.processos.Processo;

import javax.swing.*;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;

public class Dashboard {

    Looca looca = new Looca();
    private final int hardwareThreadSleep = 10_000;
    private final int processThreadSleep = 10_000;

    List<Volume> listVolume = looca.getGrupoDeDiscos().getVolumes();

    MaquinaDAO maqDao;
    LeituraDAO leituraDao;
    ProcessosDAO processDao;

    Usuario user;
    LogDAO logDAO;
    Maquina maquinaUser;

    public Dashboard(Usuario user, LogDAO logDAO) {

        this.user = user;
        this.logDAO = logDAO;

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

                    maqDao.adicionarMaquina(user, maquina);

                    this.maquinaUser = maqDao.findMaquina(user);

                    initMonitoradorDeHardware();
                    initMonitoradorDeProcessos();

                    break;
                default:
                    break;
            }
        } else {
            this.maquinaUser = maqDao.findMaquina(user);

            initMonitoradorDeHardware();
            initMonitoradorDeProcessos();
        }
    }
    private void initMonitoradorDeHardware() {

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

                    insertHardwareInfo(percentUsoRAM, percentUsoCPU, percentUsoVolumes);
                }
            } catch (Exception e) {
                logDAO.escreverLog(e.toString());
                e.printStackTrace();
            }
        }).start();
    }

    private void insertHardwareInfo(Double percentUsoRAM, Double percentUsoCPU, Double percentUsoVolumes) {

        Leitura dado = new Leitura();

        try {
            dado.setPorcentagemUsoMemoria(percentUsoRAM);
            dado.setPorcentagemUsoCPU(percentUsoCPU);
            dado.setPorcentagemUsoDisco(percentUsoVolumes);
            dado.setMaquina(maquinaUser);

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

                    List<Processo> listProcessos = looca.getGrupoDeProcessos().getProcessos();

                    listProcessos.sort((p1, p2) -> p2.getUsoMemoria().compareTo(p1.getUsoMemoria()));

                    insertProcessInfo(listProcessos);
                }
            } catch (Exception e) {
                logDAO.escreverLog(e.toString());
                e.printStackTrace();
            }
        }).start();
    }

    private void insertProcessInfo(List<Processo> listProcessos) {
            br.com.lynxcoder.model.Processo processo = new br.com.lynxcoder.model.Processo();
            processo.setPID(listProcessos.get(0).getPid().toString());
            processo.setNome(listProcessos.get(0).getNome());
            processo.setMaquina(maqDao.findMaquina(user));
            processDao = new ProcessosDAO();
            processDao.save(processo);
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

}
