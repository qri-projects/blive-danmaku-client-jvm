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

    private boolean initRoom() {
        RoomInitResponse roomInitResponse;
        try {
            roomInitResponse = roomInitRequest.request(tmpRoomId);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        RoomInitResponseData roomInitResponseData;
        try {
            roomInitResponseData = roomInitResponse.getData();
            if (roomInitResponseData == null) {
                return false;
            } else {
                this.parseRoomInit(roomInitResponseData);
            }
        } catch (BiliClientException e) {
            e.printStackTrace();
            return false;
        }

        DanmakuServerConfResponse danmakuServerConfResponse;
        try {
            danmakuServerConfResponse = danmakuServerConfRequest.request(tmpRoomId);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        DanmakuServerConfResponseData danmakuServerConfResponseData;
        try {
            danmakuServerConfResponseData = danmakuServerConfResponse.getData();
            if (danmakuServerConfResponseData == null) {
                return false;
            }else{
                this.parseServerConf(danmakuServerConfResponseData);
            }
        } catch (BiliClientException e) {
            e.printStackTrace();
            return false;
        }

        this.parseServerConf(danmakuServerConfResponseData);
        if (this.hostServerList == null || this.hostServerList.isEmpty()) {
            return false;
        }
        return true;
    }

    private void messageLoop() throws BiliDanmakuClientException {
        if (hostServerToken == null) {
            boolean initRes = this.initRoom();
            if (!initRes) {
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

                try {
                    sendAuth(hostServerToken);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                try {
                    BiliDanmakuClient.this.heartBeat();
                    return true;
                } catch (IOException e) {
                    log.error("heartbeat error: " + e.toString());
                    return false;
                }
            });
            try {
                Thread.sleep(heartBeatInterval);
            } catch (InterruptedException e) {
                log.error("heartbeat loop sleep error: " + e.toString());
            }
        }
    }

    private void sendAuth(String token) throws IOException {
        SendAuthDO sendAuthData = new SendAuthDO(this.uId, this.roomId, token);

        this.ws.send(MakePacket.makePacket(OperationEnum.AUTH, sendAuthData));
    }

    private void heartBeat() throws IOException {
        ws.send(MakePacket.getHEARTBEAT_PACKET());
    }


    public static void main(String[] args) throws BiliDanmakuClientException {
        new BiliDanmakuClient(66688L, 13578650, 30000, true, false).messageLoop();
    }
}
