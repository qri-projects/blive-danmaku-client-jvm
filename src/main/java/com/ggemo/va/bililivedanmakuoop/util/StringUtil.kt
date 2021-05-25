package com.ggemo.va.bililivedanmakuoop.util

import java.util.regex.Pattern

object StringUtil {
    private val PATTERN = Pattern.compile("(\\\\u(\\p{XDigit}{4}))")

    fun unicodeToString(unicodeStr: String): String {
        var unicodeStr = unicodeStr
        val matcher = PATTERN.matcher(unicodeStr)
        var ch: Char
        while (matcher.find()) {
            val group = matcher.group(2)
            ch = group.toInt(16).toChar()
            val group1 = matcher.group(1)
            unicodeStr = unicodeStr.replace(group1, ch.toString() + "")
        }
        return unicodeStr
    }

    fun removeTransSlash(str: String): String {
        return str.replace("\\\\/".toRegex(), "/")
    }
}