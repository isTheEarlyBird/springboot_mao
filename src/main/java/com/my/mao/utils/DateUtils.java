package com.my.mao.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换
 */
public class DateUtils {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

    public static Date string2Date(String str) throws ParseException {
        return sdf.parse(str);
    }

    public static String date2String(Date date){
        return sdf.format(date);
    }
}
