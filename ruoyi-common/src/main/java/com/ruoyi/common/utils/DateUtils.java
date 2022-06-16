package com.ruoyi.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String HHMM = "HH:mm";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     *
     * @param endDate 待比较时间
     * @param nowDate 现在时间
     * @return long [天,时,分,秒,总秒数]
     */
    public static long[] calcDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = Math.abs(endDate.getTime() - nowDate.getTime());
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒
        long second = diff % nd % nh % nm / 1000;
        //计算总共多少秒
        long allSecond = diff / 1000;
        return new long[]{day, hour, min, second, allSecond};
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long[] datePoor = calcDatePoor(endDate, nowDate);
        return datePoor[0] + "天" + datePoor[1] + "小时" + datePoor[2] + "分钟";
    }

    /**
     * 获得明天开始时间毫秒数
     *
     * @return
     */
    public static long getTomorrowStartTimeMillis() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * 计算离明天开始时间差多少秒
     *
     * @return
     */
    public static long calcTomorrowSecondPoor() {
        return (getTomorrowStartTimeMillis() - System.currentTimeMillis()) / 1000;
    }


    public static LocalTime parseLocalTime(String time) {

        while (time.length() < 5) {
            time = "0" + time;
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(HHMM);

        return LocalTime.parse(time, dateTimeFormatter);
    }


    /*public static void main(String[] args) {
        long[] datePoor = calcDatePoor(new Date(),new Date(System.currentTimeMillis()-70000));
        System.out.println(datePoor[0]);
        System.out.println(datePoor[1]);
        System.out.println(datePoor[2]);
        System.out.println(datePoor[3]);
        System.out.println(datePoor[4]);
        System.out.println(calcTomorrowSecondPoor());
    }*/
}
