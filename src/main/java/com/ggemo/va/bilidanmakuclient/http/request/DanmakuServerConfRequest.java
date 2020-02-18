package com.ggemo.va.bilidanmakuclient.http.request;

import com.ggemo.va.bilidanmakuclient.http.MyHttpClient;
import com.ggemo.va.bilidanmakuclient.http.response.impl.DanmakuServerConfResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class DanmakuServerConfRequest implements Request {
    private static final String DANMAKU_SERVER_CONF_URL_TEMPLATE = "https://api.live.bilibili.com/room/v1/Danmu/getConf?id=%d&platform=pc&player=web";

    private MyHttpClient httpClient;


    public DanmakuServerConfRequest() {
        this.httpClient = MyHttpClient.getInstance();
    }

    public DanmakuServerConfResponse request(long roomId) throws IOException {
        HttpGet httpGet = new HttpGet(String.format(DANMAKU_SERVER_CONF_URL_TEMPLATE, roomId));
        CloseableHttpResponse httpResponse = this.httpClient.request(httpGet);
        HttpEntity responseEntity = null;
        try (httpResponse) {
            responseEntity = httpResponse.getEntity();
            return DanmakuServerConfResponse.parse(EntityUtils.toString(responseEntity));
        } finally {
            EntityUtils.consume(responseEntity);
        }
    }
}
