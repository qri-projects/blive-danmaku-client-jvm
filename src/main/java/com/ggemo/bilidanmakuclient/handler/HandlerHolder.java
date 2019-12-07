package com.ggemo.bilidanmakuclient.handler;

import java.util.HashSet;
import java.util.Set;


public class HandlerHolder {
    private Set<UserCountHandler> userCountHandlerSet;
    private Set<CmdHandler> cmdHandlerSet;

    public HandlerHolder() {
        userCountHandlerSet = new HashSet<>();
        cmdHandlerSet = new HashSet<>();
    }

    public HandlerHolder(CmdHandler cmdHandler){
        this();
        this.addCmdHandler(cmdHandler);
    }

    public HandlerHolder(UserCountHandler userCountHandler, CmdHandler cmdHandler){
        this(cmdHandler);
        this.addUserCountHandler(userCountHandler);
    };

    public void addUserCountHandler(UserCountHandler o) {
        userCountHandlerSet.add(o);
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
