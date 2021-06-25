package com.ggemo.va.bilidanmakuclient.http.response.responsedataa

import com.alibaba.fastjson.annotation.JSONField

class RoomInitData {
    var encrypted = false

    @JSONField(name = "hidden_till")
    var hiddenTill = 0

    @JSONField(name = "is_hidden")
    var isHidden = false

    @JSONField(name = "is_locked")
    var isLocked = false

    @JSONField(name = "is_portrait")
    var isPortrait = false

    @JSONField(name = "is_sp")
    var isSp = 0

    @JSONField(name = "live_status")
    var liveStatus = 0

    @JSONField(name = "live_status")
    var liveTime: Long = 0

    @JSONField(name = "lock_till")
    var lockTill = 0

    @JSONField(name = "need_p2p")
    var needP2p = 0

    @JSONField(name = "pwd_verified")
    var pwdVerified = false

    @JSONField(name = "room_id")
    var roomId: Long = 0

    @JSONField(name = "room_shield")
    var roomShield = 0

    @JSONField(name = "short_id")
    var shortId: Long = 0

    @JSONField(name = "special_type")
    var specialType = 0

    @JSONField(name = "uid")
    var uid: Long = 0
}