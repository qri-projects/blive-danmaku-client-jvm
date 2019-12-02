package com.ggemo.bilidanmakuclient.improveter;

import com.alibaba.fastjson.JSON;
import com.ggemo.bilidanmakuclient.OperationEnum;
import com.ggemo.bilidanmakuclient.copyCS.util.BitConverter;
import com.ggemo.bilidanmakuclient.structs.SendAuthDO;
import lombok.Getter;
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

    public boolean sendSocketData(int totalLen, int headLen, int protocolVersion, OperationEnum operation, int param, byte[] data) {
        if(socket.isClosed()){
            return false;
        }
        try {
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
                return true;
            }
        } catch (IOException e) {
            log.error(e.toString());
            return false;
        }
    }

    public boolean sendSocketData(OperationEnum operation, int totalLen, byte[] data) {
        return sendSocketData(totalLen, HEAD_LEN, PROTOCOL_VERSION, operation, PARAM, data);
    }

    public boolean sendSocketData( OperationEnum operation, String data) {
        int totalLen = data.length() + 16;
        return sendSocketData(operation, totalLen, data.getBytes(StandardCharsets.UTF_8));
    }

    public boolean sendSocketData(OperationEnum operation, Object obj) {
        return sendSocketData( operation, JSON.toJSONString(obj));
    }

    public boolean sendHeartBeat() {
        return sendSocketData(OperationEnum.HEARTBEAT, 16, null);
    }

    public boolean sendAuth(long uId, long roomId, String token) {
        SendAuthDO sendAuthData = new SendAuthDO(uId, roomId, token);
        return sendSocketData(OperationEnum.AUTH, sendAuthData);
    }
}