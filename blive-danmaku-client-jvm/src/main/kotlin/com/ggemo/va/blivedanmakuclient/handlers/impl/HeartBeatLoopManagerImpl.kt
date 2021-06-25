package com.ggemo.va.blivedanmakuclientkt.handlers.impl

import com.ggemo.va.blivedanmakuclient.domain.BLiveDanmakuTask
import com.ggemo.va.blivedanmakuclientkt.domain.ApplicationContext
import com.ggemo.va.blivedanmakuclientkt.handlers.HeartBeatLoopManager
import com.ggemo.va.blivedanmakuclientkt.helper.SocketHelper
import kotlinx.coroutines.delay
import java.io.IOException
import java.net.Socket
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

class HeartBeatLoopManagerImpl : HeartBeatLoopManager {
    private val task2CancelMap = HashMap<ApplicationContext, AtomicBoolean>()

    override suspend fun start(socket: Socket, context: ApplicationContext) {
        while (true) {
            println("send heartBeat")
            SocketHelper.sendHeartBeat(socket)
            context.lastSendHeartBeatSuccessTime.set(System.currentTimeMillis())
            delay(context.config.heartBeatInterval)
            if(task2CancelMap[context]?.get() == true) {
                return
            }
        }
    }

    override fun cancel(context: ApplicationContext) {
        task2CancelMap.getOrPut(context) { AtomicBoolean(true) }.set(true)
    }
}