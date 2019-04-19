package org.lilacseeking.video.videoapp.Utils;

import org.apache.commons.lang.StringUtils;
import org.lilacseeking.video.videoapp.Eumns.ErrorCodeEumn;
import org.lilacseeking.video.videoapp.Exception.BusinessException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @Auther: lilacseeking
 * @Date: 2018/10/18 22:13
 * @Description:
 */
public class StringUtil {
    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(52);// [0,51)
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 由出生日期获取年龄
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static int getAge(Date birthDay) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }

    /**
     * 字符串转日期
     * @param str
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String str, String pattern){
        // 判断pattern是否为空,利用工具类
        if (StringUtils.isBlank(pattern)) {
            // 如果为空，设置默认格式
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat sd = new SimpleDateFormat();
        sd.applyPattern(pattern);
        Date res = null;
        try {
            res = sd.parse(str);
        } catch (ParseException e) {
            throw new BusinessException(ErrorCodeEumn.DATE_TIME_WRONG.getName());
        }
        return res;
    }

}
