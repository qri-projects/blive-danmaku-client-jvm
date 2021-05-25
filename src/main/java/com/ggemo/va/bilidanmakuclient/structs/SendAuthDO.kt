package com.ggemo.va.bilidanmakuclient.structs

import com.alibaba.fastjson.JSON
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import com.alibaba.fastjson.annotation.JSONField
import lombok.Data

class SendAuthDO(uId: Long, roomId: Long, token: String?) {
    @JSONField(name = "uid")
    val uId: Long = uId

    @JSONField(name = "roomid")
    val roomId: Long = roomId
    val key: String? = token

    @JSONField(name = "protover")
    val PROTOVER = 2

    @JSONField(name = "platform")
    val PLATFORM = "web"

    @JSONField(name = "clientver")
    val CLIENT_VER = "1.8.2"

    @JSONField(name = "type")
    val TYPE = 2
}