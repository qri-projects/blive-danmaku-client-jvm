package com.ggemo.va.bililivedanmakuoop.msgdata

import com.alibaba.fastjson.annotation.JSONField
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject

class GuardBuyData : MsgData() {
    @JSONField(name = "uid")
    var userId: Long? = 0
    var userName: String? = null
    var guardLevel: Number? = 0
    var num: Number? = 0
    var price: Long? = 0

    /**
     * 名叫name, 结果类型是number...
     */
    var roleName: Long? = 0
    var giftName: String? = null
    var startTime: Long? = 0
    var endTime: Long? = 0

    companion object {
        fun fromJSON(json: String?): GuardBuyData {
            return fromJSON(JSON.parseObject(json).getJSONObject("data"))
        }

        fun fromJSON(json: JSONObject): GuardBuyData {
            return json.toJavaObject(GuardBuyData::class.java)
        }
    }
}