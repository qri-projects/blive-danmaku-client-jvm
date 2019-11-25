package com.ggemo.bilidanmakuclient.http;

import com.ggemo.bilidanmakuclient.http.response.impl.RoomInitResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class InitRoom {
    private static final String ROOM_INIT_URL_TEMPLATE = "https://api.live.bilibili.com/room/v1/Room/room_init?id=%d";
    private static final String DANMAKU_SERVER_CONF_URL_TEMPLATE = "https://api.live.bilibili.com/room/v1/Danmu/getConf?id=%d";
    private MyHttpClient httpClient;

    public InitRoom() {
        this.httpClient = MyHttpClient.getInstance();
    }

    public RoomInitResponse initRoom(long roomId) throws IOException {
        HttpGet httpGet = new HttpGet(String.format(ROOM_INIT_URL_TEMPLATE, roomId));
        CloseableHttpResponse httpResponse = this.httpClient.request(httpGet);

        HttpEntity responseEntity = httpResponse.getEntity();

        RoomInitResponse roomInitResponse = RoomInitResponse.parse(EntityUtils.toString(responseEntity));

        httpGet = new HttpGet(String.format(DANMAKU_SERVER_CONF_URL_TEMPLATE, roomId));
        httpResponse = this.httpClient.request(httpGet);

        responseEntity = httpResponse.getEntity();
        try {
            return RoomInitResponse.parse(EntityUtils.toString(responseEntity));
        } finally {
            EntityUtils.consume(responseEntity);
            httpResponse.close();
        }
    }

    public static void main(String[] args) throws IOException {
        var x = new InitRoom();
        var res = x.initRoom(21450685L);
        System.out.println(res);
    }
}
