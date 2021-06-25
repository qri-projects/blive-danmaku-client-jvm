package com.ggemo.va.blivedanmakuclientkt

import com.ggemo.va.blivedanmakuclientkt.domain.ApplicationContext
import com.ggemo.va.blivedanmakuclientkt.handlers.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.locks.ReentrantLock

class BLiveDanmakuApplication : BLiveDanmakuItfc, KoinComponent {
    private val roomIniter by inject<RoomIniter>()
    private val danmakuServerIniter by inject<DanmakuServerIniter>()
    private val socketIniter by inject<SocketIniter>()
    private val socketAuthSender by inject<SocketAuthSender>()
    private val heartBeatLoopManager by inject<HeartBeatLoopManager>()
    private val msgLoopManager by inject<MsgLoopManager>()

    private val lock = ReentrantLock(true)

    override fun buildContext(): ApplicationContext {
        return ApplicationContext(BLiveDanmakuConfig(5000, 1000, 8000, 5000, 1000))
    }

    override fun doBusiness(roomId: Long) = runBlocking {
        val context = buildContext()
        if (!lock.tryLock()) {
            // todo: @sunqihong 给出警告
            return@runBlocking
        }
        try {
            val roomInfo = roomIniter.initRoom(roomId)
            val realRoomId = roomInfo.roomId
            val danmakuServerInfo = danmakuServerIniter.initDanmakuServer(realRoomId)
            val socket = socketIniter.init(realRoomId, danmakuServerInfo)
            socketAuthSender.send(socket, realRoomId, danmakuServerInfo.token)
            coroutineScope {
                launch {
                    heartBeatLoopManager.start(
                        socket,
                        context
                    )
                }
                launch(Dispatchers.IO) {
                    msgLoopManager.start(socket, context)
                }
            }

        } finally {
            lock.unlock()
            cancle(context)
        }
    }

    override fun cancle(context: ApplicationContext) {
        heartBeatLoopManager.cancel(context)
        msgLoopManager.cancel(context)
    }
}