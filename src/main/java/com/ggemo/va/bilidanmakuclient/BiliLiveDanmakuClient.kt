package com.ggemo.va.bilidanmakuclient

import com.ggemo.va.bilidanmakuclient.exception.BiliDanmakuClientException
import com.ggemo.va.bilidanmakuclient.handler.RawMsgHandler
import com.ggemo.va.bilidanmakuclient.handler.RawMsgHandlerImpl
import com.ggemo.va.bilidanmakuclient.http.exception.BiliClientException
import com.ggemo.va.bilidanmakuclient.http.request.DanmakuServerConfRequest
import com.ggemo.va.bilidanmakuclient.http.request.RoomInitRequest
import com.ggemo.va.bilidanmakuclient.http.response.impl.DanmakuServerConfResponse
import com.ggemo.va.bilidanmakuclient.http.response.impl.RoomInitResponse
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.DanmakuServerConfResponseData
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.DanmakuServerConfResponseData.HostServerInfo
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.RoomInitResponseData
import com.ggemo.va.bilidanmakuclient.util.ThreadUtil
import lombok.extern.slf4j.Slf4j
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.util.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong

@Slf4j
class BiliLiveDanmakuClient(bLiveDanmakuConfig: BLiveDanmakuConfig) {
    private val tmpRoomId: Long
    private var uId: Long
    private var roomId: Long = 0
    private var hostServerList: List<HostServerInfo>? = null
    private var hostServerToken: String?
    private val roomInitRequest: RoomInitRequest
    private val danmakuServerConfRequest: DanmakuServerConfRequest
    private val heartbeatThreadPool: ScheduledExecutorService
    private var heartbeatTask: ScheduledFuture<*>?
    private var hdpTask: CompletableFuture<Boolean>?
    private val rawMsgHandler: RawMsgHandler
    private val sendedHeartBeatSuccessedTime: AtomicLong
    private val receivedHeartBeatSuccessedTime: AtomicLong
    private var socket: Socket? = null
    private lateinit var wsClient: WsClient
    private fun parseRoomInit(data: RoomInitResponseData) {
        roomId = data.roomId
    }

    private fun parseServerConf(data: DanmakuServerConfResponseData) {
        hostServerList = data.hostServerList
        hostServerToken = data.token
    }

    @Throws(IOException::class, BiliClientException::class, BiliDanmakuClientException::class)
    private fun initRoom() {
        val roomInitResponse: RoomInitResponse
        roomInitResponse = roomInitRequest.request(tmpRoomId)
        val roomInitResponseData: RoomInitResponseData
        roomInitResponseData = roomInitResponse.getD()!!
        parseRoomInit(roomInitResponseData)
        val danmakuServerConfResponse: DanmakuServerConfResponse
        danmakuServerConfResponse = danmakuServerConfRequest.request(tmpRoomId)
        val danmakuServerConfResponseData: DanmakuServerConfResponseData
        danmakuServerConfResponseData = danmakuServerConfResponse.getD()!!
        parseServerConf(danmakuServerConfResponseData)
        if (hostServerList == null || hostServerList!!.isEmpty()) {
            throw BiliDanmakuClientException("initRoom error, hostServerList为null")
        }
    }

    private fun connect(): Socket? {
        if (hostServerToken == null) {
            while (true) {
                try {
                    initRoom()
                    break
                } catch (ignored: IOException) {
                } catch (ignored: BiliClientException) {
                } catch (ignored: BiliDanmakuClientException) {
                }
            }
        }
        socket = null

        // for循环用来从后往前选择hostServer
        for (i in hostServerList!!.indices) {
            val hostServerNo = hostServerList!!.size - 1 - i
            val hostServer = hostServerList!![hostServerNo]
            val address = InetSocketAddress(hostServer.host, hostServer.port)
            socket = Socket()
            try {
                socket!!.receiveBufferSize = RECEIVE_BUFFER_SIZE
                socket!!.connect(address)
                wsClient = WsClient(socket!!)
                wsClient.sendAuth(uId, roomId, hostServerToken)
                heartbeatTask = heartbeatThreadPool.scheduleAtFixedRate({
                    ThreadUtil.setThreadName("heart_beat_thread_" + roomId + "_" + START_COUNT)
                    try {
                        wsClient.sendHeartBeat()
                        sendedHeartBeatSuccessedTime.set(System.currentTimeMillis())
                    } catch (e: IOException) {
                        println(e.toString())
                        cleanHeartBeatTask()
                    }
                }, 2, HEARTBEAT_INTERVAL.toLong(), TimeUnit.SECONDS)
                return socket
            } catch (e: IOException) {
                println("error about heartbeatTask $e")
                cleanHeartBeatTask()
                try {
                    socket!!.close()
                    socket = null
                } catch (ignored: IOException) {
                }
            }
        }
        return null
    }

    /**
     * sync
     */
    fun start() {
        START_COUNT += 1
        val threadName = String.format("room_thread_%d_%d", roomId, START_COUNT)
        ThreadUtil.setThreadName(threadName)

        // 重连循环
        while (true) {
            var socket: Socket? = null
            socket = connect()
            if (socket == null || socket.isClosed) {
                try {
                    Thread.sleep(RECONNECT_IDLE.toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                continue
            }
            val hdp = HandleDataLoop(socket, rawMsgHandler, receivedHeartBeatSuccessedTime)
            val finalSocket: Socket = socket
            hdpTask = CompletableFuture.supplyAsync({
                val hdpTaskThreadName = "hdp_thread_in_$threadName"
                ThreadUtil.setThreadName(hdpTaskThreadName)
                try {
                    hdp.start()
                } catch (e: IOException) {
                    println(e.toString())
                    cleanHeartBeatTask()
                    try {
                        finalSocket.close()
                    } catch (ex: IOException) {
                        ex.printStackTrace()
                    }
                    return@supplyAsync false
                }
                true
            }, Executors.newSingleThreadExecutor())


            // 检测心跳循环
            while (true) {
                try {
                    Thread.sleep(CHECK_HEARTBEAT_INTERVAL.toLong())
                    val current = System.currentTimeMillis()
                    if (current - sendedHeartBeatSuccessedTime.get() > MAX_SEND_HEARTBEAT_INTERVAL) {
                        cleanHeartBeatTask()
                        break
                    }
                    if (current - receivedHeartBeatSuccessedTime.get() > MAX_RECEIVE_HEARTBEAT_INTERVAL) {
                        cleanHeartBeatTask()
                        break
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            try {
                Thread.sleep(RECONNECT_IDLE.toLong())
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    fun cleanHeartBeatTask(): Boolean {
        var cancelSendHeartBeatRes = true
        var cancelReceiveHeartBeatRes = true
        if (heartbeatTask != null && !heartbeatTask!!.isCancelled) {
            cancelSendHeartBeatRes = heartbeatTask!!.cancel(true)
            heartbeatTask = null
        }
        if (hdpTask != null && !hdpTask!!.isCancelled) {
            cancelReceiveHeartBeatRes = hdpTask!!.cancel(true)
            hdpTask = null
        }
        return cancelSendHeartBeatRes && cancelReceiveHeartBeatRes
    }

    companion object {
        private var START_COUNT = -1
        private val RANDOM = Random()
        private const val RECEIVE_BUFFER_SIZE = 10 * 1024

        // 单位: 秒
        private const val HEARTBEAT_INTERVAL = 20

        // 单位: 毫秒
        // 发送heartBeat 20s发一次, 50s都没发过认为发送失败掉线
        private const val MAX_SEND_HEARTBEAT_INTERVAL = 50000

        // 接收heartBeat 正常应该几秒收到一次, 20s都没收到认为接收掉线
        private const val MAX_RECEIVE_HEARTBEAT_INTERVAL = 20000
        private const val CHECK_HEARTBEAT_INTERVAL = 10000
        private const val RECONNECT_IDLE = 10000

        @Throws(BiliDanmakuClientException::class)
        @JvmStatic
        fun main(args: Array<String>) {
        }
    }

    init {
        tmpRoomId = bLiveDanmakuConfig.roomId
        uId = bLiveDanmakuConfig.userId ?: (1e14 + 2e14 * RANDOM.nextDouble()).toLong()
        rawMsgHandler = RawMsgHandlerImpl(bLiveDanmakuConfig)
        hostServerToken = null
        roomInitRequest = RoomInitRequest()
        danmakuServerConfRequest = DanmakuServerConfRequest()
        heartbeatTask = null
        hdpTask = null
        heartbeatThreadPool = Executors.newScheduledThreadPool(1) { r: Runnable? ->
            val t = Thread(r)
            t.name = "heartbeat thread"
            t
        }
        sendedHeartBeatSuccessedTime = AtomicLong(System.currentTimeMillis())
        receivedHeartBeatSuccessedTime = AtomicLong(System.currentTimeMillis())
    }
}