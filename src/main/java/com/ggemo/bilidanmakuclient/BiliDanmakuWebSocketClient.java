package com.ggemo.bilidanmakuclient;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class BiliDanmakuWebSocketClient extends WebSocketClient {

    public BiliDanmakuWebSocketClient(String uri) {
        super(URI.create(uri));
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("onOpen");
    }

    @Override
    public void onMessage(String s) {
        System.out.println("message: " + s);
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("close" + s);
    }

    @Override
    public void onError(Exception e) {
        System.out.println(e);
        e.printStackTrace();
    }

    public static BiliDanmakuWebSocketClient init(String host, int port, boolean ssl){
        String uri;
        if(ssl){
            uri = String.format("wss://%s:%d/sub", host, port);
        }else{
            uri = String.format("ws://%s:%d/sub", host, port);
        }
        return new BiliDanmakuWebSocketClient(uri);
    }

}
