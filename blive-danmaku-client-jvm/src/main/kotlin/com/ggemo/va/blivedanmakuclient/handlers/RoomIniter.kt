package com.ggemo.va.blivedanmakuclientkt.handlers

import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.RoomInitData

interface RoomIniter {
    fun initRoom(roomId: Long): RoomInitData
}