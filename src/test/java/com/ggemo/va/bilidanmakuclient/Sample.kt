package com.ggemo.va.bilidanmakuclient

import com.ggemo.va.bilidanmakuclient.BLiveDanmakuHandler
import com.ggemo.va.bililivedanmakuoop.msgdata.GuardBuyData
import com.ggemo.va.bililivedanmakuoop.msgdata.HeartBeatData
import com.ggemo.va.bililivedanmakuoop.msgdata.SuperChatData
import com.ggemo.va.bililivedanmakuoop.msgdata.SendGiftData
import com.ggemo.va.bililivedanmakuoop.msgdata.DanmakuData
import com.ggemo.va.bilidanmakuclient.BiliLiveDanmakuClient
import com.ggemo.va.bilidanmakuclient.BLiveDanmakuConfig
import org.junit.Test

class Sample {
    @Test
    fun simplePrintTest() {
        val roomId = 336119L
        val bLiveDanmakuHandler: BLiveDanmakuHandler = object : BLiveDanmakuHandler {
            override fun raw(rawString: String) {
                println(rawString)
            }
            override fun guardBuy(guardBuyData: GuardBuyData) {}
            override fun heartBeat(heartBeatData: HeartBeatData) {}
            override fun superChat(superChatData: SuperChatData) {}
            override fun sendGift(sendGiftData: SendGiftData) {}
            override fun danmaku(danmakuData: DanmakuData) {}
        }

        // 创建BiliLiveDanmakuClient对象,使用房间号和HandlerHolder作为实例化参数
        val client = BiliLiveDanmakuClient(
            BLiveDanmakuConfig(
                roomId, bLiveDanmakuHandler, true, null
            )
        )

        // 开始监听(这个start是个同步操作)
        client.start()
    }

    @Test
    fun oopPrintTest() {
        val roomId = 336119L
        val bLiveDanmakuHandler: BLiveDanmakuHandler = object : BLiveDanmakuHandler {
            override fun raw(rawString: String) {}
            override fun guardBuy(guardBuyData: GuardBuyData) {}
            override fun heartBeat(heartBeatData: HeartBeatData) {
                println(heartBeatData.userCount)
            }

            override fun superChat(superChatData: SuperChatData) {}
            override fun sendGift(sendGiftData: SendGiftData) {}
            override fun danmaku(danmakuData: DanmakuData) {
                println("${danmakuData.userInfo?.userName ?: "name"}: ${danmakuData.content}")
            }
        }

        // 创建BiliLiveDanmakuClient对象,使用房间号和HandlerHolder作为实例化参数
        val client = BiliLiveDanmakuClient(
            BLiveDanmakuConfig(
                roomId, bLiveDanmakuHandler, false, null
            )
        )

        // 开始监听(这个start是个同步操作)
        client.start()
    }
}