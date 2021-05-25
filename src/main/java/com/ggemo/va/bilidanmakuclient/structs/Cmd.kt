package com.ggemo.va.bilidanmakuclient.structs

import java.util.*
import java.util.function.Function
import java.util.stream.Collectors

enum class Cmd(val rawValue: String) {
    DANMAKU("DANMU_MSG"),
    SEND_GIFT("SEND_GIFT"),
    GUARD_BUY("GUARD_BUY"),
    SUPER_CHAT("SUPER_CHAT_MESSAGE");
}