package com.ggemo.va.bilidanmakuclient.http.request

import com.ggemo.va.bilidanmakuclient.http.MyHttpClient
import kotlin.Throws
import java.io.IOException
import org.apache.http.client.methods.HttpGet
import org.apache.http.HttpEntity
import org.apache.http.util.EntityUtils
import com.ggemo.va.bilidanmakuclient.http.response.impl.DanmakuServerConfResponse

class DanmakuServerConfRequest : Request {
    private val httpClient: MyHttpClient
    @Throws(IOException::class)
    fun request(roomId: Long): DanmakuServerConfResponse {
        val httpGet = HttpGet(String.format(DANMAKU_SERVER_CONF_URL_TEMPLATE, roomId))
        val httpResponse = httpClient.request(httpGet)
        var responseEntity: HttpEntity? = null
        try {
            httpResponse.use {
                responseEntity = httpResponse.entity
                return DanmakuServerConfResponse.Companion.parse(EntityUtils.toString(responseEntity))
            }
        } finally {
            EntityUtils.consume(responseEntity)
        }
    }

    companion object {
        private const val DANMAKU_SERVER_CONF_URL_TEMPLATE =
            "https://api.live.bilibili.com/room/v1/Danmu/getConf?id=%d&platform=pc&player=web"
    }

    init {
        httpClient = MyHttpClient.instance
    }
}