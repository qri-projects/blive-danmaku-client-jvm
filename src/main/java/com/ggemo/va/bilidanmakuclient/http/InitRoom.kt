package com.ggemo.va.bilidanmakuclient.http

import com.ggemo.va.bilidanmakuclient.http.MyHttpClient
import kotlin.Throws
import java.io.IOException
import com.ggemo.va.bilidanmakuclient.http.response.impl.RoomInitResponse
import org.apache.http.client.methods.HttpGet
import com.ggemo.va.bilidanmakuclient.http.request.RoomInitRequest
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.HttpEntity
import org.apache.http.util.EntityUtils
import com.ggemo.va.bilidanmakuclient.http.response.impl.DanmakuServerConfResponse
import com.ggemo.va.bilidanmakuclient.http.request.DanmakuServerConfRequest
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.RoomInitResponseData
import com.ggemo.va.bilidanmakuclient.http.response.AbstractResponse
import com.alibaba.fastjson.JSON
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.DanmakuServerConfResponseData
import com.alibaba.fastjson.annotation.JSONField
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.DanmakuServerConfResponseData.HostServerInfo
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.DanmakuServerConfResponseData.ServerInfo
import lombok.NoArgsConstructor
import lombok.AllArgsConstructor
import com.ggemo.va.bilidanmakuclient.http.exception.BiliClientException
import java.lang.UnsupportedOperationException
import com.ggemo.va.bilidanmakuclient.http.InitRoom
import kotlin.jvm.JvmStatic
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.HttpClients

class InitRoom {
    private val httpClient: MyHttpClient
    @Throws(IOException::class)
    fun initRoom(roomId: Long): RoomInitResponse {
        var httpGet = HttpGet(String.format(ROOM_INIT_URL_TEMPLATE, roomId))
        var httpResponse = httpClient.request(httpGet)
        var responseEntity = httpResponse!!.entity
        val roomInitResponse: RoomInitResponse = RoomInitResponse.Companion.parse(EntityUtils.toString(responseEntity))
        httpGet = HttpGet(String.format(DANMAKU_SERVER_CONF_URL_TEMPLATE, roomId))
        httpResponse = httpClient.request(httpGet)
        responseEntity = httpResponse.entity
        return try {
            RoomInitResponse.Companion.parse(EntityUtils.toString(responseEntity))
        } finally {
            EntityUtils.consume(responseEntity)
            httpResponse.close()
        }
    }

    companion object {
        private const val ROOM_INIT_URL_TEMPLATE = "https://api.live.bilibili.com/room/v1/Room/room_init?id=%d"
        private const val DANMAKU_SERVER_CONF_URL_TEMPLATE = "https://api.live.bilibili.com/room/v1/Danmu/getConf?id=%d"
        @Throws(IOException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val x = InitRoom()
            val res = x.initRoom(21450685L)
            println(res)
        }
    }

    init {
        httpClient = MyHttpClient.instance
    }
}