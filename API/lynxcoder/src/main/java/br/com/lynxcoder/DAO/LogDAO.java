package br.com.lynxcoder.DAO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogDAO {

    public void criarLog(String nameFile) {

        nameFile += ".txt";

        File diretorio = new File("C:\\java", nameFile);

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
