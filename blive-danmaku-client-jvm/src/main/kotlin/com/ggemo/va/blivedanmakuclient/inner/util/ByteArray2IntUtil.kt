package com.ggemo.va.blivedanmakuclient.inner.util

object ByteArray2IntUtil {
    fun byteArray2Int32(b: ByteArray): Int {
        val ch1 = b[0].toInt() and 0xff
        val ch2 = b[1].toInt() and 0xff
        val ch3 = b[2].toInt() and 0xff
        val ch4 = b[3].toInt() and 0xff
        return (ch1 shl 24) + (ch2 shl 16) + (ch3 shl 8) + (ch4 shl 0)
    }
}