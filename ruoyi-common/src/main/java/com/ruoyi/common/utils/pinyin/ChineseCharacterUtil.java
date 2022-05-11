package com.ruoyi.common.utils.pinyin;

import com.ruoyi.common.utils.StringUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname ChineseCharacterUtil
 * @Description 汉子工具类
 * @Date 2022/5/10 11:09
 * @Created by YuLiu
 */
public class ChineseCharacterUtil {

    /**
     * 汉字拼音全程
     *
     * @param hanZi 汉字
     * @return
     */
    public static String convertHanZi2PinyinFull(String hanZi) {
        return convertHanZi2Pinyin(hanZi, true);
    }

    /**
     * 汉字拼音首字母缩写
     *
     * @param hanZi 汉字
     * @return
     */
    public static String convertHanZi2PinyinAbbreviation(String hanZi) {
        return convertHanZi2Pinyin(hanZi, true);
    }


    public static String convertHanZi2Pinyin(String hanZi, Boolean full) {
/***
 * ^[\u2E80-\u9FFF]+$ 匹配所有东亚区的语言
 * ^[\u4E00-\u9FFF]+$ 匹配简体和繁体
 * ^[\u4E00-\u9FA5]+$ 匹配简体
 */
        String regExp = "^[\u4E00-\u9FFF]+$";
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isBlank(hanZi)) {
            return "";
        }
        String pinyin = "";
        for (int i = 0; i < hanZi.length(); i++) {
            char unit = hanZi.charAt(i);
            if (match(String.valueOf(unit), regExp))//是汉字，则转拼音
            {
                pinyin = convertSingleHanzi2Pinyin(unit);
                if (full) {
                    sb.append(pinyin);
                } else {
                    sb.append(pinyin.charAt(0));
                }
            } else {
                sb.append(unit);
            }
        }
        return sb.toString();
    }


    /***
     * 将单个汉字转成拼音
     * @param hanzi
     * @return
     */
    private static String convertSingleHanzi2Pinyin(char hanzi) {
        HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
        outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        String[] res;
        StringBuffer sb = new StringBuffer();
        try {
            res = PinyinHelper.toHanyuPinyinStringArray(hanzi, outputFormat);
            //对于多音字，只用第一个拼音
            sb.append(res[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return sb.toString();
    }

    /***
     * @param str 源字符串
     * @param regex 正则表达式
     * @return 是否匹配
     */
    public static boolean match(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }


    public static void main(String[] args) {

        System.out.println(convertHanZi2Pinyin("世界", true));

    }

}
