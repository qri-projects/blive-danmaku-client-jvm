package com.ggemo.va.bilidanmakuclient

import java.net.Socket
import com.ggemo.va.bilidanmakuclient.handler.RawMsgHandler
import java.util.concurrent.atomic.AtomicLong
import kotlin.Throws
import java.io.IOException
import com.ggemo.va.bilidanmakuclient.exception.BLiveDanmakuClientRuntimeException
import java.io.DataInputStream
import com.ggemo.va.bilidanmakuclient.util.ReadStreamUtil
import java.util.concurrent.CompletableFuture
import java.util.zip.DataFormatException
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.zip.Inflater

class HandleDataLoop(
    private val socket: Socket?,
    private val rawMsgHandler: RawMsgHandler,
    private val receivedHeartBeatTime: AtomicLong
) {
    @Throws(IOException::class)
    fun start() {
        if (socket != null) {
//            int bufferSize = socket.getReceiveBufferSize();
            val bufferSize = 10 * 1024
            var socketInputStream: InputStream
            while (socket.getInputStream().also { socketInputStream = it } != null) {
                val input = DataInputStream(socketInputStream)
                val ret = ReadStreamUtil.readStream(input, bufferSize)
                CompletableFuture.supplyAsync<Any?> {
                    try {
                        analyzeData(ret)
                    } catch (e: IOException) {
                        throw BLiveDanmakuClientRuntimeException("IOException in analyzeData: $e")
                    } catch (e: DataFormatException) {
                        throw BLiveDanmakuClientRuntimeException("DataFormatException in analyzeData: $e")
                    }
                    null
                }
            }
        }
    }

    @Throws(IOException::class, DataFormatException::class)
    private fun analyzeData(data: ByteArray) {
        val dataLength = data.size
        if (dataLength < 16) {
            println("wrong data")
        } else {
//            System.out.println(Arrays.toString(data));
            val inputStream = DataInputStream(ByteArrayInputStream(data))
            val msgLength = inputStream.readInt()
            if (msgLength < 16) {
                println("maybe need expand size of cache")
            } else if (msgLength > 16 && msgLength == dataLength) {
                val headerLength = inputStream.readShort()
                val version = inputStream.readShort()
                val action = inputStream.readInt() - 1
                // 直播间在线用户数目
                if (action == 2) {
                    receivedHeartBeatTime.set(System.currentTimeMillis())
                    inputStream.readInt()
                    val userCount = inputStream.readInt()
                    rawMsgHandler.handleHeartBeat(userCount)
                } else if (action == 4) {
                    val param = inputStream.readInt()
                    val msgBodyLength = dataLength - 16
                    val msgBody = ByteArray(msgBodyLength)
                    inputStream.read(msgBody, 0, msgBodyLength)
                    if (version.toInt() != 2) {
                        val jsonStr = String(msgBody, StandardCharsets.UTF_8)
                        rawMsgHandler.handleCmdMsg(jsonStr)
                    } else {
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
                                rawMsgHandler.handleCmdMsg(jsonStr)
                            } else if (innerAction == 2) {
                                // pass
                            }
                        }
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