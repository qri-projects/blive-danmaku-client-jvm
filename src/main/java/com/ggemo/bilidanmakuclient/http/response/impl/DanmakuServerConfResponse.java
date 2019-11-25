package com.ggemo.bilidanmakuclient.http.response.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.ggemo.bilidanmakuclient.http.response.AbstractResponse;
import com.ggemo.bilidanmakuclient.http.response.responsedataa.DanmakuServerConfResponseData;
import com.ggemo.bilidanmakuclient.http.response.responsedataa.RoomInitResponseData;

import java.util.List;
import java.util.Map;

public class DanmakuServerConfResponse extends AbstractResponse<DanmakuServerConfResponseData> {
    public DanmakuServerConfResponse(int code, String message, DanmakuServerConfResponseData data) {
        super(code, message, data);
    }

    public static DanmakuServerConfResponse parse(String json) {
        DanmakuServerConfResponse res = JSON.parseObject(json, DanmakuServerConfResponse.class);
        return res;
    }
}
