package com.ggemo.va.bilidanmakuclient.http.request

import com.ggemo.va.bilidanmakuclient.http.MyHttpClient
import com.ggemo.va.bilidanmakuclient.http.response.impl.RoomInitResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.util.EntityUtils
import java.io.IOException

class RoomInitRequest : Request {
    companion object {
        private const val ROOM_INIT_URL_TEMPLATE = "https://api.live.bilibili.com/room/v1/Room/room_init?id=%d"
    }

    private val httpClient: MyHttpClient = MyHttpClient.instance

    @Throws(IOException::class)
    fun request(roomId: Long): RoomInitResponse {
        val httpGet = HttpGet(String.format(ROOM_INIT_URL_TEMPLATE, roomId))
        val httpResponse = httpClient.request(httpGet)
        val responseEntity = httpResponse.entity
        return try {
            RoomInitResponse.parse(EntityUtils.toString(responseEntity))
        } finally {
            EntityUtils.consume(responseEntity)
            httpResponse.close()
        }
    }
}