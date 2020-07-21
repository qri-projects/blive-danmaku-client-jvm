package com.ggemo.va.bililivedanmakuoop.cmddataprocessor;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.ggemo.va.bililivedanmakuoop.CmdEnum;
import com.ggemo.va.bililivedanmakuoop.cmddata.GuardBuyData;
import com.ggemo.va.bililivedanmakuoop.handler.GuardBuyHandler;

public class GuardBuyCmdDataProcessor extends CmdDataProcessor<GuardBuyData, GuardBuyHandler> {
    public GuardBuyCmdDataProcessor(Set<GuardBuyHandler> handlers, long roomId) {
        super(handlers, roomId, CmdEnum.GUARD_BUY);
    }

    @Override
    public void handle(JSONObject jsonObject) {
        GuardBuyData guardBuyData = GuardBuyData.fromJSON(jsonObject.getJSONObject("data"));
        guardBuyData.setRoomId(roomId);
        this.handle(guardBuyData);
    }
}
