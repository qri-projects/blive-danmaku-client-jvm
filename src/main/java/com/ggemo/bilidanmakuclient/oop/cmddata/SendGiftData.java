package com.ggemo.bilidanmakuclient.oop.cmddata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.ggemo.bilidanmakuclient.oop.CmdData;
import com.ggemo.bilidanmakuclient.oop.util.StringUtil;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendGiftData implements CmdData {
    @Data
    public static class SendMaster {
        @JSONField(name = "uid")
        long userId;

        @JSONField(name = "uname")
        String userName;

        @JSONField(name = "room_id")
        long roomId;
    }

    @Data
    public static class ComboSend {
        @JSONField(name = "uid")
        long userId;

        @JSONField(name = "uname")
        String userName;

        @JSONField(name = "gift_num")
        int giftNum;

        @JSONField(name = "gift_id")
        int giftId;

        @JSONField(name = "gift_name")
        String giftName;

        String action;

        @JSONField(name = "combo_id")
        String comboId;

        @JSONField(name = "send_master")
        SendMaster sendMaster;
    }

    @Data
    public static class BatchComboSend {
        @JSONField(name = "uid")
        long userId;

        @JSONField(name = "uname")
        String userName;

        @JSONField(name = "gift_num")
        int giftNum;

        @JSONField(name = "batch_combo_num")
        int batchComboNum;

        @JSONField(name = "gift_id")
        int giftId;

        @JSONField(name = "gift_name")
        String giftName;

        String action;

        @JSONField(name = "batch_combo_id")
        String BatchComboId;

        @JSONField(name = "send_master")
        SendMaster sendMaster;
    }

    String giftName;

    int num;

    @JSONField(name = "uname")
    String userName;

    @JSONField(name = "face")
    String userFaceImgUrl;

    @JSONField(name = "guard_level")
    int guardLevel;

    @JSONField(name = "rcost")
    long rCost;

    @JSONField(name = "uid")
    long userId;

    @JSONField(name = "top_list")
    List topList;

    @JSONField(name = "timestamp")
    long time;

    int giftId;

    int giftType;

    String action;

    @JSONField(name = "super")
    int super_;

    @JSONField(name = "super_gift_num")
    int superGiftNum;

    @JSONField(name = "super_batch_gift_num")
    int superBatchGiftNum;

    @JSONField(name = "batch_combo_id")
    String batchComboId;

    long price;

    @JSONField(name = "rnd")
    String random;

    int newMedal;

    int newTitle;

    List medal;

    String title;

    int beatId;

    @JSONField(name = "biz_source")
    String bizSource;

    @JSONField(name = "matadata")
    String metaData;

    int remain;

    int gold;

    int silver;

    int eventScore;

    int eventNum;

    @JSONField(name = "smalltv_msg")
    List smallTvMsg;

    Object specialGift;

    @JSONField(name = "notice_msg")
    List noticeMsg;

    Object capsule;

    int addFollow;

    @JSONField(name = "effect_block")
    int effectBlock;

    @JSONField(name = "coin_type")
    String coinType;

    @JSONField(name = "total_coin")
    long totalCoin;

    int effect;

    @JSONField(name = "broadcast_id")
    int broadcastId;

    int draw;

    @JSONField(name = "crit_prob")
    int critProb;

    @JSONField(name = "combo_send")
    ComboSend comboSend;

    @JSONField(name = "batch_combo_send")
    BatchComboSend batchComboSend;

    @JSONField(name = "tag_image")
    String tagImage;

    @JSONField(name = "send_master")
    SendMaster sendMaster;

    @JSONField(name = "is_first")
    boolean firstOrnot;

    public static SendGiftData fromJSON(String json) {
        json = StringUtil.unicodeToString(json);
        return fromJSON(JSON.parseObject(json).getJSONObject("data"));
    }

    public static SendGiftData fromJSON(JSONObject jsonObject) {
        var sendGiftData = jsonObject.toJavaObject(SendGiftData.class);
        sendGiftData.setUserFaceImgUrl(StringUtil.removeTransSlash(sendGiftData.getUserFaceImgUrl()));
        sendGiftData.setTime(sendGiftData.getTime() * 1000);
        return sendGiftData;
    }

    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public static SendGiftData fromGgemoJSON(JSONObject json){
        var res = json.toJavaObject(SendGiftData.class);
        res.setTime(res.getTime() * 1000);
        return res;
}

    public static SendGiftData fromGgemoJSON(String json){
        var res = JSON.parseObject(json, SendGiftData.class);
        res.setTime(res.getTime());
        return res;
    }
}
