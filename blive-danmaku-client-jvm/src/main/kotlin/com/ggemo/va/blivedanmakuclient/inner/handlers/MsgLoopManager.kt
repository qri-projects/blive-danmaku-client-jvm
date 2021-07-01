package com.ggemo.va.blivedanmakuclientkt.handlers

import com.ggemo.va.blivedanmakuclient.handler.MsgHandlerItfc
import com.ggemo.va.blivedanmakuclientkt.BLiveDanmakuApplication
import java.net.Socket

interface MsgLoopManager {
    suspend fun start(socket: Socket, msgHandler: MsgHandlerItfc, app: BLiveDanmakuApplication)
    fun cancel(app: BLiveDanmakuApplication)
}