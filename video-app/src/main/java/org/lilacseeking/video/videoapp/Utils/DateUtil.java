package org.lilacseeking.video.videoapp.Utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @Auther: lilacseeking
 * @Date: 2019/4/16 23:39
 * @Description: 日期工具类
 */
public class DateUtil {

    /**
     * Date转字符串
     * @param date
     * @param template
     * @return
     */
    public static String dateToStr(Date date, String template) {
        SimpleDateFormat sdf = new SimpleDateFormat(template);
        return sdf.format(date);
    }

    /**
     * Date转字符串
     * @param template
     * @return
     */
    public static String dateToStr(String template) {
        SimpleDateFormat sdf = new SimpleDateFormat(template);
        return sdf.format(new Date());
    }

    // 获得某天最大时间 2017-10-15 23:59:59
    public static Date getEndOfDay(Date date) {
        if (null == date){
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获得某天最小时间 2017-10-15 00:00:00
    public static Date getStartOfDay(Date date) {
        if (null == date){
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }
}
