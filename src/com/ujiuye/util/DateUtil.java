package com.ujiuye.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
* 将字符串类型日期转日期对象
* */
public class DateUtil {
    public static Date strTranferDate(String date,String pattern){
        SimpleDateFormat sdf=new SimpleDateFormat(pattern);
        Date dat = null;
        try {
            dat = sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException();
        }
        return dat;

    }
}
