package com.ggemo.bilidanmakuclient.oophandler.cmddata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.ggemo.bilidanmakuclient.oophandler.CmdData;
import com.ggemo.bilidanmakuclient.oophandler.util.StringUtil;
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

    public static SendGiftData fromJson(String json) {
        json = StringUtil.unicodeToString(json);
        SendGiftData sendGiftData = JSON.parseObject(json, SendGiftData.class);
        sendGiftData.setUserFaceImgUrl(StringUtil.removeTransSlash(sendGiftData.getUserFaceImgUrl()));
        return sendGiftData;
    }

    public static SendGiftData fromJson(JSONObject jsonObject) {
        var sendGiftData = jsonObject.toJavaObject(SendGiftData.class);
        sendGiftData.setUserFaceImgUrl(StringUtil.removeTransSlash(sendGiftData.getUserFaceImgUrl()));
        return sendGiftData;
    }

    public String toJSONString(){
        return JSON.toJSONString(this);
    }

    public static void main(String[] args) {
        String str = "{\"cmd\":\"SEND_GIFT\",\"data\":{\"giftName\":\"\\u6253call\",\"num\":1,\"uname\":\"\\u6674\\u592912\\u5ea6\",\"face\":\"http:\\/\\/i2.hdslb.com\\/bfs\\/face\\/410da90a5fb14728a058f70acc618b7fecf894c9.jpg\",\"guard_level\":0,\"rcost\":19729847,\"uid\":60897589,\"top_list\":[],\"timestamp\":1575717640,\"giftId\":30437,\"giftType\":0,\"action\":\"\\u6295\\u5582\",\"super\":0,\"super_gift_num\":30,\"super_batch_gift_num\":30,\"batch_combo_id\":\"batch:gift:combo_id:60897589:282994:30437:1:1575717635.7649\",\"price\":100,\"rnd\":\"BCC36413-CCF9-4175-AB79-6C404A854EC6\",\"newMedal\":0,\"newTitle\":0,\"medal\":[],\"title\":\"\",\"beatId\":\"\",\"biz_source\":\"live\",\"metadata\":\"\",\"remain\":0,\"gold\":0,\"silver\":0,\"eventScore\":0,\"eventNum\":0,\"smalltv_msg\":[],\"specialGift\":null,\"notice_msg\":[],\"capsule\":null,\"addFollow\":0,\"effect_block\":0,\"coin_type\":\"gold\",\"total_coin\":100,\"effect\":0,\"broadcast_id\":0,\"draw\":0,\"crit_prob\":0,\"combo_send\":{\"uid\":60897589,\"uname\":\"\\u6674\\u592912\\u5ea6\",\"gift_num\":1,\"combo_num\":30,\"gift_id\":30437,\"gift_name\":\"\\u6253call\",\"action\":\"\\u6295\\u5582\",\"combo_id\":\"gift:combo_id:60897589:282994:30437:1575717635.764\",\"send_master\":{\"uid\":282994,\"uname\":\"\\u6ce0\\u9e22yousa\",\"room_id\":47377}},\"batch_combo_send\":{\"uid\":60897589,\"uname\":\"\\u6674\\u592912\\u5ea6\",\"gift_num\":1,\"batch_combo_num\":30,\"gift_id\":30437,\"gift_name\":\"\\u6253call\",\"action\":\"\\u6295\\u5582\",\"batch_combo_id\":\"batch:gift:combo_id:60897589:282994:30437:1:1575717635.7649\",\"send_master\":{\"uid\":282994,\"uname\":\"\\u6ce0\\u9e22yousa\",\"room_id\":47377}},\"tag_image\":\"\",\"send_master\":{\"uid\":282994,\"uname\":\"\\u6ce0\\u9e22yousa\",\"room_id\":47377},\"is_first\":false}}";
        var data = SendGiftData.fromJson(JSON.parseObject(str).getJSONObject("data"));
        System.out.println(data);
    }
}
