package br.com.lynxcoder.integration.slack.conn;


import java.net.http.HttpClient;

public class SlackConnection {

    private HttpClient client = HttpClient.newHttpClient();
    private String URL = "https://hooks.slack.com/services/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";

    public HttpClient getClient() {
        return client;
    }

    public String getURL() {
        return URL;
    }

}
