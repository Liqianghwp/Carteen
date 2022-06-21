package com.ruoyi.pay.util;

import java.io.UnsupportedEncodingException;

/**
 * @ClassName URLEncoderUtil
 * @Description:
 * @Author: lilong12
 * @Date: 2020/3/20 0020
 **/
public class URLEncoderUtil {


    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


}
