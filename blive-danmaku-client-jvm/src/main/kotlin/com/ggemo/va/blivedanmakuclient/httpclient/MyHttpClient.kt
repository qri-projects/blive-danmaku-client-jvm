package com.ggemo.va.bilidanmakuclient.http

import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import java.io.IOException

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