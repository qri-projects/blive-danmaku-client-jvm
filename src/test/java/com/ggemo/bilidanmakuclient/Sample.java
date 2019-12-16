package com.ggemo.bilidanmakuclient;

import com.ggemo.bilidanmakuclient.handler.CmdHandler;
import com.ggemo.bilidanmakuclient.handler.HandlerHolder;
import com.ggemo.bilidanmakuclient.handler.UserCountHandler;
import com.ggemo.bilidanmakuclient.oop.cmddata.DanmakuData;
import com.ggemo.bilidanmakuclient.oop.cmddata.GuardBuyData;
import com.ggemo.bilidanmakuclient.oop.cmddata.SendGiftData;
import com.ggemo.bilidanmakuclient.oop.cmddata.SuperChatData;
import com.ggemo.bilidanmakuclient.oop.handler.*;
import org.junit.Test;


public class Sample {
    private static final long ROOM_ID = 4767523;

    public static void simplePrint(long roomId) {
        HandlerHolder handlerHolder = new HandlerHolder();

        // 收到房间观众数信息时打印房间观众数
        handlerHolder.addUserCountHandler(new UserCountHandler() {
            @Override
            public void handle(int x) {
                System.out.println(x);
            }
        });
        // 收到事件时打印事件(字符串)
        handlerHolder.addCmdHandler(new CmdHandler() {
            @Override
            public void handle(String x) {
                System.out.println(x);
            }
        });

        BiliLiveDanmakuClient client = new BiliLiveDanmakuClient(roomId, handlerHolder);
        client.start();
    }

    public static void oopPrint(long roomId) {
        OopCmdHandler cmdHandler = new OopCmdHandler();

        // 收到弹幕时打印com.ggemo.bilidanmakuclient.oop.cmddata.DanmakuData对象
        cmdHandler.addDanmakuHandler(new DanmakuHandler() {
            @Override
            public void handle(DanmakuData x) {
                System.out.println(x);
            }
        });

        // 收到礼物时打印com.ggemo.bilidanmakuclient.oop.cmddata.SendGiftData对象
        cmdHandler.addSendGiftHandler(new SendGiftHandler() {
            @Override
            public void handle(SendGiftData x) {
                System.out.println(x);
            }
        });

        // 有观众上舰时打印com.ggemo.bilidanmakuclient.oop.cmddata.GuardBuyData对象
        cmdHandler.addGuardBuyHandler(new GuardBuyHandler() {
            @Override
            public void handle(GuardBuyData x) {
                System.out.println(x);
            }
        });

        // 收到醒目留言时打印com.ggemo.bilidanmakuclient.oop.cmddata.SuperChatData对象
        cmdHandler.addSuperchatHandler(new SuperchatHandler() {
            @Override
            public void handle(SuperChatData x) {
                System.out.println(x);
            }
        });

        HandlerHolder handlerHolder = new HandlerHolder();

        // 收到房间观众数信息时打印房间观众数
        handlerHolder.addUserCountHandler(x -> System.out.println(x));
        handlerHolder.addCmdHandler(cmdHandler);

        BiliLiveDanmakuClient client = new BiliLiveDanmakuClient(roomId, handlerHolder);
        client.start();
    }

    @Test
    void simplePrintTest() {
        long roomId = 4767523L;
        simplePrint(roomId);
    }

    @Test
    void oopPrintPrint() {
        long roomId = 4767523L;
        oopPrint(roomId);
    }
}
