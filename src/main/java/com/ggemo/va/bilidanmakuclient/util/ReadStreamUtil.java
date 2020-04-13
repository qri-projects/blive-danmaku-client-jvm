package com.ggemo.va.bilidanmakuclient.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author: 清纯的小黄瓜
 * Date: 2020/4/13 13:28
 * Email: 2894700792@qq.com
 */
public class ReadStreamUtil {
    public static byte[] readStream(InputStream stream, int bufferLen) throws IOException {
        byte[] out = new byte[bufferLen];
        int readLen = stream.read(out);

        if(readLen < bufferLen){
            byte[] littleOut = new byte[readLen];
            System.arraycopy(out, 0, littleOut, 0, readLen);
            return littleOut;
        }

        int longerBufferLen = 2 * bufferLen;
        byte[] innerOut = readStream(stream, longerBufferLen);
        int alloutLen = out.length + innerOut.length;
        byte[] allout = new byte[alloutLen];
        System.arraycopy(out, 0, allout, 0, bufferLen);
        System.arraycopy(innerOut, 0, allout, bufferLen, innerOut.length);
        return allout;
    }
}
