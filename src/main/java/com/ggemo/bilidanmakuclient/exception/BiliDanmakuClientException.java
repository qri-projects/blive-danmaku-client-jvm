package com.ggemo.bilidanmakuclient.exception;

public class BiliDanmakuClientException extends Exception  {
    public BiliDanmakuClientException() {
    }

    public BiliDanmakuClientException(String message) {
        super(message);
    }

    public BiliDanmakuClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public BiliDanmakuClientException(Throwable cause) {
        super(cause);
    }

    public BiliDanmakuClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
