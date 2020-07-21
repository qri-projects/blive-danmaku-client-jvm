package com.ggemo.va.bililivedanmakuoop.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ggemo.va.bilidanmakuclient.handler.CmdHandler;
import com.ggemo.va.bililivedanmakuoop.cmddataprocessor.DanmakuCmdDataProcessor;
import com.ggemo.va.bililivedanmakuoop.cmddataprocessor.CmdDataProcessor;
import com.ggemo.va.bililivedanmakuoop.cmddataprocessor.GuardBuyCmdDataProcessor;
import com.ggemo.va.bililivedanmakuoop.cmddataprocessor.SendGiftCmdDataProcessor;
import com.ggemo.va.bililivedanmakuoop.cmddataprocessor.SuperChatCmdDataProcessor;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class OopCmdHandler implements CmdHandler {
    private final Set<DanmakuHandler> danmakuHandlers;
    private final Set<GuardBuyHandler> guardBuyHandlers;
    private final Set<SendGiftHandler> sendGiftHandlers;
    private final Set<SuperChatHandler> superChatHandlers;

    private final Map<String, CmdDataProcessor> cmdDataWrapperMap;
    private final long roomId;

    public OopCmdHandler(long roomId) {
        this.roomId = roomId;
        DanmakuCmdDataProcessor danmakuProcessor =
                new DanmakuCmdDataProcessor(this.danmakuHandlers = new HashSet<>(), roomId);
        GuardBuyCmdDataProcessor guardBuyProcessor =
                new GuardBuyCmdDataProcessor(this.guardBuyHandlers = new HashSet<>(), roomId);
        SendGiftCmdDataProcessor sendGiftCmdDataProcessor =
                new SendGiftCmdDataProcessor(this.sendGiftHandlers = new HashSet<>(), roomId);
        SuperChatCmdDataProcessor superChatCmdDataProcessor =
                new SuperChatCmdDataProcessor(this.superChatHandlers = new HashSet<>(), roomId);

        cmdDataWrapperMap = new HashMap<>() {{
            put(danmakuProcessor.getCmdEnum().getStrVal(), danmakuProcessor);
            put(guardBuyProcessor.getCmdEnum().getStrVal(), guardBuyProcessor);
            put(sendGiftCmdDataProcessor.getCmdEnum().getStrVal(), sendGiftCmdDataProcessor);
            put(superChatCmdDataProcessor.getCmdEnum().getStrVal(), superChatCmdDataProcessor);
        }};
    }

    public OopCmdHandler(long roomId, DanmakuHandler danmakuHandler, GuardBuyHandler guardBuyHandler,
                         SendGiftHandler sendGiftHandler, SuperChatHandler superchatHandler,
                         Map<String, CmdDataProcessor> cmdDataWrapperMap) {
        this(roomId);
        addDanmakuHandler(danmakuHandler);
        addGuardBuyHandler(guardBuyHandler);
        addSendGiftHandler(sendGiftHandler);
        addSuperchatHandler(superchatHandler);
    }

    public boolean addDanmakuHandler(DanmakuHandler handler) {
        return danmakuHandlers.add(handler);
    }

    public boolean removeDanmakuHandler(DanmakuHandler handler) {
        return danmakuHandlers.remove(handler);
    }

    public boolean addGuardBuyHandler(GuardBuyHandler handler) {
        return guardBuyHandlers.add(handler);
    }

    public boolean removeGuardBuyHandler(GuardBuyHandler handler) {
        return guardBuyHandlers.remove(handler);
    }

    public boolean addSendGiftHandler(SendGiftHandler handler) {
        return sendGiftHandlers.add(handler);
    }

    public boolean removeSendGiftHandler(SendGiftHandler handler) {
        return sendGiftHandlers.remove(handler);
    }

    public boolean addSuperchatHandler(SuperChatHandler handler) {
        return superChatHandlers.add(handler);
    }

    public boolean removeSuperchatHandler(SuperChatHandler handler) {
        return superChatHandlers.remove(handler);
    }

    @Override
    public void handle(String str) {
        JSONObject jsonObject = JSON.parseObject(str);
        String cmd = jsonObject.getString("cmd");
        CmdDataProcessor processor = cmdDataWrapperMap.get(cmd);
        if (processor != null) {
            try {
                processor.handle(jsonObject);
            } catch (Exception e) {
                log.error(String.format("error while handling data. handler type: %s\nexception :%s\ndata: %s", cmd, e,
                        str));
            }
        }
    }
}
