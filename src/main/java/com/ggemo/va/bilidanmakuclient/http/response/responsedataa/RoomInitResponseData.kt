package com.ggemo.va.bilidanmakuclient.http.response.responsedataa

import com.ggemo.va.bilidanmakuclient.http.MyHttpClient
import kotlin.Throws
import java.io.IOException
import com.ggemo.va.bilidanmakuclient.http.response.impl.RoomInitResponse
import org.apache.http.client.methods.HttpGet
import com.ggemo.va.bilidanmakuclient.http.request.RoomInitRequest
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.HttpEntity
import org.apache.http.util.EntityUtils
import com.ggemo.va.bilidanmakuclient.http.response.impl.DanmakuServerConfResponse
import com.ggemo.va.bilidanmakuclient.http.request.DanmakuServerConfRequest
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.RoomInitResponseData
import com.ggemo.va.bilidanmakuclient.http.response.AbstractResponse
import com.alibaba.fastjson.JSON
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.DanmakuServerConfResponseData
import com.alibaba.fastjson.annotation.JSONField
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.DanmakuServerConfResponseData.HostServerInfo
import com.ggemo.va.bilidanmakuclient.http.response.responsedataa.DanmakuServerConfResponseData.ServerInfo
import lombok.NoArgsConstructor
import lombok.AllArgsConstructor
import com.ggemo.va.bilidanmakuclient.http.exception.BiliClientException
import java.lang.UnsupportedOperationException
import com.ggemo.va.bilidanmakuclient.http.InitRoom
import com.ggemo.va.bilidanmakuclient.http.response.ResponseData
import lombok.Data
import kotlin.jvm.JvmStatic
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.HttpClients

@Data
class RoomInitResponseData : ResponseData {
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