package com.ggemo.va.blivedanmakuclientkt.handlers.impl

import com.ggemo.va.blivedanmakuclientkt.handlers.SocketAuthSender
import com.ggemo.va.blivedanmakuclientkt.helper.socketHelper
import java.net.Socket
import kotlin.random.Random

object socketAuthSenderImpl: SocketAuthSender {
    override fun send(socket: Socket, roomId: Long, token: String) {
        socketHelper.sendAuth(socket, (1e14 + 2e14 * Random.nextDouble()).toLong(), roomId, token)
    }
}