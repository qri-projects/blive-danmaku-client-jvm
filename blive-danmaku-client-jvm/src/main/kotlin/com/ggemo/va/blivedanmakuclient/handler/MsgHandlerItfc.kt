package com.ggemo.va.blivedanmakuclient.handler

interface MsgHandlerItfc {
    suspend fun handleRaw(data: ByteArray)
}