package com.ggemo.va.blivedanmakuclientkt.handlers

import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.DanmakuServerData

interface DanmakuServerIniter {
    fun initDanmakuServer(roomId: Long): DanmakuServerData
}