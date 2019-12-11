package com.ggemo.bilidanmakuclient.oop.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ggemo.bilidanmakuclient.handler.CmdHandler;
import com.ggemo.bilidanmakuclient.oop.CmdEnum;
import com.ggemo.bilidanmakuclient.oop.cmddata.DanmakuData;
import com.ggemo.bilidanmakuclient.oop.cmddata.GuardBuyData;
import com.ggemo.bilidanmakuclient.oop.cmddata.SendGiftData;
import com.ggemo.bilidanmakuclient.oop.cmddata.SuperChatData;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class OopCmdHandler implements CmdHandler {
    private Set<DanmakuHandler> danmakuHandlers;
    private Set<GuardBuyHandler> guardBuyHandlers;
    private Set<SendGiftHandler> sendGiftHandlers;
    private Set<SuperchatHandler> superchatHandlers;

    public OopCmdHandler() {
        this.danmakuHandlers = new HashSet<>();
        this.guardBuyHandlers = new HashSet<>();
        this.sendGiftHandlers = new HashSet<>();
        this.superchatHandlers = new HashSet<>();
    }

    public OopCmdHandler(DanmakuHandler danmakuHandler, GuardBuyHandler guardBuyHandler, SendGiftHandler sendGiftHandler, SuperchatHandler superchatHandler) {
        this();
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

    public boolean addSuperchatHandler(SuperchatHandler handler) {
        return superchatHandlers.add(handler);
    }

    public boolean removeSuperchatHandler(SuperchatHandler handler) {
        return superchatHandlers.remove(handler);
    }

    @Override
    public void handle(String str) {
        JSONObject jsonObject = JSON.parseObject(str);
        String cmd = jsonObject.getString("cmd");
        if (CmdEnum.DANMU_MSG.equalsStr(cmd)) {
            DanmakuData danmakuData = DanmakuData.fromJSON(jsonObject.getJSONArray("info"));
            for (DanmakuHandler handler : danmakuHandlers) {
                try {
                    handler.handle(danmakuData);
                } catch (Exception e) {
                    log.error("error while handling data. handler type: " + handler.getClass().getName() + "\nexception :" + e + "\ndata: " + str);
                }
            }
        } else if (CmdEnum.GUARD_BUY.equalsStr(cmd)) {
            GuardBuyData guardBuyData = GuardBuyData.fromJSON(jsonObject.getJSONObject("data"));
            for (GuardBuyHandler handler : guardBuyHandlers) {
                try {
                    handler.handle(guardBuyData);
                } catch (Exception e) {
                    log.error("error while handling data. handler type: " + handler.getClass().getName() + "\nexception :" + e + "\ndata: " + str);
                }
            }
        } else if (CmdEnum.SEND_GIFT.equalsStr(cmd)) {
            SendGiftData sendGiftData = SendGiftData.fromJSON(jsonObject.getJSONObject("data"));
            for (SendGiftHandler handler : sendGiftHandlers) {
                try {
                    handler.handle(sendGiftData);
                } catch (Exception e) {
                    log.error("error while handling data. handler type: " + handler.getClass().getName() + "\nexception :" + e + "\ndata: " + str);
                }
            }
        } else if (CmdEnum.SUPER_CHAT_MESSAGE.equalsStr(cmd)) {
            SuperChatData superChatData = SuperChatData.fromJSON(jsonObject.getJSONObject("data"));
            for (SuperchatHandler handler : superchatHandlers) {
                try {
                    handler.handle(superChatData);
                } catch (Exception e) {
                    log.error("error while handling data. handler type: " + handler.getClass().getName() + "\nexception :" + e + "\ndata: " + str);
                }
            }
        }
    }
}
