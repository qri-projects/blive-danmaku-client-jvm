package com.ggemo.bililivedanmakuoop.handler;


import com.ggemo.bililivedanmakuoop.CmdData;

interface AbstractHandler<T extends CmdData> {
    public void handle(T data);
}
