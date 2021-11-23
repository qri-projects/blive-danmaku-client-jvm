package com.ggemo.va.blivedanmakuclient.inner.util

import com.alibaba.fastjson.JSON
import com.ggemo.va.blivedanmakuclient.handler.given.GivenMsgHandler
import com.ggemo.va.blivedanmakuclient.handler.given.msgdata.BLiveDanmakuMsgData
import com.ggemo.va.blivedanmakuclientkt.BLiveDanmakuApplication
import com.ggemo.va.blivedanmakuclientkt.Config

fun main() {
        BLiveDanmakuApplication(
            Config(
                4767523,
                object : GivenMsgHandler() {
                    override suspend fun userCount(userCount: Int) {
                        println("气人值: $userCount")
                    }

                    override suspend fun danmaku(danmaku: BLiveDanmakuMsgData.Danmaku) {
                        println(JSON.toJSONString(danmaku))
                    }
                }
            )
        ).start()
}




