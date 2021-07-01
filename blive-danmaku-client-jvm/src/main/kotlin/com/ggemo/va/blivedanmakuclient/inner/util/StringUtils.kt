package com.ggemo.va.blivedanmakuclient.inner.util

object StringUtils {
    fun removeTransSlash(str: String): String {
        return str.replace("\\\\/".toRegex(), "/")
    }
}