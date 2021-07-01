package com.ggemo.va.blivedanmakuclientkt.handlers

import com.ggemo.va.blivedanmakuclient.inner.entity.RoomInitData

interface RoomIniter {
    fun initRoom(roomId: Long): RoomInitData
}