package com.ggemo.va.bilidanmakuclient.utill;

public class EndianBitConverter {
    public static int bigEndianBytesToInt32(byte[] value, int startIndex) {
        return (value[startIndex] << 24) | (value[startIndex + 1] << 16) | (value[startIndex + 2] << 8) | (value[startIndex + 3]);
    }

    public static int bigEndianBytesToInt16(byte[] value, int startIndex) {
        return (short) ((value[startIndex] << 8) | (value[startIndex + 1]));
    }
}