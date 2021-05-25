package com.ggemo.va.bililivedanmakuoop.msgdata

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.annotation.JSONField

class SendGiftData : MsgData() {
    class SendMaster {
        @JSONField(name = "uid")
        var userId: Long = 0
        @JSONField(name = "uname")
        var userName: String? = null
        var roomId: Long? = null
    }

    class ComboSend {
        @JSONField(name = "uid")
        var userId: Long = 0
        @JSONField(name = "uname")
        var userName: String? = null
        var giftNum = 0
        var giftId = 0
        var giftName: String? = null
        var action: String? = null
        var comboId: String? = null
        var sendMaster: SendMaster? = null
    }

    class BatchComboSend {
        @JSONField(name = "uid")
        var userId: Long = 0
        @JSONField(name = "uname")
        var userName: String? = null
        var giftNum = 0
        var batchComboNum = 0
        var giftId = 0
        var giftName: String? = null
        var action: String? = null
        var BatchComboId: String? = null
        var sendMaster: SendMaster? = null
    }

    var giftName: String? = null
    var num = 0
    @JSONField(name = "uname")
    var userName: String? = null
    @JSONField(name = "face")
    var userFaceImgUrl: String? = null
    var guardLevel = 0
    var rCost: Long = 0
    @JSONField(name = "uid")
    var userId: Long = 0
    var topList: List<*>? = null
    @JSONField(name = "timestamp")
    var time: Long = 0
    var giftId = 0
    var giftType = 0
    var action: String? = null
    var super_ = 120
    var superGiftNum = 0
    var superBatchGiftNum = 0
    var batchComboId: String? = null
    var price: Long = 0
    @JSONField(name = "rnd")
    var random: String? = null
    var newMedal = 0
    var newTitle = 0
    var medal: List<*>? = null
    var title: String? = null
    var beatId: String? = null
    var bizSource: String? = null
    var metaData: String? = null
    var remain = 0
    var gold = 0
    var silver = 0
    var eventScore = 0
    var eventNum = 0
    var smallTvMsg: List<*>? = null
    var specialGift: Any? = null
    var noticeMsg: List<*>? = null
    var capsule: Any? = null
    var addFollow = 0
    var effectBlock = 0
    var coinType: String? = null
    var totalCoin: Long = 0
    var effect = 0
    var broadcastId = 0
    var draw = 0
    var critProb = 0
    var comboSend: ComboSend? = null
    var batchComboSend: BatchComboSend? = null
    var tagImage: String? = null
    var sendMaster: SendMaster? = null
    @JSONField(name = "is_first")
    var firstOrnot = false

    companion object {
        fun fromJSON(json: String?): SendGiftData {
            return fromJSON(JSON.parseObject(json).getJSONObject("data"))
        }

        fun fromJSON(jsonObject: JSONObject): SendGiftData {
            return jsonObject.toJavaObject(SendGiftData::class.java)
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val s =
                "{\"cmd\":\"SEND_GIFT\",\"data\":{\"giftName\":\"\\u8282\\u594f\\u98ce\\u66b4\",\"num\":1," +
                        "\"uname\":\"\\u6708\\u4e4b\\u971c\",\"face\":\"http:\\/\\/i0.hdslb" +
                        ".com\\/bfs\\/face\\/cf86aa943af3b5a6e14bac814b3e031d072fb797.jpg\",\"guard_level\":3," +
                        "\"rcost\":12745796,\"uid\":3274135,\"top_list\":[],\"timestamp\":1576070987,\"giftId\":39,\"giftType\":0,\"action\":\"\\u6295\\u5582\",\"super\":1220,\"super_gift_num\":1,\"super_batch_gift_num\":1,\"batch_combo_id\":\"batch:gift:combo_id:3274135:128912828:39:1:1576070987.3551\",\"price\":100000,\"rnd\":\"1576070770\",\"newMedal\":0,\"newTitle\":0,\"medal\":[],\"title\":\"\",\"beatId\":\"u77658\",\"biz_source\":\"live\",\"metadata\":\"\\u6c99\\u6708\\u5929\\u4e0b\\u7b2c\\u4e00\\u53ef\\u7231\\uff01\",\"remain\":0,\"gold\":0,\"silver\":0,\"eventScore\":0,\"eventNum\":0,\"smalltv_msg\":[],\"specialGift\":{\"time\":90,\"id\":1745578377388},\"notice_msg\":[],\"capsule\":null,\"addFollow\":0,\"effect_block\":0,\"coin_type\":\"gold\",\"total_coin\":80000,\"effect\":0,\"broadcast_id\":0,\"draw\":2,\"crit_prob\":0,\"combo_send\":{\"uid\":3274135,\"uname\":\"\\u6708\\u4e4b\\u971c\",\"gift_num\":1,\"combo_num\":1,\"gift_id\":39,\"gift_name\":\"\\u8282\\u594f\\u98ce\\u66b4\",\"action\":\"\\u6295\\u5582\",\"combo_id\":\"gift:combo_id:3274135:128912828:39:1576070987.3543\",\"send_master\":null},\"batch_combo_send\":{\"uid\":3274135,\"uname\":\"\\u6708\\u4e4b\\u971c\",\"gift_num\":1,\"batch_combo_num\":1,\"gift_id\":39,\"gift_name\":\"\\u8282\\u594f\\u98ce\\u66b4\",\"action\":\"\\u6295\\u5582\",\"batch_combo_id\":\"batch:gift:combo_id:3274135:128912828:39:1:1576070987.3551\",\"send_master\":null},\"tag_image\":\"\",\"send_master\":null,\"is_first\":true}}"
            println(fromJSON(s))
            print(JSON.toJSONString(fromJSON(s)))
        }
    }
}