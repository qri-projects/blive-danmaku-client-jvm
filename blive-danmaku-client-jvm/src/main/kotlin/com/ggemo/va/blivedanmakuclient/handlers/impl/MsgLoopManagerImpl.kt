package com.ggemo.va.blivedanmakuclient.handlers.impl


import com.alibaba.fastjson.JSON
import com.ggemo.va.blivedanmakuclient.util.ReadStreamUtil
import com.ggemo.va.blivedanmakuclientkt.domain.ApplicationContext
import com.ggemo.va.blivedanmakuclientkt.handlers.MsgLoopManager
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.io.InputStream
import java.net.Socket
import java.nio.charset.StandardCharsets
import java.util.concurrent.atomic.AtomicBoolean
import java.util.zip.Inflater

class MsgLoopManagerImpl : MsgLoopManager {
    private val log = LoggerFactory.getLogger(MsgLoopManagerImpl::class.java)
    private val task2CancelMap = HashMap<ApplicationContext, AtomicBoolean>()

    override fun cancel(context: ApplicationContext) {
        task2CancelMap.getOrPut(context) { AtomicBoolean(true) }.set(true)
    }

    override fun start(socket: Socket, context: ApplicationContext) {
        log.info("start!")
        var socketInputStream: InputStream
        val bufferSize = 10 * 1024
        while (socket.getInputStream().also { socketInputStream = it } != null) {
            if (task2CancelMap[context]?.get() == true) {
                return
            }
            val input = DataInputStream(socketInputStream)
            val ret = ReadStreamUtil.readStream(input, bufferSize)
            analyzeData(ret)
        }
    }

    /**
     * todo:
     * - suspend
     * - 清log
     * - 外部handler
     */
    private fun analyzeData(data: ByteArray) {

        val dataLength = data.size
        if (dataLength < 16) {
            log.warn("wrong data")
        } else {
//            System.out.println(Arrays.toString(data));
            val inputStream = DataInputStream(ByteArrayInputStream(data))
            val msgLength = inputStream.readInt()
            if (msgLength < 16) {
                log.warn("maybe need expand size of cache")
            } else if (msgLength > 16 && msgLength == dataLength) {
                val headerLength = inputStream.readShort()
                val version = inputStream.readShort()
                val action = inputStream.readInt() - 1
                // 直播间在线用户数目
                if (action == 2) {
//                    receivedHeartBeatTime.set(System.currentTimeMillis())
                    inputStream.readInt()
                    val userCount = inputStream.readInt()
                    println("userCount: $userCount")
//                    rawMsgHandler.handleHeartBeat(userCount)
                } else if (action == 4) {
                    val param = inputStream.readInt()
                    val msgBodyLength = dataLength - 16
                    val msgBody = ByteArray(msgBodyLength)
                    inputStream.read(msgBody, 0, msgBodyLength)
                    var versionInt = version.toInt()
                    if (versionInt == 2) {
                        // inflate
                        val inflater = Inflater()
                        inflater.setInput(msgBody)
                        while (!inflater.finished()) {
                            val header = ByteArray(16)
                            inflater.inflate(header, 0, 16)
                            val headerStream = DataInputStream(ByteArrayInputStream(header))
                            val innerMsgLen = headerStream.readInt()
                            val innerHeaderLength = headerStream.readShort()
                            val innerVersion = headerStream.readShort()
                            val innerAction = headerStream.readInt() - 1
                            val innerParam = headerStream.readInt()
                            val innerData = ByteArray(innerMsgLen - 16)
                            inflater.inflate(innerData, 0, innerData.size)
                            if (innerAction == 4) {
                                val jsonStr = String(innerData, StandardCharsets.UTF_8)
                                println(jsonStr)
//                                rawMsgHandler.handleCmdMsg(jsonStr)
                            } else if (innerAction == 2) {
                                // pass
                            }
                        }
                    } else if (versionInt == 3) {
                        TODO("ohhhhhhhhhhhhhhhhhhhhhhhhhhh" + JSON.toJSONString(msgBody))
                    } else {
                        val jsonStr = String(msgBody, StandardCharsets.UTF_8)
                        println(jsonStr)
//                        rawMsgHandler.handleCmdMsg(jsonStr)
                    }
                }
            } else if (msgLength > 16 && msgLength < dataLength) {
                val singleData = ByteArray(msgLength)
                System.arraycopy(data, 0, singleData, 0, msgLength)
                analyzeData(singleData)
                val remainLen = dataLength - msgLength
                val remainDate = ByteArray(remainLen)
                System.arraycopy(data, msgLength, remainDate, 0, remainLen)
                analyzeData(remainDate)
            }
        }
    }
}