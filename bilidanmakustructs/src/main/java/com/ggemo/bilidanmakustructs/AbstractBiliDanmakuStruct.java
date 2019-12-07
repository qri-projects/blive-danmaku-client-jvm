package com.ggemo.bilidanmakustructs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.ggemo.bilidanmakustructs.cmddata.SendGiftData;

import javax.naming.OperationNotSupportedException;

public abstract class AbstractBiliDanmakuStruct<T> {
    CmdEnum cmdStr;
    T data;

    public abstract T getData();
    public abstract CmdEnum getCmd();

//    public AbstractBiliDanmakuStruct(T data) {
//        this.data = data;
//    }

//    public abstract static AbstractBiliDanmakuStruct fromJSON(String json) throws OperationNotSupportedException;
}
