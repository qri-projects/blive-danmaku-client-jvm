package com.ggemo.va.bililivedanmakuoop.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    private static final Pattern PATTERN = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

    public static String unicodeToString(String unicodeStr) {
        Matcher matcher = PATTERN.matcher(unicodeStr);
        char ch;
        while (matcher.find()) {
            String group = matcher.group(2);
            ch = (char) Integer.parseInt(group, 16);
            String group1 = matcher.group(1);
            unicodeStr = unicodeStr.replace(group1, ch + "");
        }
        return unicodeStr;
    }

    public static String removeTransSlash(String str){
        return str.replaceAll("\\\\/", "/");
    }
}
