package com.ggemo.va.bililivedanmakuoop.msgdata

import com.alibaba.fastjson.annotation.JSONField
import com.alibaba.fastjson.JSON
import com.ggemo.va.bililivedanmakuoop.util.StringUtil
import com.alibaba.fastjson.JSONObject
import kotlin.jvm.JvmStatic

class SuperChatData : MsgData() {
    class MedalInfo {
        var iconId: Long = 0
        var targetId: Long = 0
        var special: String? = null
        var anchorUserName: String? = null
        var anchorRoomId: String? = null
        var medalLevel = 0
        var medalName: String? = null
        var medalColor: String? = null
    }

    class UserInfo {
        @JSONField(name = "uname")
        var userName: String? = null

        @JSONField(name = "face")
        var userFaceImgUrl: String? = null

        /**
         * 头像边框
         */
        @JSONField(name = "face_frame")
        var userFaceFrameImgUrl: String? = null
        var guardLevel = 0
        var userLevel = 0
        var levelColor: String? = null

        @JSONField(name = "is_vip")
        var vipOrnot = false

        @JSONField(name = "is_svip")
        var svipOrnot = false

        @JSONField(name = "is_main_vip")
        var mainVipOrnot = false
        var title: String? = null
        var manager = 0
    }

    class Gift {
        var num = 0
        var giftId = 0
        var giftName: String? = null
    }

    var id: String? = null

    @JSONField(name = "uid")
    var userId: Long = 0
    var price = 0
    var rate = 0
    var message: String? = null
    var messageJpn: String? = null

    @JSONField(name = "background_image")
    var backgroundImageUrl: String? = null

    /**
     * #EDF5FF
     */
    var backgroundColor: String? = null

    /**
     * 空的
     */
    var backgroundIcon: String? = null

    /**
     * #7497CD
     */
    var backgroundPriceColor: String? = null
    var backgroundBottomColor: String? = null

    /**
     * 这啥
     */
    var ts: Long = 0
    var token: String? = null
    var medalInfo: MedalInfo? = null
    var userInfo: UserInfo? = null

    /**
     * 单位应该是秒
     */
    @JSONField(name = "time")
    var lastTime = 0
    var startTime: Long = 0
    var endTime: Long = 0
    var gift: Gift? = null

    companion object {
        fun fromJSON(json: String?): SuperChatData {
            return fromJSON(JSON.parseObject(json).getJSONObject("data"))
        }

        fun fromJSON(jsonObject: JSONObject): SuperChatData {
            val superCharData = jsonObject.toJavaObject(SuperChatData::class.java)
            superCharData.backgroundImageUrl = superCharData.backgroundImageUrl?.let { StringUtil.removeTransSlash(it) }
            val userInfo = superCharData.userInfo
            userInfo?.userFaceImgUrl = userInfo?.userFaceImgUrl?.let { StringUtil.removeTransSlash(it) }
            userInfo?.userFaceFrameImgUrl = userInfo?.userFaceFrameImgUrl?.let { StringUtil.removeTransSlash(it) }
            return superCharData
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val s =
                "{\"cmd\":\"SUPER_CHAT_MESSAGE\",\"data\":{\"id\":\"29708\",\"uid\":6646129,\"price\":100,\"rate\":1000,\"message\":\"\\u5965\\u65af\\u5154\\u592b\\u65af\\u57fa\\u751f\\u65e5\\u5feb\\u4e50\\uff01\\u6211\\u7231\\u6c99\\u6708\\u7231\\u7684\\u6b7b\\u53bb\\u6d3b\\u6765\\uff01\",\"message_jpn\":\"\",\"background_image\":\"https:\\/\\/i0.hdslb.com\\/bfs\\/live\\/1aee2d5e9e8f03eed462a7b4bbfd0a7128bbc8b1.png\",\"background_color\":\"#FFF1C5\",\"background_icon\":\"\",\"background_price_color\":\"#ECCF75\",\"background_bottom_color\":\"#E2B52B\",\"ts\":1576073940,\"token\":\"82F6C8CF\",\"medal_info\":{\"icon_id\":0,\"target_id\":744713,\"special\":\"\",\"anchor_uname\":\"\\u96e8\\u5bab\\u6c34\\u5e0cChannel\",\"anchor_roomid\":4767523,\"medal_level\":6,\"medal_name\":\"\\u65e0\\u53e3\\u5e0c\",\"medal_color\":\"#5896de\"},\"user_info\":{\"uname\":\"\\u4e09\\u6c34\\u5316\\u6e05\\u6c81\\u4e09\\u5fc3\",\"face\":\"http:\\/\\/i2.hdslb.com\\/bfs\\/face\\/ad7c756e299839180626aafa1a40dbe43c9bb3de.jpg\",\"face_frame\":\"http:\\/\\/i0.hdslb.com\\/bfs\\/live\\/78e8a800e97403f1137c0c1b5029648c390be390.png\",\"guard_level\":3,\"user_level\":44,\"level_color\":\"#ff86b2\",\"is_vip\":1,\"is_svip\":1,\"is_main_vip\":1,\"title\":\"title-174-1\",\"manager\":0},\"time\":299,\"start_time\":1576073939,\"end_time\":1576074239,\"gift\":{\"num\":1,\"gift_id\":12000,\"gift_name\":\"\\u9192\\u76ee\\u7559\\u8a00\"}}}"
            println(JSON.toJSONString(fromJSON(s)))
        }
    }
}