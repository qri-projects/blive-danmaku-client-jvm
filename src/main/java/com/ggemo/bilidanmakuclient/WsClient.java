package com.ggemo.bilidanmakuclient;

import com.alibaba.fastjson.JSON;
import com.ggemo.bilidanmakuclient.structs.SendAuthDO;
import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
public class WsClient {
    private static final short PROTOCOL_VERSION = 1, HEAD_LEN = 16;
    private static final int PARAM = 1;

    private final Socket socket;

    public WsClient(Socket socket) {
        this.socket = socket;
    }

    public void sendSocketData(int totalLen, int headLen, int protocolVersion, OperationEnum operation, int param, byte[] data) throws IOException {
        if (socket.isClosed()) {
            throw new IOException("socket closed");
        }
//        try {
        synchronized (socket) {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeInt(totalLen);
            out.writeShort(headLen);
            out.writeShort(protocolVersion);
            out.writeInt(operation.getValue());
            out.writeInt(param);
            if (data != null && data.length > 0) {
                out.write(data);
            }
            out.flush();
        }
//        } catch (IOException e) {
//            log.error(e.toString());
//            return false;
//        }
    }

    public void sendSocketData(OperationEnum operation, int totalLen, byte[] data) throws IOException {
        sendSocketData(totalLen, HEAD_LEN, PROTOCOL_VERSION, operation, PARAM, data);
    }

    public void sendSocketData(OperationEnum operation, String data) throws IOException {
        int totalLen = data.length() + 16;
        sendSocketData(operation, totalLen, data.getBytes(StandardCharsets.UTF_8));
    }

    public void sendSocketData(OperationEnum operation, Object obj) throws IOException {
        sendSocketData(operation, JSON.toJSONString(obj));
    }

    public void sendHeartBeat() throws IOException {
        sendSocketData(OperationEnum.HEARTBEAT, 16, null);
    }

    public void sendAuth(long uId, long roomId, String token) throws IOException {
        SendAuthDO sendAuthData = new SendAuthDO(uId, roomId, token);
        sendSocketData(OperationEnum.AUTH, sendAuthData);
    }
}