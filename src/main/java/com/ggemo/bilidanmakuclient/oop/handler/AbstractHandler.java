package com.ggemo.bilidanmakuclient.oop.handler;


import com.ggemo.bilidanmakuclient.oop.CmdData;

interface AbstractHandler<T extends CmdData> {
    public void handle(T data);
}
