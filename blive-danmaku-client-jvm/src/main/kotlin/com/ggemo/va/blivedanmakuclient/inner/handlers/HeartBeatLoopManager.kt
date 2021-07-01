package com.ggemo.va.blivedanmakuclientkt.handlers

import com.ggemo.va.blivedanmakuclientkt.BLiveDanmakuApplication
import java.net.Socket

interface HeartBeatLoopManager {
    suspend fun start(socket: Socket, heartBeatInterval: Long, app: BLiveDanmakuApplication)
    fun cancel(app: BLiveDanmakuApplication)
}