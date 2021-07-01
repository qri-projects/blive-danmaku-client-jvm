package com.ggemo.va.blivedanmakuclientkt

import com.ggemo.va.blivedanmakuclient.inner.handlers.impl.msgLoopManagerImpl
import com.ggemo.va.blivedanmakuclientkt.handlers.*
import com.ggemo.va.blivedanmakuclientkt.handlers.impl.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.locks.ReentrantLock

class BLiveDanmakuApplication(override val config: Config) : BLiveDanmakuItfc {
    private val roomIniter:RoomIniter = roomIniterHttpClientImpl
    private val danmakuServerIniter: DanmakuServerIniter = danmakuServerIniterHttpClientImpl
    private val socketIniter: SocketIniter = socketIniterImpl
    private val socketAuthSender: SocketAuthSender = socketAuthSenderImpl
    private val heartBeatLoopManager: HeartBeatLoopManager = heartBeatLoopManagerImpl
    private val msgLoopManager: MsgLoopManager = msgLoopManagerImpl

    private val lock = ReentrantLock(true)

    override fun start() = runBlocking {
        if (!lock.tryLock()) {
            // todo: @sunqihong 给出警告
            return@runBlocking
        }
        try {
            val roomInfo = roomIniter.initRoom(config.roomId)
            val realRoomId = roomInfo.roomId
            val danmakuServerInfo = danmakuServerIniter.initDanmakuServer(realRoomId)
            val socket = socketIniter.init(realRoomId, danmakuServerInfo)
            socketAuthSender.send(socket, realRoomId, danmakuServerInfo.token)
            coroutineScope {
                launch {
                    heartBeatLoopManager.start(
                        socket, config.heartBeatInterval, this@BLiveDanmakuApplication
                    )
                }
                launch(Dispatchers.IO) {
                    msgLoopManager.start(socket, config.handler, this@BLiveDanmakuApplication)
                }
            }
        } finally {
            lock.unlock()
            cancel()
        }
    }

    override fun cancel() {
        heartBeatLoopManager.cancel(this)
        msgLoopManager.cancel(this)
    }
}