package com.ggemo.va.bilidanmakuclient.http.response.impl;

import com.alibaba.fastjson.JSON;
import com.ggemo.va.bilidanmakuclient.http.response.AbstractResponse;
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.DanmakuServerConfResponseData;

public class DanmakuServerConfResponse extends AbstractResponse<DanmakuServerConfResponseData> {
    public DanmakuServerConfResponse(int code, String message, DanmakuServerConfResponseData data) {
        super(code, message, data);
    }

    public static DanmakuServerConfResponse parse(String json) {
        DanmakuServerConfResponse res = JSON.parseObject(json, DanmakuServerConfResponse.class);
        return res;
    }
}
