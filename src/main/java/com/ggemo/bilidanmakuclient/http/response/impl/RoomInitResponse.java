package com.ggemo.bilidanmakuclient.http.response.impl;

import com.alibaba.fastjson.JSON;
import com.ggemo.bilidanmakuclient.http.response.AbstractResponse;
import com.ggemo.bilidanmakuclient.http.response.responsedataa.RoomInitResponseData;
import lombok.Data;

public class RoomInitResponse extends AbstractResponse<RoomInitResponseData> {
    public RoomInitResponse(int code, String message, RoomInitResponseData data) {
        super(code, message, data);
    }

    public static RoomInitResponse parse(String json) {
        RoomInitResponse res = JSON.parseObject(json, RoomInitResponse.class);
        return res;
    }
}
