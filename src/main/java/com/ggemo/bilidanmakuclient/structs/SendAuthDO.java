package com.ggemo.bilidanmakuclient.structs;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendAuthDO {
    @JSONField(name="uid")
    private long uId;

    @JSONField(name="roomid")
    private long roomId;

    @JSONField(name="protover")
    private static final int PROTOVER = 2;

    @JSONField(name="platform")
    private static final String PLATFORM = "web";

    @JSONField(name="clientver")
    private static final String CLIENT_VER = "1.8.2";

    @JSONField(name="type")
    private static final int TYPE = 2;

    private String key;
}
