package com.ggemo.va.bilidanmakuclient

import com.ggemo.va.bililivedanmakuoop.msgdata.*

interface BLiveDanmakuHandler {
    fun danmaku(danmakuData: DanmakuData)

    fun sendGift(sendGiftData: SendGiftData)

    fun superChat(superChatData: SuperChatData)

    fun heartBeat(heartBeatData: HeartBeatData)

    fun guardBuy(guardBuyData: GuardBuyData)

    fun raw(rawString: String)
}