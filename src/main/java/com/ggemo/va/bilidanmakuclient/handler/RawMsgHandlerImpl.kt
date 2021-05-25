package com.ggemo.va.bilidanmakuclient.handler

import com.alibaba.fastjson.JSON
import com.ggemo.va.bilidanmakuclient.BLiveDanmakuConfig
import com.ggemo.va.bilidanmakuclient.structs.Cmd
import com.ggemo.va.bililivedanmakuoop.msgdata.*

class RawMsgHandlerImpl(bLiveDanmakuConfig: BLiveDanmakuConfig) : RawMsgHandler {
    val outHandler = bLiveDanmakuConfig.bLiveDanmakuHandler
    val roomId = bLiveDanmakuConfig.roomId
    val handleRaw = bLiveDanmakuConfig.handleRaw

    override fun handleHeartBeat(userCount: Int) {
        val data = HeartBeatData(userCount)
        data.roomId = roomId
        outHandler.heartBeat(data)
    }

    override fun handleCmdMsg(cmdMsg: String) {
        if (handleRaw) {
            outHandler.raw(cmdMsg)
            return
        }

        val jsonObject = JSON.parseObject(cmdMsg)

        when (jsonObject.getString("cmd")) {
            Cmd.DANMAKU.rawValue -> {
                val danmakuData = DanmakuData.fromJSON(jsonObject.getJSONArray("info"))
                danmakuData.roomId = roomId
                outHandler.danmaku(danmakuData)
            }

            Cmd.SEND_GIFT.rawValue -> {
                val sendGiftData = SendGiftData.fromJSON(jsonObject.getJSONObject("data"))
                sendGiftData.roomId = roomId
                outHandler.sendGift(sendGiftData)
            }
            Cmd.SUPER_CHAT.rawValue -> {
                val superChatData = SuperChatData.fromJSON(jsonObject.getJSONObject("data"))
                superChatData.roomId = roomId
                outHandler.superChat(superChatData)
            }
            Cmd.GUARD_BUY.rawValue -> {
                val guardBuyData = GuardBuyData.fromJSON(jsonObject.getJSONObject("data"))
                guardBuyData.roomId = roomId
            }
            else ->
                outHandler.raw(cmdMsg)
        }
    }


}