package com.ggemo.bilidanmakuclient.oop.cmddata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.ggemo.bilidanmakuclient.oop.CmdData;
import com.ggemo.bilidanmakuclient.oop.util.StringUtil;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuperChatData implements CmdData {
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
        long giftName;
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
}
