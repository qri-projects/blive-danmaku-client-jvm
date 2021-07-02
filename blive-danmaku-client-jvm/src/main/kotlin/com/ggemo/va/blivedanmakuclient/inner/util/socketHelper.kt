package com.ggemo.va.blivedanmakuclientkt.helper

import com.alibaba.fastjson.JSON
import com.ggemo.va.blivedanmakuclientkt.dto.OperationEnum
import com.ggemo.va.blivedanmakuclientkt.dto.SendAuthDO
import java.io.DataOutputStream
import java.io.IOException
import java.net.Socket
import java.nio.charset.StandardCharsets

object socketHelper {
    private const val PROTOCOL_VERSION: Short = 1
    private const val HEAD_LEN: Short = 16
    private const val PARAM = 1

    fun sendSocketData(
        socket: Socket,
        totalLen: Int,
        headLen: Int,
        protocolVersion: Int,
        operation: OperationEnum,
        param: Int,
        data: ByteArray?
    ) {
        if (socket.isClosed) {
            throw IOException("socket closed")
        }
        //        try {
        synchronized(socket) {
            val out = DataOutputStream(socket.getOutputStream())
            out.writeInt(totalLen)
            out.writeShort(headLen)
            out.writeShort(protocolVersion)
            out.writeInt(operation.value)
            out.writeInt(param)
            if (data != null && data.size > 0) {
                out.write(data)
            }
            out.flush()
        }
        //        } catch (IOException e) {
//            log.error(e.toString());
//            return false;
//        }
    }

    fun sendSocketData(socket: Socket, operation: OperationEnum, totalLen: Int, data: ByteArray?) {
        sendSocketData(socket, totalLen, HEAD_LEN.toInt(), PROTOCOL_VERSION.toInt(), operation, PARAM, data)
    }

    fun sendSocketData(socket: Socket, operation: OperationEnum, data: String) {
        val totalLen = data.length + 16
        sendSocketData(socket, operation, totalLen, data.toByteArray(StandardCharsets.UTF_8))
    }

    fun sendSocketData(socket: Socket, operation: OperationEnum, obj: Any?) {
        sendSocketData(socket, operation, JSON.toJSONString(obj))
    }

    fun sendHeartBeat(socket: Socket) {
        sendSocketData(socket, OperationEnum.HEARTBEAT, 16, null)
    }

    fun sendAuth(socket: Socket, uId: Long, roomId: Long, token: String) {
        val sendAuthData = SendAuthDO(uId, roomId, token)
        sendSocketData(socket, OperationEnum.AUTH, sendAuthData)
    }
}