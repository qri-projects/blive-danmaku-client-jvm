package com.ggemo.bilidanmakuclient.copyCS;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class HostServer {
    String host;
    int port;
    @JSONField(name = "wss_port")
    int wssPort;
    @JSONField(name = "ws_port")
    int wsPort;
}
