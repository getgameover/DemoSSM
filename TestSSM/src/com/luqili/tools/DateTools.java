package com.luqili.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/*********************************************************************************
//* Copyright (C) 2015 bsteel. All Rights Reserved.
//*
//* Filename:      DateTools.java
//* Revision:      1.0
//* Author:        陆彦炯
//* Created On:    2015-1-20
//* Modified by:   
//* Modified On:   
//*
//* Description:   日期工具类
/********************************************************************************/
public class DateTools {

    public static final String DF_SECOND = "df_second";
    public static final String DF_MINUTE = "df_minute";
    public static final String DF_HOUR   = "df_hour";
    public static final String DF_DATE   = "df_date";
    public static final String DF_MONTH  = "df_month";
    public static final String DF_SEASON = "df_season";
    public static final String DF_YEAR   = "df_year";

    /**
     * 字符串 转化为日期
     * @param dateStr
     * @param formatStr 转换格式
     * @return
     */
    public static Date getDateByStr(String dateStr, String formatStr) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        try {
            date = sdf.parse(dateStr);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期转为字符串
     * @param date
     * @param formatStr 转换格式
     * @return
     */
    public static String getStrByDate(Date date, String formatStr) {
        String str = null;
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        str = sdf.format(date);
        return str;
    }

    /**
     * 日期计算
     * @param date 原始日期
     * @param timeType 计算维度
     * @param timeLag 计算值
     * @return
     */
    public static Date calculateDateByTimeLag(Date date, String timeType, int timeLag) {
        Date result = null;
        if (date != null && timeType != null) {
            try {
                Calendar c = new java.util.GregorianCalendar();
                c.setTime(date);
                if (timeType.trim().toLowerCase().equals(DateTools.DF_SECOND)) {
                    c.add(Calendar.SECOND, timeLag);
                }
                else if (timeType.trim().toLowerCase().equals(DateTools.DF_MINUTE)) {
                    c.add(Calendar.MINUTE, timeLag);
                }
                else if (timeType.trim().toLowerCase().equals(DateTools.DF_HOUR)) {
                    c.add(Calendar.HOUR, timeLag);
                }
                else if (timeType.trim().toLowerCase().equals(DateTools.DF_DATE)) {
                    c.add(Calendar.DATE, timeLag);
                }
                else if (timeType.trim().toLowerCase().equals(DateTools.DF_MONTH)) {
                    c.add(Calendar.MONTH, timeLag);
                }
                else if (timeType.trim().toLowerCase().equals(DateTools.DF_SEASON)) {
                    c.add(Calendar.MONTH, timeLag * 3);
                }
                else if (timeType.trim().toLowerCase().equals(DateTools.DF_YEAR)) {
                    c.add(Calendar.YEAR, timeLag);
                }
                result = c.getTime();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 比较两个日期的时间差
     * @param date1
     * @param date2
     * @param timeType 比较维度 （年 月 日 时 分 秒）
     * @return
     */
    public static int calculateTimeLagByDate(Date date1, Date date2, String timeType) {
        int result = -1;
        if (date1 != null && date2 != null && timeType != null) {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date1);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(date2);
            int timeTypeI = -1;
            if (timeType.trim().toLowerCase().equals(DateTools.DF_SECOND)) {
                timeTypeI = Calendar.SECOND;
            }
            else if (timeType.trim().toLowerCase().equals(DateTools.DF_MINUTE)) {
                timeTypeI = Calendar.MINUTE;
            }
            else if (timeType.trim().toLowerCase().equals(DateTools.DF_HOUR)) {
                timeTypeI = Calendar.HOUR;
            }
            else if (timeType.trim().toLowerCase().equals(DateTools.DF_DATE)) {
                timeTypeI = Calendar.DATE;
            }
            else if (timeType.trim().toLowerCase().equals(DateTools.DF_MONTH)) {
                timeTypeI = Calendar.MONTH;
            }
            else if (timeType.trim().toLowerCase().equals(DateTools.DF_SEASON)) {
                timeTypeI = Calendar.MONTH;
            }
            else if (timeType.trim().toLowerCase().equals(DateTools.DF_YEAR)) {
                timeTypeI = Calendar.YEAR;
            }
            result = c2.get(timeTypeI) - c1.get(timeTypeI);
            if (timeType.trim().toLowerCase().equals(DateTools.DF_SEASON)) {
                result = result / 3;
            }
        }
        return result;
    }

    /**
     * 获取该日期天最晚时刻
     * 例如 2012-12-12 23:59:59
     * @param date
     * @return
     */
    public static Date getEndOfDay(Date date) {
        return new Date(getStartOfDay(date).getTime() + DateUtils.MILLIS_PER_DAY - 1);
    }

    /**
     * 获取该日期天最初时刻
     * 例如 2012-12-12 00:00:00
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }

    public static void main(String[] args) {
        System.out.println(getEndOfDay(new Date()));
    }

}
