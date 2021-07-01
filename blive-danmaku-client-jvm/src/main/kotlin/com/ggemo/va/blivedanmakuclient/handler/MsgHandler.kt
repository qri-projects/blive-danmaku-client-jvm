package com.ggemo.va.blivedanmakuclient.handler

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.ggemo.va.blivedanmakuclient.inner.handlers.impl.msgLoopManagerImpl
import org.slf4j.LoggerFactory
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import java.nio.charset.StandardCharsets
import java.util.zip.Inflater

open class MsgHandler: MsgHandlerItfc {
    private val log = LoggerFactory.getLogger(msgLoopManagerImpl::class.java)

    override suspend fun handleRaw(data: ByteArray) {
        val dataLength = data.size
        if (dataLength < 16) {
            log.warn("wrong data")
        } else {
            val inputStream = DataInputStream(ByteArrayInputStream(data))
            val msgLength = inputStream.readInt()
            if (msgLength < 16) {
                log.warn("maybe need expand size of cache")
            } else if (msgLength > 16 && msgLength == dataLength) {
                val headerLength = inputStream.readShort()
                val version = inputStream.readShort()
                val action = inputStream.readInt() - 1
                val param = inputStream.readInt()
                val msgBodyLength = msgLength - 16
                val msgBody = ByteArray(msgBodyLength)
                val header = MsgHeader(msgLength, headerLength, version, action, param)
                inputStream.read(msgBody, 0, msgBodyLength)
                handleAction(action, header, msgBody)
            } else if (msgLength > 16 && msgLength < dataLength) {
                val singleData = ByteArray(msgLength)
                System.arraycopy(data, 0, singleData, 0, msgLength)
                handleRaw(singleData)
                val remainLen = dataLength - msgLength
                val remainDate = ByteArray(remainLen)
                System.arraycopy(data, msgLength, remainDate, 0, remainLen)
                handleRaw(remainDate)
            }
        }
    }

    protected suspend fun handleCmdJsonObj(cmd: String) {
        val obj = JSON.parseObject(cmd)
        val cmdName = obj.getString("cmd")
        handleCmdJsonObj(cmdName, obj)
    }

    private val actionHandlers = HashMap<Int, suspend (MsgHeader, ByteArray) -> Unit>()
    protected fun registerActionHandler(action: Int, handler: suspend (MsgHeader, ByteArray) -> Unit) {
        actionHandlers[action] = handler
    }
    protected open suspend fun handleAction(action: Int, header: MsgHeader, data: ByteArray) {
        actionHandlers[action]?.invoke(header, data)
    }

    private val versionHandlers = HashMap<Int, suspend (MsgHeader, ByteArray) -> Unit>()
    protected fun registerVersionHandler(version: Int, handler: suspend (MsgHeader, ByteArray) -> Unit) {
        versionHandlers[version] = handler
    }
    protected open suspend fun handleVersion(version: Int, header: MsgHeader, data: ByteArray) {
        (versionHandlers[version] ?: versionHandlers[1])!!.invoke(header, data)
    }

    private val cmdHandlers = HashMap<String, suspend (JSONObject) -> Unit>()
    protected fun registerCmdStringHandler(cmdName: String, handler: suspend (JSONObject) -> Unit) {
        cmdHandlers[cmdName] = handler
    }
    protected open suspend fun handleCmdJsonObj(cmdName: String, obj: JSONObject) {
        cmdHandlers[cmdName]?.invoke(obj)
    }

    protected open fun init() {
        registerActionHandler(4) { header, msgBody ->
            val versionInt = header.version.toInt()
            handleVersion(versionInt, header, msgBody)
        }

        registerVersionHandler(1) { _, msgBody ->
            val jsonStr = String(msgBody, StandardCharsets.UTF_8)
            handleCmdJsonObj(jsonStr)
        }

        registerVersionHandler(2) { _, msgBody ->
            // inflate
            val inflater = Inflater()
            inflater.setInput(msgBody)
            while (!inflater.finished()) {
                val innerHeaderBytes = ByteArray(16)
                inflater.inflate(innerHeaderBytes, 0, 16)
                val headerStream = DataInputStream(ByteArrayInputStream(innerHeaderBytes))
                val innerMsgLen = headerStream.readInt()
                val innerHeaderLength = headerStream.readShort()
                val innerVersion = headerStream.readShort()
                val innerAction = headerStream.readInt() - 1
                val innerParam = headerStream.readInt()

                val innerHeader = MsgHeader(innerMsgLen, innerHeaderLength, innerVersion, innerAction, innerParam)
                val innerData = ByteArray(innerMsgLen - 16)
                inflater.inflate(innerData, 0, innerData.size)
                handleAction(innerAction, innerHeader, innerData)
            }
        }

        registerVersionHandler(3) { msgHeader, msgBody ->
            TODO("ohhhhhhhhhhhhhhhhhhhhhhhhhhh" + JSON.toJSONString(msgBody))
        }
    }

    init {
        init()
    }

    data class MsgHeader(
        // 4
        val msgLen: Int,
        // 2
        val headerLen: Short,
        // 2
        val version: Short,
        // 4
        val action: Int,
        // 4
        val param: Int
    )
}