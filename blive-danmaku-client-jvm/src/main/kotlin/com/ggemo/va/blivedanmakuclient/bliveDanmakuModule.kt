package com.ggemo.va.blivedanmakuclient

import com.ggemo.va.blivedanmakuclient.handlers.impl.MsgLoopManagerImpl
import com.ggemo.va.blivedanmakuclientkt.handlers.*
import com.ggemo.va.blivedanmakuclientkt.handlers.impl.*
import org.koin.dsl.module
import org.slf4j.LoggerFactory

val bliveDanmakuModule = module {

    single { RoomIniterHttpClientImpl() as RoomIniter }
    single { DanmakuServerIniterHttpClientImpl() as DanmakuServerIniter }
    single { SocketIniterImpl() as SocketIniter }
    single { SocketAuthSenderImpl() as SocketAuthSender }
    single { HeartBeatLoopManagerImpl() as HeartBeatLoopManager }
    single { MsgLoopManagerImpl() as MsgLoopManager}
}