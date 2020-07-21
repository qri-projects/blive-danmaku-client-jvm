package com.ggemo.va.bililivedanmakuoop.cmddataprocessor;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.ggemo.va.bililivedanmakuoop.CmdEnum;
import com.ggemo.va.bililivedanmakuoop.cmddata.CmdData;
import com.ggemo.va.bililivedanmakuoop.handler.AbstractHandler;

public abstract class CmdDataProcessor<T extends CmdData, S extends AbstractHandler<T>> {
    private final Set<S> handlers;
    protected final long roomId;
    private final CmdEnum cmdEnum;

    public CmdDataProcessor(Set<S> handlers, long roomId, CmdEnum cmdEnum) {
        this.handlers = handlers;
        this.roomId = roomId;
        this.cmdEnum = cmdEnum;
    }

    public CmdEnum getCmdEnum() {
        return this.cmdEnum;
    }

    protected void handle(T data) {
        this.handlers.forEach(handler -> handler.handle(data));
    }

    public abstract void handle(JSONObject jsonObject);
}
