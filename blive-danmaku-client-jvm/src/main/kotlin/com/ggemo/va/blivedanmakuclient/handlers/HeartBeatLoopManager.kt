package com.ggemo.va.blivedanmakuclientkt.handlers

import com.ggemo.va.blivedanmakuclientkt.domain.ApplicationContext
import com.ggemo.va.blivedanmakuclientkt.domain.SocketWrapper
import java.net.Socket
import java.util.concurrent.atomic.AtomicLong

interface HeartBeatLoopManager {
    suspend fun start(socket: Socket, context: ApplicationContext)
    fun cancel(context: ApplicationContext)
}