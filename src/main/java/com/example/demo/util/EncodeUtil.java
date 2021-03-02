package com.example.demo.util;

import java.io.UnsupportedEncodingException;

public class EncodeUtil {
    public static String encoded(String text){
        try {
            return new String(text.getBytes("GB2312"), "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
