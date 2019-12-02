package com.ggemo.bilidanmakuclient.improveter.handler;

import java.util.HashSet;
import java.util.Set;

public class HandlerHolder {
    Set<UserCountHandler> userCountHandlerSet;
    Set<CmdHandler> cmdHandlerSet;

    public HandlerHolder() {
        userCountHandlerSet = new HashSet<>();
        cmdHandlerSet = new HashSet<>();
    }
    public boolean addUserCountHandler(UserCountHandler handler){
        return userCountHandlerSet.add(handler);
    }
    public boolean removeUserCountHandler(UserCountHandler handler){
        return userCountHandlerSet.remove(handler);
    }

    public boolean addCmdHandler(CmdHandler handler){
        return cmdHandlerSet.add(handler);
    }

    public boolean removeCmdHandler(CmdHandler handler){
        return cmdHandlerSet.remove(handler);
    }

    public void handleUserCount(int userCount){
        for (UserCountHandler userCountHandler : userCountHandlerSet) {
            userCountHandler.handle(userCount);
        }
    }
    public void handleCmd(String cmdJson){
        for (CmdHandler cmdHandler : cmdHandlerSet) {
            cmdHandler.handle(cmdJson);
        }
    }
}
