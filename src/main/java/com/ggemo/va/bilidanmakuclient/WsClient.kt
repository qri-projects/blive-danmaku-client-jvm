package com.ggemo.va.bilidanmakuclient

import lombok.extern.slf4j.Slf4j
import java.net.Socket
import kotlin.Throws
import java.io.IOException
import com.ggemo.va.bilidanmakuclient.OperationEnum
import java.io.DataOutputStream
import com.ggemo.va.bilidanmakuclient.WsClient
import com.alibaba.fastjson.JSON
import com.ggemo.va.bilidanmakuclient.structs.SendAuthDO
import java.nio.charset.StandardCharsets

@Slf4j
class WsClient(private val socket: Socket) {
    @Throws(IOException::class)
    fun sendSocketData(
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

    @Throws(IOException::class)
    fun sendSocketData(operation: OperationEnum, totalLen: Int, data: ByteArray?) {
        sendSocketData(totalLen, HEAD_LEN.toInt(), PROTOCOL_VERSION.toInt(), operation, PARAM, data)
    }

    @Throws(IOException::class)
    fun sendSocketData(operation: OperationEnum, data: String) {
        val totalLen = data.length + 16
        sendSocketData(operation, totalLen, data.toByteArray(StandardCharsets.UTF_8))
    }

    @Throws(IOException::class)
    fun sendSocketData(operation: OperationEnum, obj: Any?) {
        sendSocketData(operation, JSON.toJSONString(obj))
    }

    @Throws(IOException::class)
    fun sendHeartBeat() {
        sendSocketData(OperationEnum.HEARTBEAT, 16, null)
    }

    @Throws(IOException::class)
    fun sendAuth(uId: Long, roomId: Long, token: String?) {
        val sendAuthData = SendAuthDO(uId, roomId, token)
        sendSocketData(OperationEnum.AUTH, sendAuthData)
    }

    companion object {
        private const val PROTOCOL_VERSION: Short = 1
        private const val HEAD_LEN: Short = 16
        private const val PARAM = 1
    }
}