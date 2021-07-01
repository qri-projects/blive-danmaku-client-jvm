package com.ggemo.va.blivedanmakuclientkt.handlers

import com.ggemo.va.blivedanmakuclient.inner.entity.DanmakuServerData

interface DanmakuServerIniter {
    fun initDanmakuServer(roomId: Long): DanmakuServerData
}