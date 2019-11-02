package csl.individual.community.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author Administrator
 * @Date 2019/11/1 16:44
 */
public class DateUtils {

    /**
     * 获取当前时间-30分钟前的时间
     * @param minute
     * @return
     */
    public static Long getTimeByMinute(Calendar calendar,int minute) {

        calendar.add(Calendar.MINUTE, minute);

        return calendar.getTime().getTime();

    }

    /**
     * 获取当天时间指定时间的时间戳
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Long   initDateByDay(Integer hour,Integer minute,Integer second){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime().getTime();
    }

}
