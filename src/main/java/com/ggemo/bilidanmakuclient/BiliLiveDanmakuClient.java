package com.ggemo.bilidanmakuclient;

import com.ggemo.bilidanmakuclient.exception.BiliDanmakuClientException;
import com.ggemo.bilidanmakuclient.handler.HandlerHolder;
import com.ggemo.bilidanmakuclient.http.exception.BiliClientException;
import com.ggemo.bilidanmakuclient.http.request.DanmakuServerConfRequest;
import com.ggemo.bilidanmakuclient.http.request.RoomInitRequest;
import com.ggemo.bilidanmakuclient.http.response.impl.DanmakuServerConfResponse;
import com.ggemo.bilidanmakuclient.http.response.impl.RoomInitResponse;
import com.ggemo.bilidanmakuclient.http.response.responsedataa.DanmakuServerConfResponseData;
import com.ggemo.bilidanmakuclient.http.response.responsedataa.RoomInitResponseData;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@Slf4j
public class BiliLiveDanmakuClient {
    private long tmpRoomId, uId, roomId;

    private List<DanmakuServerConfResponseData.HostServerInfo> hostServerList;
    private String hostServerToken;

    private RoomInitRequest roomInitRequest;
    private DanmakuServerConfRequest danmakuServerConfRequest;

    private ScheduledExecutorService heartbeatThreadPool;
    private ScheduledFuture heartbeatTask;

    private static final Random RANDOM = new Random();
    private static final int RECEIVE_BUFFER_SIZE = 10 * 1024;
    private static final int HEARTBEAT_INTERVAL = 20; // 单位: 秒

    private HandlerHolder handlerHolder;

    public BiliLiveDanmakuClient(long roomId, long uId, HandlerHolder handlerHolder) {
        this.tmpRoomId = roomId;
        this.uId = uId;
        this.handlerHolder = handlerHolder;

        this.hostServerToken = null;

        this.roomInitRequest = new RoomInitRequest();
        this.danmakuServerConfRequest = new DanmakuServerConfRequest();

        heartbeatTask = null;
        heartbeatThreadPool = Executors.newScheduledThreadPool(1, r -> {
            Thread t = new Thread(r);
            t.setName("heartbeat thread");
            return t;
        });
    }

    public BiliLiveDanmakuClient(long roomId, long uId) {
        this(roomId, uId, new HandlerHolder());
    }

    public BiliLiveDanmakuClient(long roomId) {
        this(roomId, (long) (1e14 + 2e14 * RANDOM.nextDouble()));
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

        this.parseServerConf(danmakuServerConfResponseData);
        if (this.hostServerList == null || this.hostServerList.isEmpty()) {
            throw new BiliDanmakuClientException("初始化error, hostServerList为null");
        }
    }

    private Socket connect() throws IOException {
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
//            try {
                socket.setReceiveBufferSize(RECEIVE_BUFFER_SIZE);
                socket.connect(address);
                wsClient = new WsClient(socket);
                if (wsClient.sendAuth(this.uId, this.roomId, hostServerToken)) {
                    heartbeatTask = heartbeatThreadPool.scheduleAtFixedRate(wsClient::sendHeartBeat, 2, HEARTBEAT_INTERVAL, TimeUnit.SECONDS);
                    return socket;
                } else {
                    cleanHeartBeatTask();
                    try {
                        socket.close();
                } catch (IOException ignored) {
                }
                }
//            } catch (IOException e) {
//                log.error(e.toString());
//                cleanHeartBeatTask();
//            }
        }
        return socket;
    }

    /**
     * sync
     */
    public void start() {
        while (true) {
            Socket socket = null;
            try {
                socket = connect();
            }catch (IOException e){
                log.error(e.toString());
                cleanHeartBeatTask();
                continue;
            }
            if (socket != null && !socket.isClosed()) {
                HandleDataLoop hdp = new HandleDataLoop(socket, roomId, this.handlerHolder);
                try {
                    hdp.start();
                } catch (IOException e) {
                    log.error(e.toString());
                    cleanHeartBeatTask();
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

//    public void start(int nThreads) {
//        var pool = Executors.newFixedThreadPool(nThreads, r -> {
//            Thread t = new Thread(r);
//            t.setName("运行线程");
//            return t;
//        });
//        for (int i = 0; i < nThreads; i++) {
//            pool.execute(this::start);
//        }
//    }
//
//    public void startSync(int nThreads){
//        CountDownLatch latch = new CountDownLatch(1);
//        start(nThreads);
//        try {
//            latch.await();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    boolean cleanHeartBeatTask() {
        return this.heartbeatTask.cancel(true);
    }

    public static void main(String[] args) throws BiliDanmakuClientException {

    }
}
