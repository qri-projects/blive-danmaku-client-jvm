package com.ggemo.bilidanmakustructs.cmddata;

import com.alibaba.fastjson.JSON;
import com.ggemo.bilidanmakustructs.util.StringUtil;
import org.junit.Test;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class SendGiftDataTest {
    public static void main(String[] args) {
        String msg = "{\"cmd\":\"SEND_GIFT\",\"data\":{\"giftName\":\"\\u8fa3\\u6761\",\"num\":2,\"uname\":\"\\u6d1b\\u6728\\u4e4b\\u6b87\",\"face\":\"http:\\/\\/i1.hdslb.com\\/bfs\\/face\\/bd631ac70632674d32de92bbbd5deb8f175b1284.jpg\",\"guard_level\":0,\"rcost\":10499515,\"uid\":8218521,\"top_list\":[],\"timestamp\":1575286717,\"giftId\":1,\"giftType\":0,\"action\":\"\\u5582\\u98df\",\"super\":0,\"super_gift_num\":0,\"super_batch_gift_num\":0,\"batch_combo_id\":\"\",\"price\":100,\"rnd\":\"1575286683\",\"newMedal\":0,\"newTitle\":0,\"medal\":[],\"title\":\"\",\"beatId\":\"0\",\"biz_source\":\"live\",\"metadata\":\"\",\"remain\":0,\"gold\":0,\"silver\":0,\"eventScore\":0,\"eventNum\":0,\"smalltv_msg\":[],\"specialGift\":null,\"notice_msg\":[],\"capsule\":null,\"addFollow\":0,\"effect_block\":1,\"coin_type\":\"silver\",\"total_coin\":200,\"effect\":0,\"broadcast_id\":0,\"draw\":0,\"crit_prob\":0,\"tag_image\":\"\",\"send_master\":null,\"is_first\":true}}\n";
        var obj = JSON.parseObject(msg);
        var data = obj.getJSONObject("data");
        System.out.println(data.toJSONString());
        SendGiftData o = data.toJavaObject(SendGiftData.class);
        System.out.println(o.toString());
    }

    @Test
    public void test(){
        String msg = "{\"cmd\":\"SEND_GIFT\",\"data\":{\"giftName\":\"\\u8fa3\\u6761\",\"num\":2,\"uname\":\"\\u6d1b\\u6728\\u4e4b\\u6b87\",\"face\":\"http:\\/\\/i1.hdslb.com\\/bfs\\/face\\/bd631ac70632674d32de92bbbd5deb8f175b1284.jpg\",\"guard_level\":0,\"rcost\":10499515,\"uid\":8218521,\"top_list\":[],\"timestamp\":1575286717,\"giftId\":1,\"giftType\":0,\"action\":\"\\u5582\\u98df\",\"super\":0,\"super_gift_num\":0,\"super_batch_gift_num\":0,\"batch_combo_id\":\"\",\"price\":100,\"rnd\":\"1575286683\",\"newMedal\":0,\"newTitle\":0,\"medal\":[],\"title\":\"\",\"beatId\":\"0\",\"biz_source\":\"live\",\"metadata\":\"\",\"remain\":0,\"gold\":0,\"silver\":0,\"eventScore\":0,\"eventNum\":0,\"smalltv_msg\":[],\"specialGift\":null,\"notice_msg\":[],\"capsule\":null,\"addFollow\":0,\"effect_block\":1,\"coin_type\":\"silver\",\"total_coin\":200,\"effect\":0,\"broadcast_id\":0,\"draw\":0,\"crit_prob\":0,\"tag_image\":\"\",\"send_master\":null,\"is_first\":true}}\n";
        msg = msg.replaceAll("\\\\/", "/");
        System.out.println(StringUtil.unicodeToString(msg));
    }
}