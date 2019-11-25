package com.ggemo.bilidanmakuclient.http.response.responsedataa;

import com.alibaba.fastjson.annotation.JSONField;
import com.ggemo.bilidanmakuclient.http.response.ResponseData;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DanmakuServerConfResponseData implements ResponseData {
    @Data
    public class HostServerInfo{
        String host;
        int port;
        @JSONField(name="ws_port")
        int wsPort;
        @JSONField(name="wss_port")
        int wssPort;
    }

    @Data
    public class ServerInfo{
        String host;
        int port;
    }

    @JSONField(name="refresh_row_factor")
    float refreshRowFactor;

    @JSONField(name="refresh_rate")
    int refreshRate;

    @JSONField(name="max_delay")
    int maxDelay;

    @JSONField(name="port")
    int port;

    @JSONField(name="host")
    String host;

    @JSONField(name="host_server_list")
    List<HostServerInfo> hostServerList;

    @JSONField(name="server_list")
    List<ServerInfo> serverList;

    @JSONField(name="token")
    String token;
}
