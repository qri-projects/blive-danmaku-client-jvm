package com.ggemo.va.bilidanmakuclient.http;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;

public class MyHttpClient {
    private CloseableHttpClient httpClient;
    private static final MyHttpClient INSTANCE = new MyHttpClient();

    private MyHttpClient() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        HttpClientBuilder clientbuilder = HttpClients.custom().setConnectionManager(cm);
        clientbuilder.disableCookieManagement();
        this.httpClient = clientbuilder.build();
    }

    public static MyHttpClient getInstance() {
        return INSTANCE;
    }

    public CloseableHttpResponse request(HttpPost httpPost) throws IOException {
        return httpClient.execute(httpPost);
    }


    public CloseableHttpResponse request(HttpGet httpGet) throws IOException {
        return httpClient.execute(httpGet);
    }
}
