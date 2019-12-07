package com.ggemo.bilidanmakustructs.handler;

import com.ggemo.bilidanmakuclient.BiliLiveDanmakuClient;
import com.ggemo.bilidanmakuclient.handler.HandlerHolder;
import com.ggemo.bilidanmakustructs.cmddata.DanmakuData;

import static org.junit.Assert.*;

public class OopCmdHandlerTest {
    public static void main(String[] args) {
        OopCmdHandler handler = new OopCmdHandler();
        handler.addDanmakuHandler(System.out::println);
        handler.addGuardBuyHandler(System.out::println);
        handler.addSendGiftHandler(System.out::println);
        handler.addSuperchatHandler(System.out::println);

        HandlerHolder sHandler = new HandlerHolder(handler);
        sHandler.addCmdHandler(System.out::println);
        BiliLiveDanmakuClient client = new BiliLiveDanmakuClient(4767523L, sHandler);
        client.startSync(1);
    }

}