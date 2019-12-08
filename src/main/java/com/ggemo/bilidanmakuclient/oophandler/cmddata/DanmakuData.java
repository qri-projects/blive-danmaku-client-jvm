package com.ggemo.bilidanmakuclient.oophandler.cmddata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ggemo.bilidanmakuclient.oophandler.CmdData;
import lombok.*;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class DanmakuData implements CmdData {
    private static final String CMD = "DANMU_MSG";

    @Data
    public static class Info {
        int param0;
        int mode;
        int fontSize;
        String color;
        long time;
        long random;
        int param1;
        String uidCrc;
        int param2;
        int messageType;
        int bubble;

        public Info(int param0, int mode, int fontSize, String color, long time, long random, int param1, String uidCrc, int param2, int messageType, int bubble) {
            this.param0 = param0;
            this.mode = mode;
            this.fontSize = fontSize;
            this.color = color;
            this.time = time;
            this.random = random;
            this.param1 = param1;
            this.uidCrc = uidCrc;
            this.param2 = param2;
            this.messageType = messageType;
            this.bubble = bubble;
        }

        public Info(JSONArray o) {
            this(o.getInteger(0), o.getInteger(1), o.getInteger(2), parseColor(o.getLong(3)), o.getLong(4), o.getLong(5), o.getInteger(6), o.getString(7), o.getInteger(8), o.getInteger(9), o.getInteger(10));
        }
    }

    @Data
    public static class UserInfo {
        long userId;
        String userName;
        boolean adminOrnot;
        boolean vipOrnot;
        boolean svipOrnot;
        int urank;
        boolean mobileVertifiedOrnot;
        String userNameColor;

        public UserInfo(long userId, String userName, boolean adminOrnot, boolean vipOrnot, boolean svipOrnot, int urank, boolean mobileVertifiedOrnot, String userNameColor) {
            this.userId = userId;
            this.userName = userName;
            this.adminOrnot = adminOrnot;
            this.vipOrnot = vipOrnot;
            this.svipOrnot = svipOrnot;
            this.urank = urank;
            this.mobileVertifiedOrnot = mobileVertifiedOrnot;
            this.userNameColor = userNameColor;
        }

        public UserInfo(JSONArray o) {
            this(o.getInteger(0), o.getString(1), o.getBoolean(2), o.getBoolean(3), o.getBoolean(4), o.getInteger(5), o.getBoolean(6), o.getString(7));
        }
    }

    @Data
    public static class UserMedal {
        int medalLevel;
        String medalName;
        String userName;
        long roomId;
        String medalColor;
        String specialMedalName;
        boolean specialMedalOrnot;

        public UserMedal(int medalLevel, String medalName, String userName, long roomId, String medalColor, String specialMedalName, boolean specialMedalOrnot) {
            this.medalLevel = medalLevel;
            this.medalName = medalName;
            this.userName = userName;
            this.roomId = roomId;
            this.medalColor = medalColor;
            this.specialMedalName = specialMedalName;
            this.specialMedalOrnot = specialMedalOrnot;
        }

        public UserMedal(JSONArray o) {
            this(o.getInteger(0), o.getString(1), o.getString(2), o.getLong(3), parseColor(o.getLong(4)), o.getString(5), o.getBoolean(6));
        }
    }

    @Data
    public static class UserLevel {
        int userLevel;
        int param0;
        String userLevelColor;
        String userLevelRank;

        public UserLevel(int userLevel, int param0, String userLevelColor, String userLevelRank) {
            this.userLevel = userLevel;
            this.param0 = param0;
            this.userLevelColor = userLevelColor;
            this.userLevelRank = userLevelRank;
        }

        public UserLevel(JSONArray o) {
            this(o.getInteger(0), o.getInteger(1), parseColor(o.getLong(2)), o.getString(3));
        }
    }

    @Data
    public static class Title {
        String oldTitle;
        String newTitle;

        public Title(String oldTitle, String newTitle) {
            this.oldTitle = oldTitle;
            this.newTitle = newTitle;
        }

        public Title(JSONArray o) {
            this(o.getString(0), o.getString(1));
        }
    }

    /**
     * 弹幕相关info
     */
    private Info info;

    /**
     * 弹幕内容
     */
    private String content;

    /**
     * 用户信息
     */
    private UserInfo userInfo;

    /**
     * 狗牌信息
     */
    private UserMedal userMedal;

    /**
     * 用户等级信息
     */
    private UserLevel userLevel;

    /**
     * title
     */
    private Title title;

    int param0;

    /**
     * 舰队类型,0为非舰队, 1总督, 2提督, 3舰长
     */
    int privilegeType;

    Object param1;

    Object param2;
    Object param3;
    Object param4;

    public DanmakuData(JSONArray o) {
        this(new Info(o.getJSONArray(0)), o.getString(1), new UserInfo(o.getJSONArray(2)), new UserMedal(o.getJSONArray(3)), new UserLevel(o.getJSONArray(4)), new Title(o.getJSONArray(5)), o.getInteger(6), o.getInteger(7), o.getObject(8, Object.class), o.getObject(9, Object.class), o.getObject(10, Object.class), o.getObject(11, Object.class));
    }

    public static DanmakuData fromJson(JSONArray o){
        return new DanmakuData(o);
    }

    public String toJSONString(){
        return JSON.toJSONString(this);
    }

    public static String parseColor(long i){
        String s = Long.toHexString(i);
        int len = s.length();
        if(len < 6){
            s = StringUtils.repeat("0", 6 - len) + s;
        }
        s = "#" + s;
        return s;
    }
}
