package com.ggemo.va.bililivedanmakuoop.cmddata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ggemo.va.bililivedanmakuoop.CmdData;

import org.apache.commons.lang.StringUtils;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class DanmakuData implements CmdData {
    private static final String CMD = "DANMU_MSG";
    private static final long serialVersionUID = -198478175324866238L;

    @Data
    @NoArgsConstructor
    public static class Info implements Serializable {
        private static final long serialVersionUID = 5551992099455324583L;

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
    @NoArgsConstructor
    public static class UserInfo implements Serializable  {
        private static final long serialVersionUID = -4600817537319374039L;
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
            this();
            if(o.size() == 8) {
                this.setUserId(o.getLong(0));
                this.setUserName(o.getString(1));
                this.setAdminOrnot(o.getBoolean(2));
                this.setVipOrnot(o.getBoolean(3));
                this.setSvipOrnot(o.getBoolean(4));
                this.setUrank(o.getInteger(5));
                this.setMobileVertifiedOrnot(o.getBoolean(6));
                this.setUserNameColor(o.getString(7));
            }
        }
    }

    @Data
    @NoArgsConstructor
    public static class UserMedal implements Serializable  {
        private static final long serialVersionUID = -5479233442964368371L;
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
            this();
            if(o.size() == 7) {
                this.setMedalLevel(o.getInteger(0));
                this.setMedalName(o.getString(1));
                this.setUserName(o.getString(2));
                this.setRoomId(o.getLong(3));
                this.setMedalColor(parseColor(o.getLong(4)));
                this.setSpecialMedalName(o.getString(5));
                this.setSpecialMedalOrnot(o.getBoolean(6));
            }
//            this(o.getInteger(0), o.getString(1), o.getString(2), o.getLong(3), parseColor(o.getLong(4)), o.getString(5), o.getBoolean(6));
        }
    }

    @Data
    @NoArgsConstructor
    public static class UserLevel implements Serializable  {
        private static final long serialVersionUID = -6033907377652842L;
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
            this();
            if(o.size() == 4){
                this.setUserLevel(o.getInteger(0));
                this.setParam0(o.getInteger(1));
                this.setUserLevelColor(parseColor(o.getLong(2)));
                this.setUserLevelRank(o.getString(3));
            }
        }
    }

    @Data
    @NoArgsConstructor
    public static class Title implements Serializable  {
        private static final long serialVersionUID = 7330787924476183960L;
        String oldTitle;
        String newTitle;

        public Title(String oldTitle, String newTitle) {
            this.oldTitle = oldTitle;
            this.newTitle = newTitle;
        }

        public Title(JSONArray o) {
            this();
            if(o.size() == 2) {
                this.setOldTitle(o.getString(0));
                this.setNewTitle(o.getString(1));
            }
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

    Long roomId;

    public DanmakuData(Info info, String content, UserInfo userInfo, UserMedal userMedal, UserLevel userLevel, Title title, int param0, int privilegeType, Object param1, Object param2, Object param3, Object param4) {
        this.info = info;
        this.content = content;
        this.userInfo = userInfo;
        this.userMedal = userMedal;
        this.userLevel = userLevel;
        this.title = title;
        this.param0 = param0;
        this.privilegeType = privilegeType;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
    }

    public DanmakuData(JSONArray o) {
        this(new Info(o.getJSONArray(0)), o.getString(1), new UserInfo(o.getJSONArray(2)), new UserMedal(o.getJSONArray(3)), new UserLevel(o.getJSONArray(4)), new Title(o.getJSONArray(5)), o.getInteger(6), o.getInteger(7), o.getObject(8, Object.class), o.getObject(9, Object.class), o.getObject(10, Object.class), o.getObject(11, Object.class));
    }

    public static DanmakuData fromJSON(JSONArray o){
        return new DanmakuData(o);
    }

    public static DanmakuData fromJSON(String json){
        return fromJSON(JSON.parseObject(json).getJSONArray("info"));
    }

    public static DanmakuData fromGgemoJSON(JSONObject json){
        return json.toJavaObject(DanmakuData.class);
    }

    public static DanmakuData fromGgemoJSON(String json){
        return JSON.parseObject(json, DanmakuData.class);
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

    public static void main(String[] args) {
        String json = "{\"cmd\":\"SEND_GIFT\",\"data\":{\"giftName\":\"\\u8282\\u594f\\u98ce\\u66b4\",\"num\":1,\"uname\":\"\\u6708\\u4e4b\\u971c\",\"face\":\"http:\\/\\/i0.hdslb.com\\/bfs\\/face\\/cf86aa943af3b5a6e14bac814b3e031d072fb797.jpg\",\"guard_level\":3,\"rcost\":12745796,\"uid\":3274135,\"top_list\":[],\"timestamp\":1576070987,\"giftId\":39,\"giftType\":0,\"action\":\"\\u6295\\u5582\",\"super\":0,\"super_gift_num\":1,\"super_batch_gift_num\":1,\"batch_combo_id\":\"batch:gift:combo_id:3274135:128912828:39:1:1576070987.3551\",\"price\":100000,\"rnd\":\"1576070770\",\"newMedal\":0,\"newTitle\":0,\"medal\":[],\"title\":\"\",\"beatId\":\"u77658\",\"biz_source\":\"live\",\"metadata\":\"\\u6c99\\u6708\\u5929\\u4e0b\\u7b2c\\u4e00\\u53ef\\u7231\\uff01\",\"remain\":0,\"gold\":0,\"silver\":0,\"eventScore\":0,\"eventNum\":0,\"smalltv_msg\":[],\"specialGift\":{\"time\":90,\"id\":1745578377388},\"notice_msg\":[],\"capsule\":null,\"addFollow\":0,\"effect_block\":0,\"coin_type\":\"gold\",\"total_coin\":80000,\"effect\":0,\"broadcast_id\":0,\"draw\":2,\"crit_prob\":0,\"combo_send\":{\"uid\":3274135,\"uname\":\"\\u6708\\u4e4b\\u971c\",\"gift_num\":1,\"combo_num\":1,\"gift_id\":39,\"gift_name\":\"\\u8282\\u594f\\u98ce\\u66b4\",\"action\":\"\\u6295\\u5582\",\"combo_id\":\"gift:combo_id:3274135:128912828:39:1576070987.3543\",\"send_master\":null},\"batch_combo_send\":{\"uid\":3274135,\"uname\":\"\\u6708\\u4e4b\\u971c\",\"gift_num\":1,\"batch_combo_num\":1,\"gift_id\":39,\"gift_name\":\"\\u8282\\u594f\\u98ce\\u66b4\",\"action\":\"\\u6295\\u5582\",\"batch_combo_id\":\"batch:gift:combo_id:3274135:128912828:39:1:1576070987.3551\",\"send_master\":null},\"tag_image\":\"\",\"send_master\":null,\"is_first\":true}}";
        System.out.println(json);
        var danmakuData = DanmakuData.fromJSON(json);
        System.out.println(danmakuData);
    }
}
