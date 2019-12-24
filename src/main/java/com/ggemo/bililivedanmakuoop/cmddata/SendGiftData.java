package com.ggemo.bililivedanmakuoop.cmddata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.ggemo.bililivedanmakuoop.CmdData;
import com.ggemo.bililivedanmakuoop.util.StringUtil;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendGiftData implements CmdData {
    private static final long serialVersionUID = -268502141127829004L;

    @Data
    public static class SendMaster {
        @JSONField(name = "uid")
        long userId;

        @JSONField(name = "uname")
        String userName;

        @JSONField(name = "room_id")
        Long roomId;
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

    String beatId;

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

    Long roomId;

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

    public static void main(String[] args) {
        String s = "{\"cmd\":\"SEND_GIFT\",\"data\":{\"giftName\":\"\\u8282\\u594f\\u98ce\\u66b4\",\"num\":1,\"uname\":\"\\u6708\\u4e4b\\u971c\",\"face\":\"http:\\/\\/i0.hdslb.com\\/bfs\\/face\\/cf86aa943af3b5a6e14bac814b3e031d072fb797.jpg\",\"guard_level\":3,\"rcost\":12745796,\"uid\":3274135,\"top_list\":[],\"timestamp\":1576070987,\"giftId\":39,\"giftType\":0,\"action\":\"\\u6295\\u5582\",\"super\":0,\"super_gift_num\":1,\"super_batch_gift_num\":1,\"batch_combo_id\":\"batch:gift:combo_id:3274135:128912828:39:1:1576070987.3551\",\"price\":100000,\"rnd\":\"1576070770\",\"newMedal\":0,\"newTitle\":0,\"medal\":[],\"title\":\"\",\"beatId\":\"u77658\",\"biz_source\":\"live\",\"metadata\":\"\\u6c99\\u6708\\u5929\\u4e0b\\u7b2c\\u4e00\\u53ef\\u7231\\uff01\",\"remain\":0,\"gold\":0,\"silver\":0,\"eventScore\":0,\"eventNum\":0,\"smalltv_msg\":[],\"specialGift\":{\"time\":90,\"id\":1745578377388},\"notice_msg\":[],\"capsule\":null,\"addFollow\":0,\"effect_block\":0,\"coin_type\":\"gold\",\"total_coin\":80000,\"effect\":0,\"broadcast_id\":0,\"draw\":2,\"crit_prob\":0,\"combo_send\":{\"uid\":3274135,\"uname\":\"\\u6708\\u4e4b\\u971c\",\"gift_num\":1,\"combo_num\":1,\"gift_id\":39,\"gift_name\":\"\\u8282\\u594f\\u98ce\\u66b4\",\"action\":\"\\u6295\\u5582\",\"combo_id\":\"gift:combo_id:3274135:128912828:39:1576070987.3543\",\"send_master\":null},\"batch_combo_send\":{\"uid\":3274135,\"uname\":\"\\u6708\\u4e4b\\u971c\",\"gift_num\":1,\"batch_combo_num\":1,\"gift_id\":39,\"gift_name\":\"\\u8282\\u594f\\u98ce\\u66b4\",\"action\":\"\\u6295\\u5582\",\"batch_combo_id\":\"batch:gift:combo_id:3274135:128912828:39:1:1576070987.3551\",\"send_master\":null},\"tag_image\":\"\",\"send_master\":null,\"is_first\":true}}";
        System.out.println(SendGiftData.fromJSON(s));
    }
}
