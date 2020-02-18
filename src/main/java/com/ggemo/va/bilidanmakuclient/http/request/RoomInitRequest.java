package com.ggemo.va.bilidanmakuclient.http.request;

import com.ggemo.va.bilidanmakuclient.http.MyHttpClient;
import com.ggemo.va.bilidanmakuclient.http.response.impl.RoomInitResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class RoomInitRequest implements Request {
    private static final String ROOM_INIT_URL_TEMPLATE = "https://api.live.bilibili.com/room/v1/Room/room_init?id=%d";
    private MyHttpClient httpClient;


    public RoomInitRequest() {
        this.httpClient = MyHttpClient.getInstance();
    }

    public RoomInitResponse request(long roomId) throws IOException {
        HttpGet httpGet = new HttpGet(String.format(ROOM_INIT_URL_TEMPLATE, roomId));
        CloseableHttpResponse httpResponse = this.httpClient.request(httpGet);

        HttpEntity responseEntity = httpResponse.getEntity();
        try {
            RoomInitResponse roomInitResponse = RoomInitResponse.parse(EntityUtils.toString(responseEntity));
            return roomInitResponse;
        } finally {
            EntityUtils.consume(responseEntity);
            httpResponse.close();
        }
    }
}
