package br.com.lynxcoder.DAO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogDAO {

    public void criarLog(String logName) {

        logName += ".txt";

        File diretorio = new File("C:\\Users\\guilherme.scheleger\\Desktop\\LynxCoder\\API\\lynxcoder", logName);

        if (diretorio.isDirectory()) {
            FileWriter arquivo = null;

            try {
                arquivo = new FileWriter("banco.txt", false);
                arquivo.write("teste log");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
