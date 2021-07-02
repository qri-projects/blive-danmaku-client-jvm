package com.ggemo.va.bilidanmakuclient.http.response

import com.alibaba.fastjson.annotation.JSONField
import com.ggemo.va.bilidanmakuclient.http.exception.BiliClientException

open class AbstractResponse<T> : Response {
    var code: Number? = null
    @JSONField(name = "msg")
    var message: String? = null
    var data: T? = null

    @Throws(BiliClientException::class)
    fun getD(): T {
        if (this.code != 0) {
            throw BiliClientException("http get data failed, code = $code, message: $message")
        }
        if (data == null) {
            throw BiliClientException("http get data failed, data is null")
        }
        return data!!
    }

    override fun toString(): String {
        return if (data != null) {
            "AbstractResponse{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    ", data=" + data +
                    '}'
        } else {
            "AbstractResponse{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    ", data=null" +
                    '}'
        }
    }
}