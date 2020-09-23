package com.sfb.baselib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期时间转换工具类<br/>
 * 提供如下常用转换模版 <br/>
 * {@link #PATTERN_DATETIME} 年-月-日 时:分:秒 <br/>
 * {@link #PATTERN_DATETIME_NUM} 年月日时分秒<br/>
 * {@link #PATTERN_DATE} 年-月-日<br/>
 * {@link #PATTERN_TIME} 时:分:秒<br/>
 */
public class DateUtils {

    private static final Locale DEFAULT_LOCALE = Locale.CHINESE;
    private static final String DEFAULT_TIME_ZONE = "Asia/Shanghai";

    /**
     * 常用模版——年-月-日 时:分:秒
     */
    public static final String PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 常用模版——年月日时分秒
     */
    public static final String PATTERN_DATETIME_NUM = "yyyyMMddHHmmssSSS";
    /**
     * 常用模版——年-月-日
     */
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    /**
     * 常用模版——时:分:秒
     */
    public static final String PATTERN_TIME = "HH:mm:ss";

    /**
     * 格式化时间戳，转换为{@link #PATTERN_DATETIME}(年-月-日 时:分:秒)输出
     *
     * @param time 需要转换的时间戳
     */
    public static String formatTime(long time) {
        return formatTime(time, PATTERN_DATETIME);
    }

    /**
     * 格式化时间戳，转换为需要的格式输出
     *
     * @param time    需要转换的时间戳
     * @param pattern 格式化的模版（此工具类中亦提供常用模版）
     */
    public static String formatTime(long time, String pattern) {
        //补足13位时间戳
        if (String.valueOf(time).length() == 10) {
            time = time * 1000;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, DEFAULT_LOCALE);
        sdf.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
        Date date = new Date(time);
        return sdf.format(date);
    }

    /**
     * 格式化Date对象，转换为{@link #PATTERN_DATETIME}(年-月-日 时:分:秒)输出
     *
     * @param date 需要转换的Date对象
     */
    public static String formatTime(Date date) {
        return formatTime(date, PATTERN_DATETIME);
    }

    /**
     * 格式化Date对象，转换为需要的格式输出
     *
     * @param date    需要转换的Date对象
     * @param pattern 格式化的模版（此工具类中亦提供常用模版）
     */
    public static String formatTime(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, DEFAULT_LOCALE);
        sdf.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
        return sdf.format(date);
    }

    /**
     * 格式化时间，转换为时间戳，使用{@link #PATTERN_DATETIME}(年-月-日 时:分:秒)模版
     *
     * @param dateString 格式化的时间字符串
     */
    public static long parseTime(String dateString) {
        return parseTime(dateString, PATTERN_DATETIME);
    }

    /**
     * 格式化时间，转换为时间戳
     *
     * @param dateString 格式化的时间字符串
     * @param pattern    格式化模版（此工具类中亦提供常用模版）
     */
    public static long parseTime(String dateString, String pattern) {
        Date date = parseTimeAsDate(dateString, pattern);
        if (date != null) {
            return date.getTime();
        }
        return 0;
    }

    /**
     * 格式化时间，转换为Date对象
     *
     * @param dateString 格式化的时间字符串
     * @param pattern    格式化模版（此工具类中亦提供常用模版）
     */
    public static Date parseTimeAsDate(String dateString, String pattern) {
        Date timeDate = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.CHINESE);
            sdf.setTimeZone(TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
            timeDate = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeDate;
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param time1 long
     * @param time1 long
     * @return boolean
     */
    public static boolean isSameDate(long time1, long time2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(time1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(time2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param date1 Date
     * @param date2 Date
     * @return boolean
     */
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
        boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }
}
