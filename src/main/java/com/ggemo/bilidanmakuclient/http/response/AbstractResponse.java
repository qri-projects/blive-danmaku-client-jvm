package com.ggemo.bilidanmakuclient.http.response;

import com.ggemo.bilidanmakuclient.http.exception.BiliClientException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbstractResponse<T extends ResponseData> implements Response {
    int code;
    String message;
    T data;

    public static Response parse(String json) {
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
            throw new BiliClientException("code != 0");
        }
        if (data == null) {
            throw new BiliClientException("data is null");
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
