package com.ggemo.va.bilidanmakuclient;

import com.ggemo.va.bilidanmakuclient.exception.BiliDanmakuClientException;
import com.ggemo.va.bilidanmakuclient.handler.HandlerHolder;
import com.ggemo.va.bilidanmakuclient.http.exception.BiliClientException;
import com.ggemo.va.bilidanmakuclient.http.request.DanmakuServerConfRequest;
import com.ggemo.va.bilidanmakuclient.http.request.RoomInitRequest;
import com.ggemo.va.bilidanmakuclient.http.response.impl.DanmakuServerConfResponse;
import com.ggemo.va.bilidanmakuclient.http.response.impl.RoomInitResponse;
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.DanmakuServerConfResponseData;
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.RoomInitResponseData;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.DataFormatException;

@Slf4j
public class BiliLiveDanmakuClient {
    private long tmpRoomId, uId, roomId;

    private List<DanmakuServerConfResponseData.HostServerInfo> hostServerList;
    private String hostServerToken;

    private RoomInitRequest roomInitRequest;
    private DanmakuServerConfRequest danmakuServerConfRequest;

    private ScheduledExecutorService heartbeatThreadPool;
    private ScheduledFuture heartbeatTask;
    private CompletableFuture<Boolean> hdpTask;

    private static final Random RANDOM = new Random();
    private static final int RECEIVE_BUFFER_SIZE = 10 * 1024;

    // 单位: 秒
    private static final int HEARTBEAT_INTERVAL = 20;

    // 单位: 毫秒
    // 发送heartBeat 20s发一次, 50s都没发过认为发送失败掉线
    private static final int MAX_SEND_HEARTBEAT_INTERVAL = 50000;
    // 接收heartBeat 正常应该几秒收到一次, 20s都没收到认为接收掉线
    private static final int MAX_RECEIVE_HEARTBEAT_INTERVAL = 20000;
    private static final int CHECK_HEARTBEAT_INTERVAL = 10000;

    private HandlerHolder handlerHolder;

    private AtomicLong sendedHeartBeatSuccessedTime;
    private AtomicLong receivedHeartBeatSuccessedTime;

    public BiliLiveDanmakuClient(long roomId, long uId, HandlerHolder handlerHolder) {
        this.tmpRoomId = roomId;
        this.uId = uId;
        this.handlerHolder = handlerHolder;

        this.hostServerToken = null;

        this.roomInitRequest = new RoomInitRequest();
        this.danmakuServerConfRequest = new DanmakuServerConfRequest();

        heartbeatTask = null;
        hdpTask = null;
        heartbeatThreadPool = Executors.newScheduledThreadPool(1, r -> {
            Thread t = new Thread(r);
            t.setName("heartbeat thread");
            return t;
        });

        this.sendedHeartBeatSuccessedTime = new AtomicLong(System.currentTimeMillis());
        this.receivedHeartBeatSuccessedTime = new AtomicLong(System.currentTimeMillis());

    }

    public BiliLiveDanmakuClient(long roomId, HandlerHolder handlerHolder) {
        this(roomId, (long) (1e14 + 2e14 * RANDOM.nextDouble()), handlerHolder);
    }

    private void parseRoomInit(RoomInitResponseData data) {
        this.roomId = data.getRoomId();
    }

    private void parseServerConf(DanmakuServerConfResponseData data) {
        this.hostServerList = data.getHostServerList();
        this.hostServerToken = data.getToken();
    }

    private void initRoom() throws IOException, BiliClientException, BiliDanmakuClientException {
        RoomInitResponse roomInitResponse;
        roomInitResponse = roomInitRequest.request(tmpRoomId);
        RoomInitResponseData roomInitResponseData;
        roomInitResponseData = roomInitResponse.getData();
        this.parseRoomInit(roomInitResponseData);

        DanmakuServerConfResponse danmakuServerConfResponse;
        danmakuServerConfResponse = danmakuServerConfRequest.request(tmpRoomId);
        DanmakuServerConfResponseData danmakuServerConfResponseData;
        danmakuServerConfResponseData = danmakuServerConfResponse.getData();
        this.parseServerConf(danmakuServerConfResponseData);
        if (this.hostServerList == null || this.hostServerList.isEmpty()) {
            throw new BiliDanmakuClientException("initRoom error, hostServerList为null");
        }
    }

    private Socket connect() {
        if (hostServerToken == null) {
            while (true) {
                try {
                    this.initRoom();
                    break;
                } catch (IOException | BiliClientException | BiliDanmakuClientException ignored) {
                }
            }
        }

        Socket socket = null;
        WsClient wsClient = null;

        // for循环用来从后往前选择hostServer
        for (int i = 0; i < this.hostServerList.size(); i++) {
            int hostServerNo = this.hostServerList.size() - 1 - i;

            DanmakuServerConfResponseData.HostServerInfo hostServer = this.hostServerList.get(hostServerNo);

            InetSocketAddress address = new InetSocketAddress(hostServer.getHost(), hostServer.getPort());

            socket = new Socket();
            try {
                socket.setReceiveBufferSize(RECEIVE_BUFFER_SIZE);
                socket.connect(address);
                wsClient = new WsClient(socket);
                wsClient.sendAuth(this.uId, this.roomId, hostServerToken);
                WsClient finalWsClient = wsClient;
                heartbeatTask = heartbeatThreadPool.scheduleAtFixedRate(() -> {
                    try {
                        finalWsClient.sendHeartBeat();
                        sendedHeartBeatSuccessedTime.set(System.currentTimeMillis());
                    } catch (IOException e) {
                        log.error("error in heartbeatTask " + e.toString());
                        cleanHeartBeatTask();
                    }
                }, 2, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
                return socket;
            } catch (IOException e) {
                log.error("error out of heartbeatTask " + e.toString());
                cleanHeartBeatTask();
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }
        return null;
    }

    /**
     * sync
     */
    public void start() {
        while (true) {
            Socket socket = null;
            socket = connect();

            if (socket == null || socket.isClosed()) {
                continue;
            }
            HandleDataLoop hdp = new HandleDataLoop(socket, roomId, handlerHolder, receivedHeartBeatSuccessedTime);

            Socket finalSocket = socket;
            hdpTask = CompletableFuture.supplyAsync(() -> {
                try {
                    hdp.start();
                } catch (IOException | DataFormatException e) {
                    log.error("error in start " + e.toString());
                    cleanHeartBeatTask();
                    try {
                        finalSocket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    return false;
                }
                return true;
            });


            while (true) {
                try {
                    Thread.sleep(CHECK_HEARTBEAT_INTERVAL);
                    long current = System.currentTimeMillis();
                    if (current - sendedHeartBeatSuccessedTime.get() > MAX_SEND_HEARTBEAT_INTERVAL) {
                        cleanHeartBeatTask();
                        break;
                    }
                    if (current - receivedHeartBeatSuccessedTime.get() > MAX_RECEIVE_HEARTBEAT_INTERVAL) {
                        cleanHeartBeatTask();
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    boolean cleanHeartBeatTask() {
        boolean cancelSendHeartBeatRes = true, cancelReceiveHeartBeatRes = true;
        if (heartbeatTask != null && !heartbeatTask.isCancelled()) {
            cancelSendHeartBeatRes = heartbeatTask.cancel(true);
        }
        if (hdpTask != null && !hdpTask.isCancelled()) {
            cancelReceiveHeartBeatRes = hdpTask.cancel(true);
        }
        return cancelSendHeartBeatRes && cancelReceiveHeartBeatRes;
    }

    public static void main(String[] args) throws BiliDanmakuClientException {

    }
}
