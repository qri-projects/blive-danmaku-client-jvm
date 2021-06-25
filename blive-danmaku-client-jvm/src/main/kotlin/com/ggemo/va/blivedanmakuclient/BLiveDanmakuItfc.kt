package com.ggemo.va.blivedanmakuclientkt

import com.ggemo.va.blivedanmakuclientkt.domain.ApplicationContext
import com.ggemo.va.blivedanmakuclientkt.handlers.*


interface BLiveDanmakuItfc {
    fun buildContext(): ApplicationContext

    fun doBusiness(roomId: Long)

    fun cancle(context: ApplicationContext)
}