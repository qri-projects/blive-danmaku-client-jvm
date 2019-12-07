package com.ggemo.bilidanmakuclient;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import com.ggemo.bilidanmakuclient.handler.HandlerHolder;

@Slf4j
public class HandleDataLoop {
    private Socket socket;
    private long roomId;

    private HandlerHolder handlerHolder;

    public HandleDataLoop(Socket socket, long roomId, HandlerHolder handlerHolder) {
        this.socket = socket;
        this.roomId = roomId;
        this.handlerHolder = handlerHolder;
    }

    public void start() throws IOException {
        if (socket != null) {
            int bufferSize = 10 * 1024;
            try {
                bufferSize = socket.getReceiveBufferSize();
                log.info("连接成功" + "真实直播间ID：" + roomId);
            } catch (SocketException e) {
                log.error(e.toString());
            }
            byte[] ret = new byte[bufferSize];
//            try {
            while (true) {
                DataInputStream input = new DataInputStream(socket.getInputStream());
                int retLength = input.read(ret);
                if (retLength > 0) {
                    byte[] recvData = new byte[retLength];
                    System.arraycopy(ret, 0, recvData, 0, retLength);
                    analyzeData(recvData);
                }
            }
        }
    }

    private void analyzeData(byte[] data) {

        int dataLength = data.length;
        if (dataLength < 16) {
            log.info("错误的数据");
        } else {
            DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(data));
            try {
                int msgLength = inputStream.readInt();
                if (msgLength < 16) {
                    log.info("可能需要扩大缓冲区大小");
                } else if (msgLength > 16 && msgLength == dataLength) {

                    // 其实是两个char
                    inputStream.readInt();

                    int action = inputStream.readInt() - 1;

                    // 直播间在线用户数目
                    if (action == 2) {
                        inputStream.readInt();
                        int userCount = inputStream.readInt();
                        handlerHolder.handleUserCount(userCount);
                    } else if (action == 4) {
                        inputStream.readInt();
                        int msgBodyLength = dataLength - 16;
                        byte[] msgBody = new byte[msgBodyLength];
                        if (inputStream.read(msgBody) == msgBodyLength) {
                            String jsonStr = new String(msgBody, StandardCharsets.UTF_8);
                            handlerHolder.handleCmd(jsonStr);
                        }
                    }
                } else if (msgLength > 16 && msgLength < dataLength) {
                    byte[] singleData = new byte[msgLength];
                    System.arraycopy(data, 0, singleData, 0, msgLength);
                    analyzeData(singleData);
                    int remainLen = dataLength - msgLength;
                    byte[] remainDate = new byte[remainLen];
                    System.arraycopy(data, msgLength, remainDate, 0, remainLen);
                    analyzeData(remainDate);
                }
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
    }
}
