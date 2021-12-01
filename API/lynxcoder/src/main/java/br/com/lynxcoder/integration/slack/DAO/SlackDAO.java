package br.com.lynxcoder.integration.slack.DAO;

import br.com.lynxcoder.integration.slack.conn.SlackConnection;
import br.com.lynxcoder.model.Usuario;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class SlackDAO {

    private final SlackConnection slackConn = new SlackConnection();

    private Integer iteratorRequestRam = 0;
    private Integer iteratorRequestVol = 0;

    private LocalDateTime dateFirstRequestRam;
    private LocalDateTime dateFirstRequestVol;

    private void sendHttpResponseJson(JSONObject json) {

        HttpRequest request = HttpRequest.newBuilder(URI.create(slackConn.getURL()))
        .header("accept", "application/json")
        .POST(HttpRequest.BodyPublishers.ofString(json.toString()))
        .build();

        HttpResponse<String> response = null;

        try {
            response = slackConn.getClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("Status: %s", response.statusCode()));
        System.out.println(String.format("Response: %s", response.body()));

    }

    public void welcomeMessage(Usuario user) {

        JSONObject json = new JSONObject();

        json.put("text", "Bem vindo(a) "+user.getNome()+"!! :palmas:");
        
        sendHttpResponseJson(json);
        
    }

    public void receiveMaqData(Double percentUsoVolumes, Double percentUsoRAM, Usuario user) {

        JSONObject json = new JSONObject();
        LocalDateTime dateRequestNow = LocalDateTime.now();

        if (percentUsoVolumes.intValue() > 79) {
            iteratorRequestVol++;

            if (iteratorRequestVol == 1) {
                dateFirstRequestVol = LocalDateTime.now();
                json.put(
                    "text",
                    "Eii "+user.getNome()+"..sua máquina ta precisando de uma limpeza!\n" +
                    "Porcentagem de uso [VOLUMES]: " + percentUsoVolumes.intValue() + "%\n\n"
                );
                sendHttpResponseJson(json);
            }

            if (iteratorRequestVol > 1 && dateRequestNow.getHour() == dateFirstRequestVol.plusHours(3).getHour()) {
                json.put(
                    "text",
                    user.getNome()+" depois da uma olhadinha se tem arquivos para excluir da sua máquina!\n" +
                    "Porcentagem de uso [VOLUMES]: " + percentUsoVolumes.intValue() + "%\n\n"
                );
                sendHttpResponseJson(json);
            }
        }

        if (percentUsoRAM.intValue() > 79) {
            iteratorRequestRam++;

            if (iteratorRequestRam == 1) {
                dateFirstRequestRam = LocalDateTime.now();
                json.put(
                    "text",
                    "Eii "+user.getNome()+"..sua máquina pode ficar lenta!\n" +
                    "Porcentagem de uso [RAM]: " + percentUsoRAM.intValue() + "%\n\n"
                );
                sendHttpResponseJson(json);
            }

            if (iteratorRequestRam > 1 && dateRequestNow.getHour() == dateFirstRequestRam.plusHours(3).getHour()) {
                json.put(
                    "text",
                    user.getNome()+" depois da uma olhadinha se sua máquina não está rodando programas desnecessários\n" +
                    "Porcentagem de uso [RAM]: " + percentUsoRAM.intValue() + "%\n\n"
                );
                sendHttpResponseJson(json);
            }
        }
        
    }

}
