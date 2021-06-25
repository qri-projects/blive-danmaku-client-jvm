package com.ggemo.va.bilidanmakuclient.http.response.impl

import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.RoomInitData
import com.ggemo.va.bilidanmakuclient.http.response.AbstractResponse
import com.alibaba.fastjson.JSON

class RoomInitResponse :
    AbstractResponse<RoomInitData?>() {
    companion object {
        fun parse(json: String?): RoomInitResponse {
            return JSON.parseObject(json, RoomInitResponse::class.java)
        }
    }
}