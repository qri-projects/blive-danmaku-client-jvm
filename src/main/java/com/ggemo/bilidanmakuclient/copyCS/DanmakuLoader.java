package com.ggemo.bilidanmakuclient.copyCS;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.ggemo.bilidanmakuclient.copyCS.util.BitConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;

@Slf4j
public abstract class DanmakuLoader {

    public abstract void handleReceiveRoomCount(int userCount) throws Exception;

    public abstract void handleReceiveDanku();

    private static String[] DEFAULT_HOSTS = new String[]{"livecmt-2.bilibili.com", "livecmt-1.bilibili.com"};
    private static final String CID_INFO_URL = "https://api.live.bilibili.com/room/v1/Danmu/getConf?room_id=";

    private static final String CHAT_HOST_DEFAULT = "chat.bilibili.com";
    private static final Integer CHAT_PORT_DEFAULT = 2243;

    private Integer chatPort;
    private String chatHost;


    private static final Random RANDOM = new Random();

    private Socket client;
    private InputStream netStream;
    private boolean connected;

    private static final short PROTOCOL_VERSION = 1;
    private Integer lastRoomId;
    private String lastServer;
    private HttpClient httpClient;

    private int roomId;
    private List<HostServer> hostServerList;

    public DanmakuLoader(int roomId) throws IOException {
        initHttpClient(roomId);
        initParam();
    }

    private void initHttpClient(int roomId) {
        this.roomId = roomId;
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        HttpClientBuilder clientbuilder = HttpClients.custom().setConnectionManager(cm);
        clientbuilder.disableCookieManagement();
        this.httpClient = clientbuilder.build();
    }

    private void initParam() {
        connected = false;
        chatHost = CHAT_HOST_DEFAULT;
        chatPort = CHAT_PORT_DEFAULT;
        lastRoomId = null;
    }

    public boolean connect() throws IOException {
        if (this.connected) {
            throw new UnsupportedOperationException();
        }
        if (lastRoomId == null || roomId != lastRoomId) {
            HttpResponse req = httpClient.execute(new HttpGet(CID_INFO_URL + roomId));
            String resStr = EntityUtils.toString(req.getEntity());
            JSONObject data = JSON.parseObject(resStr).getJSONObject("data");
            hostServerList = data.getObject("host_server_list", new TypeReference<List<HostServer>>() {
            });
            chatHost = hostServerList.get(0).host;
//            chatHost = "wss://" + hostServerList.get(0).host;
            chatPort = hostServerList.get(0).wsPort;
            if (chatHost.isEmpty() || chatHost.isBlank() || chatPort == null) {
//                throw new Exception("");
            }
        } else {
            chatHost = lastServer;
        }
        System.out.println(chatHost);
        System.out.println(chatPort + "");
        client = new Socket(chatHost, chatPort);
        netStream = client.getInputStream();
        if (sendJoinChannel(roomId)) {
            connected = true;

            Executors.newSingleThreadExecutor((r) -> {
                Thread t = new Thread(r);
                t.setName("heartBeatThread: ");
                return t;
            }).execute(this::heartbeatLoop);

            Executors.newSingleThreadExecutor(r -> {
                Thread t = new Thread(r);
                t.setName("receiveMessageThread");
                return t;
            }).execute(() -> {
                try {
                    receiveMessageLoop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            lastServer = chatHost;
            lastRoomId = roomId;
            return true;
        }
        return false;
    }

    private void receiveMessageLoop() throws IOException {
        byte[] stableBuffer = new byte[client.getReceiveBufferSize()];
        while (this.connected) {
            netStream.read(stableBuffer, 0, 4);
            int packetLen = BitConverter.toInt(stableBuffer, 0);
            packetLen = networkToHostOrder(packetLen);
            if (packetLen < 16) {
                System.out.println(Arrays.toString(stableBuffer));
                throw new SocketException("协议失败: (L:" + packetLen + ")");
            }

            // magic
            netStream.read(stableBuffer, 0, 2);
            // protocol_version
            netStream.read(stableBuffer, 0, 2);

            netStream.read(stableBuffer, 0, 4);
            var typeId = BitConverter.toInt(stableBuffer, 0);
            typeId = networkToHostOrder(typeId);

            // magic, params?
            netStream.read(stableBuffer, 0, 4);

            int playloadLen = packetLen - 16;
            if (playloadLen == 0) {
                // 没有内容了
                continue;
            }
            // 和反编译的代码对应
            typeId = typeId - 1;
            byte[] buffer = new byte[playloadLen];
            netStream.read(buffer, 0, playloadLen);

            System.out.println("typeId: " + typeId);
            byte[] bb = Arrays.copyOf(buffer, playloadLen);
            String json = new String(bb, StandardCharsets.UTF_8);
            System.out.println("String: \"" + json + "\"");

            switch (typeId) {
                case 0:
                case 1:
                case 2: {
                    bb = new byte[4];
                    for (int i = 0; i < 4; i++) {
                        bb[i] = buffer[3 - i];
                    }
                    int viewer = BitConverter.toInt(bb, 0);
                    try {
                        handleReceiveRoomCount(viewer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }

                // playerCommand
                case 3:
                case 4: {
                    break;
                }
                default: {
                    break;
                }

            }
        }
    }

    private static int networkToHostOrder(int x) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[4]);
        bb.asIntBuffer().put(x);
        return BitConverter.toInt(bb.array());
    }

    private boolean sendJoinChannel(int channelId) {
        long tmpUid = (long) (1e14 + 2e14 * RANDOM.nextDouble());
        String playload = "{\"roomid\":" + channelId + " , \"uid\":" + tmpUid + " }";
        try {
            makePacket(makePacket(7, playload));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void heartbeatLoop() {
        while (this.connected) {
            try {
                this.sendHeartbeat();
            } catch (IOException e) {
                e.printStackTrace();
                disConnect();
            }
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendHeartbeat() throws IOException {
        makePacket(makePacket(2, ""));
    }

    private void makePacket(ByteArrayOutputStream stream) throws IOException {
        client.getOutputStream().write(stream.toByteArray());
    }

    private static ByteArrayOutputStream makePacket(int action, String body) throws IOException {
        return makePacket(0, (short) 16, PROTOCOL_VERSION, action, 1, body);
    }

    private static ByteArrayOutputStream makePacket(int packetlength, short magic, short ver, int action, int param, String body) throws IOException {
        byte[] playload = body.getBytes(StandardCharsets.UTF_8);

        if (packetlength == 0) {
            packetlength = playload.length + 16;
        }

        byte[] buffer = new byte[packetlength];
        try (ByteArrayOutputStream ms = new ByteArrayOutputStream()) {
            byte[] b = BitConverter.toBe(BitConverter.getBytes(buffer.length));
            ms.write(b, 0, 4);

            b = BitConverter.toBe(BitConverter.getBytes(magic));
            ms.write(b, 0, 2);

            b = BitConverter.toBe(BitConverter.getBytes(ver));
            ms.write(b, 0, 2);

            b = BitConverter.toBe(BitConverter.getBytes(action));
            ms.write(b, 0, 4);

            b = BitConverter.toBe(BitConverter.getBytes(param));
            ms.write(b, 0, 4);

            if (playload.length > 0) {
                ms.write(playload, 0, playload.length);
            }
            return ms;
        }
    }


    private void disConnect() {
        if (connected) {
            log.info("Disconnected");

            connected = false;

            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            netStream = null;
        }

    }

    public static void main(String[] args) throws IOException {
        System.out.println(1);
        ByteArrayOutputStream s = DanmakuLoader.makePacket(7, "{\"roomid\":" + "13" + " , \"uid\":" + "123" + " }");
        System.out.println(Arrays.toString(s.toByteArray()));

//        new DanmakuLoader(493) {
//            @Override
//            public void handleReceiveRoomCount(int userCount) throws Exception {
//                System.out.println("观众人数" + userCount);
//            }
//
//            @Override
//            public void handleReceiveDanku() {
//
//            }
//        }.connect();
    }
}
