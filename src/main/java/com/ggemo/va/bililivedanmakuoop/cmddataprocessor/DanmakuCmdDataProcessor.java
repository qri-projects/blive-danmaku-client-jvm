package com.ggemo.va.bililivedanmakuoop.cmddataprocessor;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.ggemo.va.bililivedanmakuoop.CmdEnum;
import com.ggemo.va.bililivedanmakuoop.cmddata.DanmakuData;
import com.ggemo.va.bililivedanmakuoop.handler.DanmakuHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DanmakuCmdDataProcessor extends CmdDataProcessor<DanmakuData, DanmakuHandler> {

    public DanmakuCmdDataProcessor(
            Set<DanmakuHandler> abstractHandlers, long roomId) {
        super(abstractHandlers, roomId, CmdEnum.DANMU_MSG);
    }

    @Override
    public void handle(JSONObject jsonObject) {
        DanmakuData danmakuData = DanmakuData.fromJSON(jsonObject.getJSONArray("info"));
        danmakuData.setRoomId(this.roomId);
        this.handle(danmakuData);
    }
}
