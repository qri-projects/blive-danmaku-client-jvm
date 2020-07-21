package com.ggemo.va.bililivedanmakuoop.cmddataprocessor;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.ggemo.va.bililivedanmakuoop.CmdEnum;
import com.ggemo.va.bililivedanmakuoop.cmddata.SendGiftData;
import com.ggemo.va.bililivedanmakuoop.handler.SendGiftHandler;

public class SendGiftCmdDataProcessor extends CmdDataProcessor<SendGiftData, SendGiftHandler> {
    public SendGiftCmdDataProcessor(Set<SendGiftHandler> handlers, long roomId) {
        super(handlers, roomId, CmdEnum.SEND_GIFT);
    }

    @Override
    public void handle(JSONObject jsonObject) {
        SendGiftData sendGiftData = SendGiftData.fromJSON(jsonObject.getJSONObject("data"));
        sendGiftData.setRoomId(roomId);
        this.handle(sendGiftData);
    }
}
