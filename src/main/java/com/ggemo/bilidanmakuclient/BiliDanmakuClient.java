package com.ggemo.bilidanmakuclient;

import com.alibaba.fastjson.JSON;
import com.ggemo.bilidanmakuclient.exception.BiliDanmakuClientException;
import com.ggemo.bilidanmakuclient.http.exception.BiliClientException;
import com.ggemo.bilidanmakuclient.http.request.DanmakuServerConfRequest;
import com.ggemo.bilidanmakuclient.http.request.RoomInitRequest;
import com.ggemo.bilidanmakuclient.http.response.impl.DanmakuServerConfResponse;
import com.ggemo.bilidanmakuclient.http.response.impl.RoomInitResponse;
import com.ggemo.bilidanmakuclient.http.response.responsedataa.DanmakuServerConfResponseData;
import com.ggemo.bilidanmakuclient.http.response.responsedataa.RoomInitResponseData;
import com.ggemo.bilidanmakuclient.structs.SendAuthDO;
import lombok.extern.slf4j.Slf4j;
import struct.JavaStruct;

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Supplier;

@Slf4j
public class BiliDanmakuClient {
    private long tmpRoomId, uId, roomId, roomShortId, roomOwnerUid;
    private int heartBeatInterval;
    private boolean ssl;
    private boolean loop;
    private BiliDanmakuWebSocketClient ws;

    private List<DanmakuServerConfResponseData.HostServerInfo> hostServerList;
    private String hostServerToken;

    private RoomInitRequest roomInitRequest;
    private DanmakuServerConfRequest danmakuServerConfRequest;

    private ExecutorService heartBeatExecutor;
    private ExecutorService heartBeatLoopExecutor;


    public BiliDanmakuClient(long roomId, long uId, int heartBeatInterval, boolean ssl, boolean loop) {
        this.tmpRoomId = roomId;
        this.uId = uId;
        this.heartBeatInterval = heartBeatInterval;
        this.ssl = ssl;
        this.loop = loop;

        this.hostServerToken = null;

        this.roomInitRequest = new RoomInitRequest();
        this.danmakuServerConfRequest = new DanmakuServerConfRequest();

        this.heartBeatExecutor = Executors.newSingleThreadExecutor((r) -> {
            Thread t = new Thread(r);
            t.setName("heartbeat thread");
            return t;
        });

        this.heartBeatLoopExecutor = Executors.newSingleThreadExecutor((r) -> {
            Thread t = new Thread(r);
            t.setName("heartbeatLoop thread");
            return t;
        });
    }

    private void parseRoomInit(RoomInitResponseData data) {
        this.roomId = data.getRoomId();
        this.roomShortId = data.getShortId();
        this.roomOwnerUid = data.getUid();
    }

    private void parseServerConf(DanmakuServerConfResponseData data) {
        this.hostServerList = data.getHostServerList();
        this.hostServerToken = data.getToken();
    }

    private boolean initRoom() throws IOException, BiliClientException, BiliDanmakuClientException {
        RoomInitResponse roomInitResponse;
        try {
            roomInitResponse = roomInitRequest.request(tmpRoomId);
        } catch (IOException e) {
            String exceptionMsg = "roomInit IOException";
            log.error(exceptionMsg + e);
            throw new IOException(exceptionMsg);
        }
        RoomInitResponseData roomInitResponseData;
        try {
            roomInitResponseData = roomInitResponse.getData();
            this.parseRoomInit(roomInitResponseData);
        } catch (BiliClientException e) {
            String exceptionMsg = "roomInitResponse.getData BiliException";
            log.error(exceptionMsg + e);
            throw new BiliClientException(exceptionMsg);
        }

        DanmakuServerConfResponse danmakuServerConfResponse;
        try {
            danmakuServerConfResponse = danmakuServerConfRequest.request(tmpRoomId);
        } catch (IOException e) {
            String exceptionMsg = "danmakuServerConf IOException";
            log.error(exceptionMsg + e);
            throw new IOException(exceptionMsg);
        }
        DanmakuServerConfResponseData danmakuServerConfResponseData;
        try {
            danmakuServerConfResponseData = danmakuServerConfResponse.getData();
            this.parseServerConf(danmakuServerConfResponseData);
        } catch (BiliClientException e) {
            String exceptionMsg = "danmakuServerConfResponse.getData BiliException";
            log.error(exceptionMsg + e);
            throw new BiliClientException(exceptionMsg);
        }

        this.parseServerConf(danmakuServerConfResponseData);
        if (this.hostServerList == null || this.hostServerList.isEmpty()) {
            throw new BiliDanmakuClientException("初始化error, hostServerList为null");
        }
        return true;
    }

    private void messageLoop() throws BiliDanmakuClientException {
        if (hostServerToken == null) {
            boolean initRes = false;
            try {
                initRes = this.initRoom();
            } catch (IOException | BiliClientException e) {
                throw new BiliDanmakuClientException("初始化error");
            }
        }

        int retryCount = 0;
        while (true) {
            DanmakuServerConfResponseData.HostServerInfo hostServer = this.hostServerList.get(retryCount % this.hostServerList.size());
            BiliDanmakuWebSocketClient ws = null;
            try {
                ws = new BiliDanmakuWebSocketClient(String.format("ws://%s:%d/sub", hostServer.getHost(), hostServer.getWsPort()));
                this.ws = ws;
                ws.connect();
                retryCount = 0;
                sendAuth(hostServerToken);
                CompletableFuture.supplyAsync(() -> {
                    heartBeatLoop();
                    return null;
                }, heartBeatLoopExecutor);

            } finally {
                if (ws != null) {
                    ws.close();
                }
            }
        }
    }

    private void heartBeatLoop() {
        while (true) {
            CompletableFuture.supplyAsync(() -> {
                BiliDanmakuClient.this.heartBeat();
                return true;
            });
            try {
                Thread.sleep(heartBeatInterval);
            } catch (InterruptedException e) {
                log.error("heartbeat loop sleep error: " + e.toString());
            }
        }
    }

    private void sendAuth(String token) {
        SendAuthDO sendAuthData = new SendAuthDO(this.uId, this.roomId, token);

        this.ws.send(MakePacket.makePacket(OperationEnum.AUTH, sendAuthData));
    }

    private void heartBeat() {
        ws.send(MakePacket.getHEARTBEAT_PACKET());
    }


    public static void main(String[] args) throws BiliDanmakuClientException {
        new BiliDanmakuClient(66688L, 13578650, 30000, true, false).messageLoop();
    }
}
