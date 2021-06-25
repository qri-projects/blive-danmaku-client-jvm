package com.ggemo.va.blivedanmakuclientkt.handlers.impl

import com.ggemo.va.blivedanmakuclientkt.handlers.SocketAuthSender
import com.ggemo.va.blivedanmakuclientkt.helper.SocketHelper
import java.net.Socket
import kotlin.random.Random

class SocketAuthSenderImpl: SocketAuthSender {
    override fun send(socket: Socket, roomId: Long, token: String) {
        SocketHelper.sendAuth(socket, (1e14 + 2e14 * Random.nextDouble()).toLong(), roomId, token)
    }
}