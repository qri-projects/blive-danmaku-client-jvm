package com.ggemo.va.bilidanmakuclient.structs;

import com.ggemo.va.bilidanmakuclient.utill.EndianBitConverter;
import lombok.*;

/**
 * Author: 清纯的小黄瓜
 * Date: 2020/4/10 22:17
 * Email: 2894700792@qq.com
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class DanmakuProtocol {
    /// <summary>
    /// 消息总长度 (协议头 + 数据长度)
    /// </summary>
    public int packetLength;
    /// <summary>
    /// 消息头长度 (固定为16[sizeof(DanmakuProtocol)])
    /// </summary>
    public int headerLength;
    /// <summary>
    /// 消息版本号
    /// </summary>
    public int version;
    /// <summary>
    /// 消息类型
    /// </summary>
    public int action;
    /// <summary>
    /// 参数, 固定为1
    /// </summary>
    public int parameter;

    public DanmakuProtocol(byte[] buffer) {
        this.packetLength = EndianBitConverter.bigEndianBytesToInt32(buffer, 0);
        this.headerLength = EndianBitConverter.bigEndianBytesToInt16(buffer, 4);
        this.version = EndianBitConverter.bigEndianBytesToInt16(buffer, 6);
        this.action = EndianBitConverter.bigEndianBytesToInt32(buffer, 8);
        this.parameter = EndianBitConverter.bigEndianBytesToInt32(buffer, 12);
    }

    public static void main(String[] args) {
//        byte[] bytes = new byte[]{0, 0, 1, 24, 0, 16, 0, 2, 0, 0, 0, 5, 0, 0, 0, 0, 120, -38, 68, -113, -79, 74, 59, 65, 16, -58, -17, 95, -4, 123, -97, -31, -85, -89, -104, -35, -67, -39, -67, -69, 46, 16, 99, 21, 27, -79, 90, 15, 49, 49, -126, 16, 99, 97, 82, -123, -128, -58, -128, 22, -94, 104, 42, 37, -115, 47, 32, 34, 118, -94, 47, 115, -36, 29, -66, -123, 108, -48, 100, 6, 62, 102, -32, -101, -17, -57, 68, -47, -65, 86, -76, 17, -123, -6, 31, 100, -116, -18, -55, 33, 50, 52, 27, -37, -19, -35, -3, -10, -50, 22, 8, -57, -125, -93, 83, 100, -34, 51, 41, -46, 66, -54, 58, -25, -76, 18, 82, -110, 88, 49, 28, 27, 23, 91, -75, -38, 116, 66, 76, -80, -90, -105, -70, -125, 78, 7, -60, -95, 115, 66, -7, 122, -9, -3, 49, -85, -25, -117, -14, -2, 28, -28, -107, 17, -105, 88, 97, 66, -7, 114, 83, 127, -35, 86, -97, 87, -43, -5, 67, -3, 52, 91, -7, 126, 79, 73, 49, 115, 64, 3, 57, 121, -91, 9, -43, -37, 99, -75, -72, -58, -33, 80, 92, 60, 23, -45, -53, 98, 58, 7, -59, -50, 58, -47, -122, 20, -117, -46, 86, 11, 1, -127, -19, -75, 16, -109, 36, 44, 46, 101, -62, -34, -120, -39, -12, 36, -60, -122, 72, 96, 25, 29, 80, -125, 81, -65, 79, 99, 12, -49, -112, -83, -97, 35, 116, -121, -56, -48, 74, 26, -90, -71, -103, 58, 76, -42, -42, -91, 112, 62, -7, 9, 0, 0, -1, -1, 8, 112, 98, 79};
//        var danmakuProtocol = new DanmakuProtocol(bytes);
//        System.out.println(danmakuProtocol);
    }
}
