package com.ggemo.va.blivedanmakuclientkt

data class BLiveDanmakuConfig(
    // 发送心跳的间隔
    val heartBeatInterval: Long,
    // 检测心跳健康任务的执行间隔
    val checkHeartBeatInterval: Long,
    // 最长发送心跳的间隔, 超过这个时间, 认为程序挂掉
    val maxSentHeartBeatInterval: Long,
    // 最长接收心跳的间隔, 超过这个时间, 认为程序挂掉
    val maxReceivedHeartBeatInterval: Long,
    // 重连等待时长
    val reconnectInterval: Long
)
