package com.ggemo.va.blivedanmakuclientkt.domain

interface SocketWrapper {

    fun sendHeartBeat()

    fun sendAuth(uId: Long, roomId: Long, token: String)
}