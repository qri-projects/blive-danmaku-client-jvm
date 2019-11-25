package com.ggemo.bilidanmakuclient.http.response.responsedataa;

import com.alibaba.fastjson.annotation.JSONField;
import com.ggemo.bilidanmakuclient.http.response.ResponseData;
import lombok.Data;

@Data
public class RoomInitResponseData implements ResponseData {
    boolean encrypted;

    @JSONField(name="hidden_till")
    int hiddenTill;

    @JSONField(name="is_hidden")
    boolean isHidden;

    @JSONField(name="is_locked")
    boolean isLocked;

    @JSONField(name="is_portrait")
    boolean isPortrait;

    @JSONField(name="is_sp")
    int isSp;

    @JSONField(name="live_status")
    int liveStatus;

    @JSONField(name="live_status")
    long liveTime;

    @JSONField(name="lock_till")
    int lockTill;

    @JSONField(name="need_p2p")
    int needP2p;

    @JSONField(name="pwd_verified")
    boolean pwdVerified;

    @JSONField(name="room_id")
    long roomId;

    @JSONField(name="room_shield")
    int roomShield;

    @JSONField(name="short_id")
    long shortId;

    @JSONField(name="special_type")
    int specialType;

    @JSONField(name="uid")
    long uid;

}
