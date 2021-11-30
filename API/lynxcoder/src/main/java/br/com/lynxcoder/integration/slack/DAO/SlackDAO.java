package br.com.lynxcoder.integration.slack.DAO;

import br.com.lynxcoder.integration.slack.conn.SlackConnection;
import br.com.lynxcoder.model.Usuario;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SlackDAO {

    private final SlackConnection slackConn = new SlackConnection();

    public void welcomeMessage(Usuario user) {

        JSONObject json = new JSONObject();

        json.put("text", "Bem vindo(a) "+user.getNome()+"!! :palmas:");

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

    public void showSlackData(Double percentUsoVolumes, Double percentUsoCpu, Double percentUsoRAM) {

        JSONObject json = new JSONObject();

        if (percentUsoVolumes.intValue() > 80) {

        }

        if (percentUsoCpu.intValue() > 80) {

        }

        if (percentUsoRAM.intValue() > 80) {

        }

        json.put(
            "text", "\nPorcentagem de uso [VOLUMES]: "+percentUsoVolumes.intValue()+"%\n"+
            "Porcentagem de uso [CPU]: "+percentUsoCpu.intValue()+"%\n"+
            "Porcentagem de uso [RAM]: "+percentUsoRAM.intValue()+"%\n\n"
        );

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

    }

    public void responseSlackData() {


    }

}
