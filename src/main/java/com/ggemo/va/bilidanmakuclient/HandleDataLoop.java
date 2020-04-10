package com.ggemo.va.bilidanmakuclient;

import com.ggemo.va.bilidanmakuclient.handler.HandlerHolder;
import com.ggemo.va.bilidanmakuclient.structs.DanmakuProtocol;
import com.ggemo.va.bilidanmakuclient.utill.EndianBitConverter;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;
import java.util.zip.Inflater;

@Slf4j
public class HandleDataLoop {
    private static final int PROTOCOL_LENGTH = 16;
    private static final int INIT_BUFFER_SIZE = 4096;
    private Socket socket;
    private long roomId;
    private AtomicLong receivedHeartBeatTime;

    private HandlerHolder handlerHolder;

    private byte[] protocolBuffer;
    private byte[] buffer;

    public HandleDataLoop(Socket socket, long roomId, HandlerHolder handlerHolder, AtomicLong receivedHeartBeatTime) {
        this.socket = socket;
        this.roomId = roomId;
        this.handlerHolder = handlerHolder;
        this.receivedHeartBeatTime = receivedHeartBeatTime;
        this.protocolBuffer = new byte[PROTOCOL_LENGTH];
    }

    public void start() throws IOException, DataFormatException {
        if (socket != null) {
            int bufferSize = INIT_BUFFER_SIZE;
            bufferSize = socket.getReceiveBufferSize();
            log.info("connect successed. real roomId：" + roomId);
            this.buffer = new byte[bufferSize];
//            try {
            while (true) {
                DataInputStream input = new DataInputStream(socket.getInputStream());
                handleMsg(input);
            }
        }
    }

    public void handleMsg(DataInputStream input) throws IOException, DataFormatException {
        input.read(protocolBuffer, 0, PROTOCOL_LENGTH);
        DanmakuProtocol protocol = new DanmakuProtocol(protocolBuffer);
        var packageLength = protocol.getPacketLength();
        if (packageLength < 16) {
            log.warn("protocol fail: " + packageLength);
            return;
        }

        var dataLen = packageLength - PROTOCOL_LENGTH;
        if (dataLen == 0) {
            return;
        }
        if (dataLen > this.buffer.length) {
            // 不够长再申请
            buffer = new byte[dataLen];
        }
        input.read(buffer, 0, dataLen);
        if (protocol.version == 2 && protocol.action == 5) {
            handleDeflateMsg(buffer, dataLen);
        } else {
            handleSingleMsg(protocol.action, buffer);
        }
    }

    public void handleDeflateMsg(byte[] data, int dataLen) throws IOException, DataFormatException {
        var protocolBuffer = new byte[PROTOCOL_LENGTH];
        Inflater decompresser = new Inflater();
        decompresser.setInput(data);

        while (!decompresser.finished()) {
            decompresser.inflate(protocolBuffer, 0, PROTOCOL_LENGTH);
            DanmakuProtocol protocolIn;
            try {
                protocolIn = new DanmakuProtocol(protocolBuffer);

//              var innerDataLen = protocolIn.getPacketLength() - 16;
                int innerDataLen = protocolIn.getPacketLength() - PROTOCOL_LENGTH;
                if(innerDataLen < 0){
                    innerDataLen = data.length - 16;
                }

                byte[] danmakuBuffer = new byte[innerDataLen];

                decompresser.inflate(danmakuBuffer, 0, innerDataLen);
                handleSingleMsg(protocolIn.getAction(), danmakuBuffer);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    public void handleSingleMsg(int action, byte[] data) {
        if (action == 3) {
            // 直播间在线用户数目
            this.receivedHeartBeatTime.set(System.currentTimeMillis());
            int userCount = EndianBitConverter.bigEndianBytesToInt32(data, 0);
            handlerHolder.handleUserCount(userCount);
        } else if (action == 5) {
            // CMD
            var jsonStr = new String(data, StandardCharsets.UTF_8);
            handlerHolder.handleCmd(jsonStr);
        }
    }

    public static void main(String[] args) throws IOException, DataFormatException {
        var s = "{\"cmd\":\"DANMU_MSG\",\"info\":[[0,1,25,16777215,1586554760335,1586552546,0,\"63e97abb\",0,0,0],\"10\",[13578650,\"完美潇洒的小黄瓜\",0,0,0,10000,1,\"\"],[12,\"沙月\",\"沙月ちゃん\",4767523,10512625,\"\",0],[25,0,5805790,\"\\u003e50000\"],[\"\",\"\"],0,0,null,{\"ts\":1586554760,\"ct\":\"2E123A33\"},0,0,null,null,0]}";
        Deflater deflater = new Deflater();
        deflater.setInput(s.getBytes(StandardCharsets.UTF_8));
        deflater.deflate(s.getBytes(StandardCharsets.UTF_8));
        var in = deflater.getTotalIn();
        var out = deflater.getTotalOut();
        System.out.println(in);
        System.out.println(out);

        var res = EndianBitConverter.bigEndianBytesToInt16(new byte[]{0, 0, 3, -67}, 2);
        System.out.println(res);
    }
}
