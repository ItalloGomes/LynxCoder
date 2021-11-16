package br.com.lynxcoder.DAO;
import com.github.britooo.looca.api.core.Looca;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogDAO {

    public void criarLog(String logName) {

        logName += ".txt";
        File diretorio = new File("C:\\Users\\guilherme.scheleger\\Desktop\\LynxCoder\\API\\lynxcoder", logName);

    }

    public void escreverLog(Double conteudo) {

        Date data = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = dateFormat.format(data);
        Looca looca = new Looca();
        conteudo = looca.getProcessador().getUso();
        String logName = dataFormatada;
        logName += ".txt";

        try {
            FileWriter arquivo = new FileWriter(logName, false);
            arquivo.write(String.valueOf(conteudo));
            arquivo.close();
            System.out.println("Arquivo criado");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Arqivo não criado");
        }


    }
}
