package com.ggemo.va.blivedanmakuclientkt.handlers

import com.ggemo.va.blivedanmakuclientkt.domain.SocketWrapper

interface SocketHeartBeatSender {
    fun send(socket: SocketWrapper)
}