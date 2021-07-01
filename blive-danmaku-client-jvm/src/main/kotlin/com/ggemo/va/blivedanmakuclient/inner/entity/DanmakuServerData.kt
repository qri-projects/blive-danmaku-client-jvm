package com.ggemo.va.blivedanmakuclient.inner.entity


import com.alibaba.fastjson.annotation.JSONField

class DanmakuServerData {
    inner class HostServerInfo {
        var host: String? = null
        var port = 0

        @JSONField(name = "ws_port")
        var wsPort = 0

        @JSONField(name = "wss_port")
        var wssPort = 0
    }
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
    var token: String = ""
}