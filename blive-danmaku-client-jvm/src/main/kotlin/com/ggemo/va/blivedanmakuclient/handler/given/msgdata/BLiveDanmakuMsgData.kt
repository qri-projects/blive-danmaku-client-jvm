package com.ggemo.va.blivedanmakuclient.handler.given.msgdata

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.annotation.JSONField
import com.ggemo.va.blivedanmakuclient.inner.util.stringUtils

open class BLiveDanmakuMsgData {
    open class Cmd(val rawValue: String)

    object Cmds {
        val DANMAKU = Cmd("DANMU_MSG")
        val SEND_GIFT = Cmd("SEND_GIFT")
        val GUARD_BUY = Cmd("GUARD_BUY")
        val SUPER_CHAT = Cmd("SUPER_CHAT_MESSAGE")
    }

    open class Msg {
        var roomId: Long = 0
    }

    open class Danmaku(
        /**
         * 弹幕相关info
         */
        val info: Info?,
        /**
         * 弹幕内容
         */
        val content: String?,
        /**
         * 用户信息
         */
        val userInfo: UserInfo?,
        /**
         * 狗牌信息
         */
        val userMedal: UserMedal?,
        /**
         * 用户等级信息
         */
        val userLevel: UserLevel?,
        /**
         * title
         */
        val title: Title?,
        var param0: Int?,
        /**
         * 舰队类型,0为非舰队, 1总督, 2提督, 3舰长
         */
        var privilegeType: Int?,
        var param1: Any?,
        var param2: Any?,
        var param3: Any?,
        var param4: Any?
    ) :
        Msg() {
        open class Info(
            var param0: Int?,
            var mode: Int?,
            var fontSize: Int?,
            var color: String?,
            var time: Long?,
            var random: Long?,
            var param1: Int?,
            var uidCrc: String?,
            var param2: Int?,
            var messageType: Int?,
            var bubble: Int?
        ) {
            constructor(o: JSONArray) : this(
                o.getInteger(0),
                o.getInteger(1),
                o.getInteger(2),
                parseColor(o.getLong(3)),
                o.getLong(4),
                o.getLong(5),
                o.getInteger(6),
                o.getString(7),
                o.getInteger(8),
                o.getInteger(9),
                o.getInteger(10)
            )
        }

        open class UserInfo(
            val userId: Long?,
            val userName: String?,
            val adminOrnot: Boolean?,
            val vipOrnot: Boolean?,
            val svipOrnot: Boolean?,
            val uRank: Int?,
            val mobileVerifiedOrnot: Boolean?,
            val userNameColor: String?
        ) {
            constructor(o: JSONArray) : this(
                o.getLong(0),
                o.getString(1),
                o.getBoolean(2),
                o.getBoolean(3),
                o.getBoolean(4),
                o.getInteger(5),
                o.getBoolean(6),
                o.getString(7)
            )
        }

        open class UserMedal(
            val medalLevel: Int?,
            val medalName: String?,
            val userName: String?,
            val roomId: Long?,
            val medalColor: String?,
            val specialMedalName: String?,
            val specialMedalOrnot: Boolean?
        ) {
            companion object {
                fun fromJSON(o: JSONArray): UserMedal? {
                    if (o.size < 7) {
                        return null
                    }
                    return UserMedal(
                        o.getInteger(0),
                        o.getString(1),
                        o.getString(2),
                        o.getLong(3),
                        parseColor(o.getLong(4)),
                        o.getString(5),
                        o.getBoolean(6)
                    )
                }
            }
        }

        open class UserLevel(
            val userLevel: Int,
            val param0: Int,
            val userLevelColor: String?,
            val userLevelRank: String?
        ) {
            constructor(o: JSONArray) : this(
                o.getInteger(0),
                o.getInteger(1),
                parseColor(o.getLong(2)),
                o.getString(3)
            )
        }

        open class Title(
            val oldTitle: String?,
            val newTitle: String?
        ) {
            constructor(o: JSONArray) : this(o.getString(0), o.getString(1))
        }

        constructor(o: JSONArray) : this(
            Info(o.getJSONArray(0)),
            o.getString(1),
            UserInfo(o.getJSONArray(2)),
            UserMedal.fromJSON(o.getJSONArray(3)),
            UserLevel(o.getJSONArray(4)),
            Title(o.getJSONArray(5)),
            o.getInteger(6),
            o.getInteger(7),
            o.getObject<Any>(8, Any::class.java),
            o.getObject<Any>(9, Any::class.java),
            o.getObject<Any>(10, Any::class.java),
            o.getObject<Any>(11, Any::class.java)
        )

        companion object {
            private const val CMD = "DANMU_MSG"
            private const val serialVersionUID = -198478175324866238L
            fun fromJSON(o: JSONArray): Danmaku {
                return Danmaku(o)
            }

            fun parseColor(i: Long): String {
                var s = java.lang.Long.toHexString(i)
                val len = s.length
                if (len < 6) {
                    s = "0".repeat(6 - len) + s
                }
                s = "#$s"
                return s
            }
        }
    }

    open class GuardBuy : Msg() {
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
            fun fromJSON(json: String?): GuardBuy {
                return fromJSON(JSON.parseObject(json).getJSONObject("data"))
            }

            fun fromJSON(json: JSONObject): GuardBuy {
                return json.toJavaObject(GuardBuy::class.java)
            }
        }
    }

    open class SendGift : Msg() {
        open class SendMaster {
            @JSONField(name = "uid")
            var userId: Long = 0

            @JSONField(name = "uname")
            var userName: String? = null
            var roomId: Long? = null
        }

        open class ComboSend {
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

        open class BatchComboSend {
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
            fun fromJSON(json: String?): SendGift {
                return fromJSON(JSON.parseObject(json).getJSONObject("data"))
            }

            fun fromJSON(jsonObject: JSONObject): SendGift {
                return jsonObject.toJavaObject(SendGift::class.java)
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

    open class SuperChat : Msg() {
        open class MedalInfo {
            var iconId: Long = 0
            var targetId: Long = 0
            var special: String? = null
            var anchorUserName: String? = null
            var anchorRoomId: String? = null
            var medalLevel = 0
            var medalName: String? = null
            var medalColor: String? = null
        }

        open class UserInfo {
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

        open class Gift {
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
            fun fromJSON(json: String?): SuperChat {
                return fromJSON(JSON.parseObject(json).getJSONObject("data"))
            }

            fun fromJSON(jsonObject: JSONObject): SuperChat {
                val superCharData = jsonObject.toJavaObject(SuperChat::class.java)
                superCharData.backgroundImageUrl =
                    superCharData.backgroundImageUrl?.let { stringUtils.removeTransSlash(it) }
                val userInfo = superCharData.userInfo
                userInfo?.userFaceImgUrl = userInfo?.userFaceImgUrl?.let { stringUtils.removeTransSlash(it) }
                userInfo?.userFaceFrameImgUrl = userInfo?.userFaceFrameImgUrl?.let { stringUtils.removeTransSlash(it) }
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
}