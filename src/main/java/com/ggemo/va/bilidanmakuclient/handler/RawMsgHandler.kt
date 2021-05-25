package com.ggemo.va.bilidanmakuclient.handler

interface RawMsgHandler {
    fun handleHeartBeat(userCount: Int)
    fun handleCmdMsg(cmdMsg: String)
}