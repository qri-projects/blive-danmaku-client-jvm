package com.ggemo.va.bilidanmakuclient.util;

import lombok.extern.slf4j.Slf4j;

/**
 * Author: 清纯的小黄瓜
 * Date: 2020/4/13 11:52
 * Email: 2894700792@qq.com
 */
@Slf4j
public class ThreadUtil {
    public static void setThreadName(String threadName){
        Thread.currentThread().setName(threadName);
        log.info("[set thread name: " + threadName + "]");
    }
}
