package com.ggemo.va.blivedanmakuclient.inner.util

object stringUtils {
    fun removeTransSlash(str: String): String {
        return str.replace("\\\\/".toRegex(), "/")
    }
}