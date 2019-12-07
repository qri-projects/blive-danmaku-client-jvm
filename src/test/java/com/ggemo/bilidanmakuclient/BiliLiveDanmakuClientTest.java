package com.ggemo.bilidanmakuclient;

import com.ggemo.bilidanmakuclient.handler.HandlerHolder;
import org.junit.Test;

import static org.junit.Assert.*;

public class BiliLiveDanmakuClientTest {
    @Test
    public void test() {
        HandlerHolder handlerHolder = new HandlerHolder();
        handlerHolder.addUserCountHandler(x -> System.out.println("userCount: " + x));
        handlerHolder.addCmdHandler(System.out::println);
        BiliLiveDanmakuClient client = new BiliLiveDanmakuClient(4767523L, handlerHolder);
        client.startSync(3);
    }
}