package com.ggemo.va.blivedanmakuclient.util

import com.ggemo.va.blivedanmakuclient.bliveDanmakuModule
import com.ggemo.va.blivedanmakuclientkt.BLiveDanmakuApplication
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin

class BLiveDanmaku {
    fun start() {
        startKoin {
            modules(bliveDanmakuModule)
        }
            BLiveDanmakuApplication().doBusiness(336119)
    }
}

fun main() {
    startKoin {
        modules(bliveDanmakuModule)
    }
    runBlocking {
        BLiveDanmakuApplication().doBusiness(336119)
    }
}