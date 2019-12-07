package com.ggemo.bilidanmakustructs.cmddata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.ggemo.bilidanmakustructs.CmdData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class GuardBuyData implements CmdData {
    @JSONField(name = "uid")
    long userId;

    @JSONField(name = "username")
    String userName;

    @JSONField(name = "guard_level")
    int guardLevel;

    int num;

    long price;

    /**
     * 名叫name, 结果类型是number...
     */
    @JSONField(name = "role_name")
    long roleName;

    @JSONField(name = "gift_name")
    String giftName;

    @JSONField(name = "start_time")
    long startTime;

    @JSONField(name = "end_time")
    long endTime;

    public static GuardBuyData fromJson(String json){
        return JSON.parseObject(json, GuardBuyData.class);
    }
    public static GuardBuyData fromJson(JSONObject json){
        return json.toJavaObject(GuardBuyData.class);
    }
}
