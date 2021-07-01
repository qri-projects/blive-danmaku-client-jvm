package com.ggemo.va.blivedanmakuclientkt.handlers.impl

import com.ggemo.va.bilidanmakuclient.http.request.RoomInitRequest
import com.ggemo.va.blivedanmakuclient.inner.entity.RoomInitData
import com.ggemo.va.blivedanmakuclientkt.handlers.RoomIniter

object roomIniterHttpClientImpl: RoomIniter {
    private val roomInitRequest: RoomInitRequest = RoomInitRequest()

    override fun initRoom(roomId: Long): RoomInitData {
        return roomInitRequest.request(roomId).getD()!!
    }
}