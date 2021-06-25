package com.ggemo.va.bilidanmakuclient.http.response.impl

import com.ggemo.va.bilidanmakuclient.http.response.AbstractResponse
import com.alibaba.fastjson.JSON
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.DanmakuServerData

class DanmakuServerConfResponse :
    AbstractResponse<DanmakuServerData?>() {
    companion object {
        fun parse(json: String?): DanmakuServerConfResponse {
            return JSON.parseObject(json, DanmakuServerConfResponse::class.java)
        }
    }
}