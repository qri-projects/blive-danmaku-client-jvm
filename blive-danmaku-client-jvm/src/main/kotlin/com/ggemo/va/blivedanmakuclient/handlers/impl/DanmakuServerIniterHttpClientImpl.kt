package com.ggemo.va.blivedanmakuclientkt.handlers.impl

import com.ggemo.va.bilidanmakuclient.http.request.DanmakuServerConfRequest
import com.ggemo.va.bilidanmakuclient.http.response.impl.DanmakuServerConfResponse
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.DanmakuServerData
import com.ggemo.va.blivedanmakuclientkt.handlers.DanmakuServerIniter

class DanmakuServerIniterHttpClientImpl: DanmakuServerIniter {
    private val danmakuServerConfRequest = DanmakuServerConfRequest()
    override fun initDanmakuServer(roomId: Long): DanmakuServerData {

        val danmakuServerResponse: DanmakuServerConfResponse = danmakuServerConfRequest.request(roomId)

        return danmakuServerResponse.getD()!!
    }
}