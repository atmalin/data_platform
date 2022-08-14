package cn.iecas.springboot.framework.util;


import cn.iecas.springboot.framework.common.constant.DatePattern;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ch
 * @date 2021-10-20
 */
public class DateUtil {

    public static String getDateString(Date date){
        if (date == null){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.YYYY_MM_DD);
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

    public static String getDateTimeString(Date date){
        if (date == null){
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DatePattern.YYYY_MM_DD_HH_MM_SS);
        String dateString = simpleDateFormat.format(date);
        return dateString;
    }

}
