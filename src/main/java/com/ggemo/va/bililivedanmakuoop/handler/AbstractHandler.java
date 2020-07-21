package com.ggemo.va.bililivedanmakuoop.handler;


import com.ggemo.va.bililivedanmakuoop.cmddata.CmdData;

public interface AbstractHandler<T extends CmdData> {
    public void handle(T data);
}
