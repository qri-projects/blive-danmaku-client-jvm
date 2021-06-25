package com.ggemo.va.blivedanmakuclient.util

import kotlin.Throws
import java.io.IOException
import java.io.InputStream

/**
 * Author: 清纯的小黄瓜
 * Date: 2020/4/13 13:28
 * Email: 2894700792@qq.com
 */
object ReadStreamUtil {
    fun readStream(stream: InputStream, bufferLen: Int): ByteArray {
        val out = ByteArray(bufferLen)
        val readLen = stream.read(out)
        if (readLen < bufferLen) {
            val littleOut = ByteArray(readLen)
            System.arraycopy(out, 0, littleOut, 0, readLen)
            return littleOut
        }
        val longerBufferLen = 2 * bufferLen
        val innerOut = readStream(stream, longerBufferLen)
        val alloutLen = out.size + innerOut.size
        val allout = ByteArray(alloutLen)
        System.arraycopy(out, 0, allout, 0, bufferLen)
        System.arraycopy(innerOut, 0, allout, bufferLen, innerOut.size)
        return allout
    }
}