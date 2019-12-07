package com.ggemo.bilidanmakustructs.handler;


import com.ggemo.bilidanmakustructs.CmdData;

interface AbstractHandler<T extends CmdData> {
    public void handle(T data);
}
