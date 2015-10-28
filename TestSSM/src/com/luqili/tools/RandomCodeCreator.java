package com.luqili.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/*********************************************************************************
//* Copyright (C) 2015 bsteel. All Rights Reserved.
//*
//* Filename:      RandomCodeCreator.java
//* Revision:      1.0
//* Author:        陆彦炯
//* Created On:    2015-1-20
//* Modified by:   
//* Modified On:   
//*
//* Description:   随机数生成器
/********************************************************************************/
public class RandomCodeCreator {

    public static final String CODE_STR = "0123456789";
    public static final String UPPER_CHART_STR = "ABCDEEGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CHART_STR = "abcdefghijklmnopqrstuvwxyz";   
    /**
     * 生成随机数 尾数可变
     * 生成格式：150130104822-45653
     * @param num 尾数个数
     * @return
     */
    public static String createRandomCode(int num) {
        String code = null;
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String dateStr = format.format(date);
        dateStr = dateStr.substring(2, dateStr.length());
        Random random = new Random();
        StringBuffer strBuf = new StringBuffer();
        for (int i = 0 ; i < num ; i++) {
            strBuf.append(CODE_STR.charAt(random.nextInt(CODE_STR.length())));
        }
        code = dateStr + "-" + strBuf.toString();
        return code;
    }

    /**
     * 生成UUID
     * @return
     */
    public static String createUUId() {
        String id = null;
        Random r = new Random();
        String rd = String.valueOf(r.nextInt(100000000));
        if (rd.length() < 8) {
            rd = String.valueOf((8 - rd.length()) * 10 + rd);
            rd = rd.substring(1, rd.length());
        }
        id = String.valueOf(System.currentTimeMillis()) + rd;
        return id;
    }

    /**
     * 整形随机数
     * @return
     */
    public static Long createRandomLong() {
        Long l = null;
        UUID uuid = UUID.randomUUID();
        l = Math.abs(uuid.getMostSignificantBits());
        return l;
    }

    public static void main(String[] args) {
        // RandomCodeCreator creator = new RandomCodeCreator();
        System.out.println("long==" + RandomCodeCreator.createRandomLong());
    }

    /**
     * 生成随机数
     * @param num
     * @return
     */
    public static String createRandomNum(int num) {
        StringBuffer strBuf = new StringBuffer();
        Random random = new Random();
        for (int i = 0 ; i < num ; i++) {
            strBuf.append(CODE_STR.charAt(random.nextInt(CODE_STR.length())));
        }
        return strBuf.toString();
    }
    
    /**
     * 密码随机生成
     */
    public static String createRandomPassword(int num){
    	
    	 String allStr = CODE_STR + UPPER_CHART_STR + LOWER_CHART_STR;
    	 StringBuffer strBuf = new StringBuffer();
         Random random = new Random();
         for (int i = 0 ; i < num ; i++) {
             strBuf.append(allStr.charAt(random.nextInt(allStr.length())));
         }
         return strBuf.toString();
    	
    }
}
