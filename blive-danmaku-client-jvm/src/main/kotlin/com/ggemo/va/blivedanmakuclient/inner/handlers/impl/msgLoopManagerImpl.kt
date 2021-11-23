package com.ggemo.va.blivedanmakuclient.inner.handlers.impl


import com.ggemo.va.blivedanmakuclient.handler.MsgHandlerItfc
import com.ggemo.va.blivedanmakuclient.inner.util.readStreamUtil
import com.ggemo.va.blivedanmakuclientkt.BLiveDanmakuApplication
import com.ggemo.va.blivedanmakuclientkt.handlers.MsgLoopManager
import java.io.DataInputStream
import java.io.InputStream
import java.net.Socket
import java.util.concurrent.atomic.AtomicBoolean

object msgLoopManagerImpl : MsgLoopManager {
    private val task2CancelMap = HashMap<BLiveDanmakuApplication, AtomicBoolean>()

    override fun cancel(app: BLiveDanmakuApplication) {
        task2CancelMap.getOrPut(app) { AtomicBoolean(true) }.set(true)
    }

    override suspend fun start(socket: Socket, msgHandler: MsgHandlerItfc, app: BLiveDanmakuApplication) {
        println("start!")
        var socketInputStream: InputStream
        val bufferSize = 10 * 1024
        while (socket.getInputStream().also { socketInputStream = it } != null) {
            if (task2CancelMap[app]?.get() == true) {
                return
            }
            val input = DataInputStream(socketInputStream)
            val data = readStreamUtil.readStream(input, bufferSize)
            msgHandler.handleRaw(data)
        }
    }
}