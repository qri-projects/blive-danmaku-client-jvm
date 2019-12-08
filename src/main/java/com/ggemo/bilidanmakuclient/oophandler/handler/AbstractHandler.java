package com.ggemo.bilidanmakuclient.oophandler.handler;


import com.ggemo.bilidanmakuclient.oophandler.CmdData;

interface AbstractHandler<T extends CmdData> {
    public void handle(T data);
}
