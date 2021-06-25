package com.ggemo.va.blivedanmakuclientkt.handlers

import com.ggemo.va.blivedanmakuclientkt.domain.ApplicationContext
import java.net.Socket

interface MsgLoopManager {
    fun start(socket: Socket, context: ApplicationContext)
    fun cancel(context: ApplicationContext)
}