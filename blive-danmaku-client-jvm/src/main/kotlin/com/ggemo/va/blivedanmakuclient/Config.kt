package com.ggemo.va.blivedanmakuclientkt

import com.ggemo.va.blivedanmakuclient.handler.MsgHandlerItfc

data class Config(
    val roomId: Long,
    val handler: MsgHandlerItfc,
    // 发送心跳的间隔
    val heartBeatInterval: Long = 2000
)
