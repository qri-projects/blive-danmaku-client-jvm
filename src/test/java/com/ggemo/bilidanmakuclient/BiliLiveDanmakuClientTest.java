package com.ggemo.bilidanmakuclient;

import com.ggemo.bilidanmakuclient.handler.HandlerHolder;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class BiliLiveDanmakuClientTest {
    @Test
    public void test() {
        HandlerHolder handlerHolder = new HandlerHolder();
        handlerHolder.addUserCountHandler(x -> System.out.println("userCount: " + x));
        handlerHolder.addCmdHandler(System.out::println);
        BiliLiveDanmakuClient client = new BiliLiveDanmakuClient(4767523L, handlerHolder);
        client.start();
    }
    @Test
    public void test2(){
        var d = new Date(1575854995000L);
        Calendar calender = Calendar.getInstance();
        calender.setTime(d);
        System.out.println(calender.get(Calendar.YEAR));
    }
}