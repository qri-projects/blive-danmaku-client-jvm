package com.ggemo.va.bilidanmakuclient.util

import lombok.extern.slf4j.Slf4j
import kotlin.Throws
import java.io.IOException
import com.ggemo.va.bilidanmakuclient.util.ReadStreamUtil

/**
 * Author: 清纯的小黄瓜
 * Date: 2020/4/13 11:52
 * Email: 2894700792@qq.com
 */
@Slf4j
object ThreadUtil {
    fun setThreadName(threadName: String) {
        Thread.currentThread().name = threadName
    }
}