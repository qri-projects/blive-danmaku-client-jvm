package com.ggemo.va.blivedanmakuclientkt.handlers

import java.net.Socket

interface SocketAuthSender {
    fun send(socket: Socket, roomId: Long, token: String)
}