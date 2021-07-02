package com.ggemo.va.blivedanmakuclient.handler.given

import com.ggemo.va.blivedanmakuclient.handler.MsgHandler
import com.ggemo.va.blivedanmakuclient.handler.given.msgdata.BLiveDanmakuMsgData
import com.ggemo.va.blivedanmakuclient.inner.util.byteArray2IntUtil

abstract class GivenMsgHandler: MsgHandler() {
    open suspend fun userCount(userCount: Int) {}

    open suspend fun danmaku(danmaku: BLiveDanmakuMsgData.Danmaku) {}

    open suspend fun sendGift(sendGift: BLiveDanmakuMsgData.SendGift) {}

    open suspend fun guardBuy(guardBuy: BLiveDanmakuMsgData.GuardBuy) {}

    open suspend fun superChat(superChat: BLiveDanmakuMsgData.SuperChat) {}

    init {
        registerActionHandler(2) { _, data ->
            val userCount = byteArray2IntUtil.byteArray2Int32(data)
            this.userCount(userCount)
        }

        registerCmdStringHandler(BLiveDanmakuMsgData.Cmds.DANMAKU.rawValue) {
            this.danmaku(BLiveDanmakuMsgData.Danmaku.fromJSON(it.getJSONArray("info")))
        }

        registerCmdStringHandler(BLiveDanmakuMsgData.Cmds.SEND_GIFT.rawValue) {
            this.sendGift(BLiveDanmakuMsgData.SendGift.fromJSON(it.getJSONObject("data")))
        }

        registerCmdStringHandler(BLiveDanmakuMsgData.Cmds.GUARD_BUY.rawValue) {
            this.guardBuy(BLiveDanmakuMsgData.GuardBuy.fromJSON(it.getJSONObject("data")))
        }

        registerCmdStringHandler(BLiveDanmakuMsgData.Cmds.SUPER_CHAT.rawValue) {
            this.superChat(BLiveDanmakuMsgData.SuperChat.fromJSON(it.getJSONObject("data")))
        }
    }
}