package com.ggemo.va.bililivedanmakuoop.msgdata

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import org.apache.commons.lang.StringUtils

class DanmakuData(
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
) : MsgData() {
    class Info(
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

    class UserInfo(
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

    class UserMedal(
        val medalLevel: Int?,
        val medalName: String?,
        val userName: String?,
        val roomId: Long?,
        val medalColor: String?,
        val specialMedalName: String?,
        val specialMedalOrnot: Boolean?
    ) {
        constructor(o: JSONArray) : this(
            o.getInteger(0),
            o.getString(1),
            o.getString(2),
            o.getLong(3),
            parseColor(o.getLong(4)),
            o.getString(5),
            o.getBoolean(6)
        )
    }

    class UserLevel(
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

    class Title(
        val oldTitle: String?,
        val newTitle: String?
    ) {
        constructor(o: JSONArray) : this(o.getString(0), o.getString(1))
    }

    constructor(o: JSONArray) : this(
        Info(o.getJSONArray(0)),
        o.getString(1),
        UserInfo(o.getJSONArray(2)),
        UserMedal(o.getJSONArray(3)),
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
        fun fromJSON(o: JSONArray): DanmakuData {
            return DanmakuData(o)
        }

        fun parseColor(i: Long): String {
            var s = java.lang.Long.toHexString(i)
            val len = s.length
            if (len < 6) {
                s = StringUtils.repeat("0", 6 - len) + s
            }
            s = "#$s"
            return s
        }

        @JvmStatic
        fun main(args: Array<String>) {
            val json = "{\n" +
                    "  \"cmd\": \"DANMU_MSG\",\n" +
                    "  \"info\": [\n" +
                    "    [\n" +
                    "      0,\n" +
                    "      4,\n" +
                    "      25,\n" +
                    "      14893055,\n" +
                    "      1620873001268,\n" +
                    "      1620872995,\n" +
                    "      0,\n" +
                    "      \"63e97abb\",\n" +
                    "      0,\n" +
                    "      0,\n" +
                    "      5,\n" +
                    "      \"#1453BAFF,#4C2263A2,#3353BAFF\"\n" +
                    "    ],\n" +
                    "    \"啃\",\n" +
                    "    [\n" +
                    "      13578650,\n" +
                    "      \"清楚可怜的小黄瓜\",\n" +
                    "      0,\n" +
                    "      0,\n" +
                    "      0,\n" +
                    "      10000,\n" +
                    "      1,\n" +
                    "      \"#00D1F1\"\n" +
                    "    ],\n" +
                    "    [\n" +
                    "      26,\n" +
                    "      \"沙月\",\n" +
                    "      \"沙月ちゃん\",\n" +
                    "      4767523,\n" +
                    "      398668,\n" +
                    "      \"\",\n" +
                    "      0,\n" +
                    "      6809855,\n" +
                    "      398668,\n" +
                    "      6850801,\n" +
                    "      3,\n" +
                    "      1,\n" +
                    "      128912828\n" +
                    "    ],\n" +
                    "    [\n" +
                    "      26,\n" +
                    "      0,\n" +
                    "      5805790,\n" +
                    "      \"\\u003e50000\",\n" +
                    "      0\n" +
                    "    ],\n" +
                    "    [\n" +
                    "      \"\",\n" +
                    "      \"\"\n" +
                    "    ],\n" +
                    "    0,\n" +
                    "    3,\n" +
                    "    null,\n" +
                    "    {\n" +
                    "      \"ts\": 1620873001,\n" +
                    "      \"ct\": \"E3010B16\"\n" +
                    "    },\n" +
                    "    0,\n" +
                    "    0,\n" +
                    "    null,\n" +
                    "    null,\n" +
                    "    0,\n" +
                    "    105\n" +
                    "  ]\n" +
                    "}"

            val danmakuData = fromJSON(JSON.parseObject(json).getJSONArray("info"))
            println(JSON.toJSONString(danmakuData))
        }
    }
}