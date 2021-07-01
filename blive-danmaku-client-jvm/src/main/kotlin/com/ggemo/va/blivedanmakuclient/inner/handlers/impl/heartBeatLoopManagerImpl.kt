package com.ggemo.va.blivedanmakuclientkt.handlers.impl

import com.ggemo.va.blivedanmakuclientkt.BLiveDanmakuApplication
import com.ggemo.va.blivedanmakuclientkt.handlers.HeartBeatLoopManager
import com.ggemo.va.blivedanmakuclientkt.helper.SocketHelper
import kotlinx.coroutines.delay
import java.net.Socket
import java.util.concurrent.atomic.AtomicBoolean

object heartBeatLoopManagerImpl : HeartBeatLoopManager {
    private val task2CancelMap = HashMap<BLiveDanmakuApplication, AtomicBoolean>()

    override suspend fun start(socket: Socket, heartBeatInterval: Long, app: BLiveDanmakuApplication) {
        while (true) {
            println("send heartBeat")
            SocketHelper.sendHeartBeat(socket)
            delay(heartBeatInterval)
            if(task2CancelMap[app]?.get() == true) {
                return
            }
        }
    }

    override fun cancel(app: BLiveDanmakuApplication) {
        task2CancelMap.getOrPut(app) { AtomicBoolean(true) }.set(true)
    }
}