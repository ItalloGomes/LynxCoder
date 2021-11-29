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
    String caminho;

    public void criarLog( String nomeArquivo) {

        nomeArquivo += ".txt";


        try {
            arquivo = new File(caminho, nomeArquivo);


            if (arquivo.createNewFile()) {
                System.out.println("Log criado com sucesso");

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


            } catch (IOException e) {
                e.printStackTrace();

            }


        }
    }

