package com.ggemo.va.blivedanmakuclientkt.domain

import com.ggemo.va.blivedanmakuclientkt.BLiveDanmakuConfig
import java.net.Socket
import java.util.concurrent.atomic.AtomicLong

data class ApplicationContext(
    val config: BLiveDanmakuConfig,
) {
    val lastSendHeartBeatSuccessTime: AtomicLong = AtomicLong()
    val lastReceiveBeatSuccessTime: AtomicLong = AtomicLong()
    var socket: Socket? = null
}