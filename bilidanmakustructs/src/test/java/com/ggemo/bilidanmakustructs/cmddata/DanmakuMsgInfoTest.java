package com.ggemo.bilidanmakustructs.cmddata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DanmakuMsgInfoTest {
    public static void main(String[] args) {
        String msg = "{\"cmd\":\"DANMU_MSG\",\"info\":[[0,1,25,14893055,1575289287230,1575281000,0,\"546ddb2d\",0,0,0],\"bgm声音好大\",[273417,\"月ノ美兎Official\",0,0,0,10000,1,\"\"],[6,\"金鱼人\",\"琉绮Ruki\",21403609,5805790,\"\",0],[17,0,6406234,\"\\u003e50000\"],[\"title-111-1\",\"title-111-1\"],0,3,null,{\"ts\":1575289287,\"ct\":\"68B227DB\"},null,null]}\n";
        JSONObject jsonObject = JSON.parseObject(msg);
        JSONArray data = jsonObject.getJSONArray("info");
        var o = new DanmakuData(data);
        System.out.println(o.toString());
    }
}