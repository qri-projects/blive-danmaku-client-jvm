package com.ggemo.bililivedanmakuoop;

import com.alibaba.fastjson.TypeReference;
import com.ggemo.bililivedanmakuoop.cmddata.DanmakuData;
import com.ggemo.bililivedanmakuoop.cmddata.GuardBuyData;
import com.ggemo.bililivedanmakuoop.cmddata.SendGiftData;
import com.ggemo.bililivedanmakuoop.cmddata.SuperChatData;
import lombok.Getter;

public enum CmdEnum {
    /**
     * 已知的命令
     */
    DANMU_MSG("DANMU_MSG", DanmakuData.class),
    GUARD_BUY("GUARD_BUY", GuardBuyData.class),
    SEND_GIFT("SEND_GIFT", SendGiftData.class),
    SUPER_CHAT_MESSAGE("SUPER_CHAT_MESSAGE", SuperChatData.class),
    ;

    @Getter
    String strVal;
    Class cmdDataClass;
    TypeReference typeReference;

    CmdEnum(String strVal, Class clazz) {
        this.strVal = strVal;
        this.cmdDataClass = clazz;
    }


    public boolean equalsStr(String cmdStr){
        return getStrVal().equals(cmdStr);
    }
}
