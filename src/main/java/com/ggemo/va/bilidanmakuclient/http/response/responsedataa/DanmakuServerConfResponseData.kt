package com.ggemo.va.bilidanmakuclient.http.response.responsedataa

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
import com.ggemo.va.bilidanmakuclient.http.response.ResponseData
import lombok.Data
import kotlin.jvm.JvmStatic
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.HttpClients

@Data
class DanmakuServerConfResponseData : ResponseData {
    @Data
    inner class HostServerInfo {
        var host: String? = null
        var port = 0

        @JSONField(name = "ws_port")
        var wsPort = 0

        @JSONField(name = "wss_port")
        var wssPort = 0
    }

    @Data
    inner class ServerInfo {
        var host: String? = null
        var port = 0
    }

    @JSONField(name = "refresh_row_factor")
    var refreshRowFactor = 0f

    @JSONField(name = "refresh_rate")
    var refreshRate = 0

    @JSONField(name = "max_delay")
    var maxDelay = 0

    @JSONField(name = "port")
    var port = 0

    @JSONField(name = "host")
    var host: String? = null

    @JSONField(name = "host_server_list")
    var hostServerList: List<HostServerInfo>? = null

    @JSONField(name = "server_list")
    var serverList: List<ServerInfo>? = null

    @JSONField(name = "token")
    var token: String? = null
}