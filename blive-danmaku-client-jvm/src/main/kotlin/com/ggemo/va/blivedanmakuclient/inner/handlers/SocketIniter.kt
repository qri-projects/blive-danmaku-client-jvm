package com.ggemo.va.blivedanmakuclientkt.handlers

import com.ggemo.va.blivedanmakuclient.inner.entity.DanmakuServerData
import java.net.Socket

interface SocketIniter {
    fun init(roomId: Long, danmakuServerInfo: DanmakuServerData): Socket
}