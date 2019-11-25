package com.ggemo.bilidynamicclient.response;

import com.ggemo.bilidynamicclient.exception.BiliClientException;
import lombok.*;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractResponse<T extends ResponseData> implements Response {
    int code;
    String message;
    T data;

    public static Response parse(String json) throws BiliClientException {
        throw new UnsupportedOperationException();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() throws BiliClientException {
        if (this.code != 0) {
            throw new BiliClientException();
        }
        return data;
    }

    @Override
    public String toString() {
        if (data != null) {
            return "AbstractResponse{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    ", data=" + data +
                    '}';
        }else{
            return "AbstractResponse{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    ", data=null" +
                    '}';
        }
    }
}
