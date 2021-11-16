package br.com.lynxcoder.DAO;
import com.github.britooo.looca.api.core.Looca;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogDAO {

    File arquivo;

    public void criarLog(String nomeArquivo) {

        nomeArquivo += ".txt";
        System.out.println(nomeArquivo);
        arquivo = new File("C:\\bandtec\\LynxCoder\\API\\lynxcoder", nomeArquivo);
        try {
            if (arquivo.createNewFile()) {
                System.out.println("Arquivo criado");
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao criar arquivo");
        }
    }

    public void escreverLog(Double conteudo) {

        Date data = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = dateFormat.format(data);

        try {
            FileWriter escritor = new FileWriter(arquivo, false);
            escritor.write(dataFormatada + conteudo);
            escritor.close();
            System.out.println("Registro criado: " + dataFormatada);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Registro não criado: " + dataFormatada);
        }


    }
}
