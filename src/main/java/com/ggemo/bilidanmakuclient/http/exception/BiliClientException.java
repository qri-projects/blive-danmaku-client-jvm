package com.ggemo.bilidynamicclient.exception;

public class BiliClientException extends Exception {
    public BiliClientException() {
    }

    public BiliClientException(String message) {
        super(message);
    }

    public BiliClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public BiliClientException(Throwable cause) {
        super(cause);
    }

    public BiliClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
