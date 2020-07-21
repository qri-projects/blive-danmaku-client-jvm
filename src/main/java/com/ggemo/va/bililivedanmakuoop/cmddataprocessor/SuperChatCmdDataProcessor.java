package com.ggemo.va.bililivedanmakuoop.cmddataprocessor;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.ggemo.va.bililivedanmakuoop.CmdEnum;
import com.ggemo.va.bililivedanmakuoop.cmddata.SuperChatData;
import com.ggemo.va.bililivedanmakuoop.handler.SuperChatHandler;

public class SuperChatCmdDataProcessor extends CmdDataProcessor<SuperChatData, SuperChatHandler> {
    public SuperChatCmdDataProcessor(Set<SuperChatHandler> handlers, long roomId) {
        super(handlers, roomId, CmdEnum.SUPER_CHAT_MESSAGE);
    }

    @Override
    public void handle(JSONObject jsonObject) {
        SuperChatData superChatData = SuperChatData.fromJSON(jsonObject.getJSONObject("data"));
        superChatData.setRoomId(roomId);
        this.handle(superChatData);
    }
}
