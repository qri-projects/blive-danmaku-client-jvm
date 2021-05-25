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

class MyHttpClient private constructor() {
    private val httpClient: CloseableHttpClient
    @Throws(IOException::class)
    fun request(httpPost: HttpPost?): CloseableHttpResponse {
        return httpClient.execute(httpPost)
    }

    @Throws(IOException::class)
    fun request(httpGet: HttpGet?): CloseableHttpResponse {
        return httpClient.execute(httpGet)
    }

    companion object {
        val instance = MyHttpClient()
    }

    init {
        val cm = PoolingHttpClientConnectionManager()
        cm.maxTotal = 100
        val clientbuilder = HttpClients.custom().setConnectionManager(cm)
        clientbuilder.disableCookieManagement()
        httpClient = clientbuilder.build()
    }
}