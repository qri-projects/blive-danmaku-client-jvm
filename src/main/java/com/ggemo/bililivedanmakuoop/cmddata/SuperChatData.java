package com.ggemo.bililivedanmakuoop.cmddata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.ggemo.bililivedanmakuoop.CmdData;
import com.ggemo.bililivedanmakuoop.util.StringUtil;
import lombok.*;

import java.io.Serializable;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuperChatData implements CmdData {
    private static final long serialVersionUID = 5866964280953314125L;

    @Data
    public static class MedalInfo{
        @JSONField(name = "icon_id")
        long iconId;

        @JSONField(name = "target_id")
        long targetId;

        String special;

        @JSONField(name = "anchor_uname")
        String anchorUserName;

        @JSONField(name = "anchor_roomid")
        String anchorRoomId;

        @JSONField(name = "medal_level")
        int medalLevel;

        @JSONField(name = "medal_name")
        String medalName;

        @JSONField(name = "medal_color")
        String medalColor;
    }

    @Data
    public static class UserInfo{
        @JSONField(name = "uname")
        String userName;

        @JSONField(name = "face")
        String userFaceImgUrl;

        /**
         * 头像边框
         */
        @JSONField(name = "face_frame")
        String userFaceFrameImgUrl;

        @JSONField(name = "guard_level")
        int guardLevel;

        @JSONField(name = "user_level")
        int userLevel;

        @JSONField(name = "level_color")
        String levelColor;

        @JSONField(name = "is_vip")
        boolean vipOrnot;

        @JSONField(name = "is_svip")
        boolean svipOrnot;

        @JSONField(name = "is_main_vip")
        boolean mainVipOrnot;

        String title;

        /**
         * 这啥
         */
        int manager;
    }

    @Data
    public static class Gift{
        int num;

        @JSONField(name = "gift_id")
        int giftId;

        @JSONField(name = "gift_name")
        String giftName;
    }

    String id;

    @JSONField(name = "uid")
    long userId;

    int price;

    int rate;

    String message;

    @JSONField(name = "message_jpn")
    String messageJpn;

    @JSONField(name = "background_image")
    String backgroundImageUrl;

    /**
     * #EDF5FF
      */
    @JSONField(name = "background_color")
    String backgroundColor;

    /**
     * 空的
     */
    @JSONField(name = "background_icon")
    String backgroundIcon;

    /**
     * #7497CD
     */
    @JSONField(name = "background_price_color")
    String backgroundPriceColor;

    @JSONField(name = "background_bottom_color")
    String backgroundBottomColor;

    /**
     * 这啥
     */
    long ts;

    String token;

    @JSONField(name = "medal_info")
    MedalInfo medalInfo;

    @JSONField(name = "user_info")
    UserInfo userInfo;

    /**
     * 单位应该是秒
     */
    @JSONField(name = "time")
    int lastTime;

    @JSONField(name = "start_time")
    long startTime;

    @JSONField(name = "end_time")
    long endTime;

    Gift gift;

    Long roomId;

    public static SuperChatData fromJSON(String json) {
        json = StringUtil.unicodeToString(json);
        return fromJSON(JSON.parseObject(json).getJSONObject("data"));
    }

    public static SuperChatData fromJSON(JSONObject jsonObject) {
        SuperChatData superCharData = jsonObject.toJavaObject(SuperChatData.class);

        superCharData.setBackgroundImageUrl(StringUtil.removeTransSlash(superCharData.getBackgroundImageUrl()));
        UserInfo userInfo = superCharData.getUserInfo();
        userInfo.setUserFaceImgUrl(StringUtil.removeTransSlash(userInfo.getUserFaceImgUrl()));
        userInfo.setUserFaceFrameImgUrl(StringUtil.removeTransSlash(userInfo.getUserFaceFrameImgUrl()));
        return superCharData;
    }

    public static SuperChatData fromGgemoJSON(JSONObject json){
        return json.toJavaObject(SuperChatData.class);
    }

    public static SuperChatData fromGgemoJSON(String json){
        return JSON.parseObject(json, SuperChatData.class);
    }

    public String toJSONString(){
        return JSON.toJSONString(this);
    }

    public static void main(String[] args) {
        String s = "{\"cmd\":\"SUPER_CHAT_MESSAGE\",\"data\":{\"id\":\"29708\",\"uid\":6646129,\"price\":100,\"rate\":1000,\"message\":\"\\u5965\\u65af\\u5154\\u592b\\u65af\\u57fa\\u751f\\u65e5\\u5feb\\u4e50\\uff01\\u6211\\u7231\\u6c99\\u6708\\u7231\\u7684\\u6b7b\\u53bb\\u6d3b\\u6765\\uff01\",\"message_jpn\":\"\",\"background_image\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/live\\/1aee2d5e9e8f03eed462a7b4bbfd0a7128bbc8b1.png\",\"background_color\":\"#FFF1C5\",\"background_icon\":\"\",\"background_price_color\":\"#ECCF75\",\"background_bottom_color\":\"#E2B52B\",\"ts\":1576073940,\"token\":\"82F6C8CF\",\"medal_info\":{\"icon_id\":0,\"target_id\":744713,\"special\":\"\",\"anchor_uname\":\"\\u96e8\\u5bab\\u6c34\\u5e0cChannel\",\"anchor_roomid\":4767523,\"medal_level\":6,\"medal_name\":\"\\u65e0\\u53e3\\u5e0c\",\"medal_color\":\"#5896de\"},\"user_info\":{\"uname\":\"\\u4e09\\u6c34\\u5316\\u6e05\\u6c81\\u4e09\\u5fc3\",\"face\":\"http:\\/\\/i2.hdslb.com\\/bfs\\/face\\/ad7c756e299839180626aafa1a40dbe43c9bb3de.jpg\",\"face_frame\":\"http:\\/\\/i0.hdslb.com\\/bfs\\/live\\/78e8a800e97403f1137c0c1b5029648c390be390.png\",\"guard_level\":3,\"user_level\":44,\"level_color\":\"#ff86b2\",\"is_vip\":1,\"is_svip\":1,\"is_main_vip\":1,\"title\":\"title-174-1\",\"manager\":0},\"time\":299,\"start_time\":1576073939,\"end_time\":1576074239,\"gift\":{\"num\":1,\"gift_id\":12000,\"gift_name\":\"\\u9192\\u76ee\\u7559\\u8a00\"}}}";
        System.out.println(SuperChatData.fromJSON(s));
    }

}
