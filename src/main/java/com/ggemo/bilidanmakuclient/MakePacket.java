package com.ggemo.bilidanmakuclient;

import com.alibaba.fastjson.JSON;
import com.ggemo.bilidanmakuclient.copyCS.DanmakuLoader;
import com.ggemo.bilidanmakuclient.copyCS.util.BitConverter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
public class MakePacket {
    private static final short PROTOCOL_VERSION = 1, magic = 16;
    private static final int PARAM = 1;
    private static final String DEFAULT_BODY = "{}";
    private static final byte[] PLAYLOAD_BYTES = DEFAULT_BODY.getBytes(StandardCharsets.UTF_8);

    private static final byte[] VERSION_BYTES = BitConverter.toBe(BitConverter.getBytes(PROTOCOL_VERSION)), PARAM_BYTES = BitConverter.toBe(BitConverter.getBytes(PARAM)), MAGIC_BYTES = BitConverter.toBe(BitConverter.getBytes(magic));

    @Getter
    private static final byte[] HEARTBEAT_PACKET = makePacket(OperationEnum.HEARTBEAT, PLAYLOAD_BYTES);


    public static byte[] makePacket(OperationEnum operation) {
        return makePacket(operation, MAGIC_BYTES, VERSION_BYTES, PARAM_BYTES, PLAYLOAD_BYTES);
    }

    public static byte[] makePacket(OperationEnum operation, Object object) {
        return makePacket(operation, JSON.toJSONString(object));
    }

    public static byte[] makePacket(OperationEnum operation, String body) {
        byte[] playload = body.getBytes(StandardCharsets.UTF_8);
        return makePacket(operation, playload);
    }

    public static byte[] makePacket(OperationEnum operation, byte[] playload) {
        return makePacket(operation, MAGIC_BYTES, VERSION_BYTES, PARAM_BYTES, playload);
    }

    public static byte[] makePacket(OperationEnum operation, short magic, short version, int param, String body) {
        byte[] playload = body.getBytes(StandardCharsets.UTF_8);
        return makePacket(operation, BitConverter.toBe(BitConverter.getBytes(magic)), BitConverter.toBe(BitConverter.getBytes(version)), BitConverter.toBe(BitConverter.getBytes(param)), playload);
    }

    public static byte[] makePacket(OperationEnum operation, byte[] magic, byte[] version,  byte[] param, byte[] playload) {
        int playloadLen = playload.length;
        int packetLen = playloadLen + 16;

        try (ByteArrayOutputStream ms = new ByteArrayOutputStream()) {
            ms.write(BitConverter.toBe(BitConverter.getBytes(packetLen)), 0, 4);

            ms.write(magic, 0, 2);

            ms.write(version, 0, 2);

            ms.write(operation.getByteValue(), 0, 4);

            ms.write(param, 0, 4);

            if (playloadLen > 0) {
                ms.write(playload, 0, playloadLen);
            }
            return ms.toByteArray();
        } catch (IOException e) {
            // 这里信任不会发生IOException
            log.error("construct ByteArrayOutputStream failed: " + e);
            return null;
        }
    }


    public static void main(String[] args) throws IOException {
//        byte[] s = MakePacket.makePacket(7, "{\"roomid\": 13, \"uid\": 123}");
        byte[] s = MakePacket.makePacket(OperationEnum.HEARTBEAT);
        System.out.println(Arrays.toString(s));
    }
}

