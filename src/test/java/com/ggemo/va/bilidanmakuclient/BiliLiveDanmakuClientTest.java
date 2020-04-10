package com.ggemo.va.bilidanmakuclient;

import com.ggemo.va.bilidanmakuclient.handler.HandlerHolder;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class BiliLiveDanmakuClientTest {
    @Test
    public void test() {
        HandlerHolder handlerHolder = new HandlerHolder();
        handlerHolder.addUserCountHandler(x -> System.out.println("userCount: " + x));
        handlerHolder.addCmdHandler(System.out::println);
        BiliLiveDanmakuClient client = new BiliLiveDanmakuClient(336116L, handlerHolder);
        client.start();
    }
    @Test
    public void test2(){
        var d = new Date(1575854995000L);
        Calendar calender = Calendar.getInstance();
        calender.setTime(d);
        System.out.println(calender.get(Calendar.YEAR));
    }

    @Test
    public void test3(){
        byte[] bytes = new byte[]{0, 0, 1, 24, 0, 16, 0, 2, 0, 0, 0, 5, 0, 0, 0, 0, 120, -38, 68, -113, -79, 74, 59, 65, 16, -58, -17, 95, -4, 123, -97, -31, -85, -89, -104, -35, -67, -39, -67, -69, 46, 16, 99, 21, 27, -79, 90, 15, 49, 49, -126, 16, 99, 97, 82, -123, -128, -58, -128, 22, -94, 104, 42, 37, -115, 47, 32, 34, 118, -94, 47, 115, -36, 29, -66, -123, 108, -48, 100, 6, 62, 102, -32, -101, -17, -57, 68, -47, -65, 86, -76, 17, -123, -6, 31, 100, -116, -18, -55, 33, 50, 52, 27, -37, -19, -35, -3, -10, -50, 22, 8, -57, -125, -93, 83, 100, -34, 51, 41, -46, 66, -54, 58, -25, -76, 18, 82, -110, 88, 49, 28, 27, 23, 91, -75, -38, 116, 66, 76, -80, -90, -105, -70, -125, 78, 7, -60, -95, 115, 66, -7, 122, -9, -3, 49, -85, -25, -117, -14, -2, 28, -28, -107, 17, -105, 88, 97, 66, -7, 114, 83, 127, -35, 86, -97, 87, -43, -5, 67, -3, 52, 91, -7, 126, 79, 73, 49, 115, 64, 3, 57, 121, -91, 9, -43, -37, 99, -75, -72, -58, -33, 80, 92, 60, 23, -45, -53, 98, 58, 7, -59, -50, 58, -47, -122, 20, -117, -46, 86, 11, 1, -127, -19, -75, 16, -109, 36, 44, 46, 101, -62, -34, -120, -39, -12, 36, -60, -122, 72, 96, 25, 29, 80, -125, 81, -65, 79, 99, 12, -49, -112, -83, -97, 35, 116, -121, -56, -48, 74, 26, -90, -71, -103, 58, 76, -42, -42, -91, 112, 62, -7, 9, 0, 0, -1, -1, 8, 112, 98, 79};
        System.out.println(Arrays.toString(bytes));
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        System.out.println(new String(bytes, StandardCharsets.ISO_8859_1));
        System.out.println(new String(bytes, StandardCharsets.US_ASCII));
        System.out.println(new String(bytes, StandardCharsets.UTF_16));
        System.out.println(new String(bytes, StandardCharsets.UTF_16BE));
        System.out.println(new String(bytes, StandardCharsets.UTF_16LE));
        try {
            System.out.println(new String(bytes, "GBK"));
        }catch (Exception e){
            System.out.println(e.toString());
        }

        System.out.println(Arrays.toString("小黄瓜呀".getBytes(StandardCharsets.UTF_8)));
    }
}