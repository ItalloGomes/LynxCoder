package br.com.lynxcoder.DAO;
import com.github.britooo.looca.api.core.Looca;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogDAO {

    Date data = new Date();
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String dataFormatada = dateFormat.format(data);
    Looca looca = new Looca();
    File arquivo;


    public void criarLog(String nomeArquivo) {

        nomeArquivo += ".txt";


        try {
            arquivo = new File("C:\\Users\\fabiane.segato\\OneDrive - Yandeh\\Documentos\\Sprints\\LynxCoder-sprint3\\LynxCoder\\API\\lynxcoder", nomeArquivo);

            if (arquivo.createNewFile()) {
                System.out.println("Arquivo criado");
                System.out.println("Registro criado: " + dataFormatada);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao criar arquivo");
        }
    }


        public void escreverLog(String conteudo){

            try {
                FileWriter escritor = new FileWriter(arquivo, true);
                escritor.write(dataFormatada + conteudo + "\n");

                escritor.close();
                System.out.println("Registro criado: " + dataFormatada);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Registro não criado: " + dataFormatada);
            }


        }
    }

