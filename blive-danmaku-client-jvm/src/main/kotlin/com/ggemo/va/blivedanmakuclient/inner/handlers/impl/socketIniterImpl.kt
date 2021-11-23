package com.ggemo.va.blivedanmakuclientkt.handlers.impl

import com.ggemo.va.blivedanmakuclient.inner.entity.DanmakuServerData
import com.ggemo.va.blivedanmakuclientkt.handlers.SocketIniter
import java.net.InetSocketAddress
import java.net.Socket

object socketIniterImpl : SocketIniter {
    private const val RECEIVE_BUFFER_SIZE = 10 * 1024

    override fun init(roomId: Long, danmakuServerInfo: DanmakuServerData): Socket {

        val hostServerList = danmakuServerInfo.hostServerList!!
        // for循环用来从后往前选择hostServer
        for (i in hostServerList.indices) {
            val hostServerNo = hostServerList.size - 1 - i
            val hostServer = hostServerList[hostServerNo]
            val address = InetSocketAddress(hostServer.host, hostServer.port)
            val socket = Socket()
            socket.receiveBufferSize = RECEIVE_BUFFER_SIZE
            socket.connect(address)
            return socket
        }
        throw RuntimeException("no danmaku server can reach")
    }
}