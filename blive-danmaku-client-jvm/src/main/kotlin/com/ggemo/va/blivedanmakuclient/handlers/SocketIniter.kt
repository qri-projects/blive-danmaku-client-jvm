package com.ggemo.va.blivedanmakuclientkt.handlers

import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.DanmakuServerData
import java.net.Socket

interface SocketIniter {
    fun init(roomId: Long, danmakuServerInfo: DanmakuServerData): Socket
}