package io.github.ryanhoo.firFlight.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by GuDong on 8/29/16 21:58.
 * Contact with gudong.name@gmail.com.
 */
public class DateUtils {
    private static final String KEY_FORMAT_SECOND = "yyyy-MM-dd";
    private static final String KEY_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * <p>Checks if two dates are on the same day ignoring time.</p>
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is <code>null</code>
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    /**
     * <p>Checks if two calendars represent the same day ignoring time.</p>
     *
     * @param cal1 the first calendar, not altered, not null
     * @param cal2 the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is <code>null</code>
     */
    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    /**
     * <p>Checks if a date is today.</p>
     *
     * @param date the date, not altered, not null.
     * @return true if the date is today.
     * @throws IllegalArgumentException if the date is <code>null</code>
     */
    public static boolean isToday(Date date) {
        return isSameDay(date, Calendar.getInstance().getTime());
    }


    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(KEY_FORMAT_SECOND);
        return sdf.format(date);
    }

    public static String formatAppCreateTime(String dateTime) {
        Date date = parseStringToDate(dateTime);
        return formatDate(date);
    }

    public static String formatTimePersonality(String strDate){
        Date date = parseStringToDate(strDate);
        return convertDateToShowStr(date);
    }

    public static Date parseStringToDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat(KEY_FORMAT);
        try {
            return format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }



    private static String convertDateToShowStr(Date date) {
        String showStr = "";
        long yearSeconds = 31536000L;//365 * 24 * 60 * 60;
        long monthSeconds = 2592000L;//30 * 24 * 60 * 60;
        long daySeconds = 86400L;//24 * 60 * 60;
        long hourSeconds = 3600L;//60 * 60;
        long minuteSeconds = 60L;


        long time = (new Date().getTime() - date.getTime()) / 1000;
        if (time <= 0) {
            showStr = "刚刚";
            return showStr;
        }
        if (time / yearSeconds > 0) {
            int year = (int) (time / yearSeconds);
            if (year > 10)
                showStr = "很久前";
            else {
                showStr = year + "年前";
            }
        } else if (time / monthSeconds > 0) {
            showStr = time / monthSeconds + "个月前";
        } else if (time / daySeconds > 0) {
            long count = time / daySeconds;
            if(count>3){
                showStr = formatDate(date);
            }else{
                showStr = time / daySeconds + "天前";
            }
        } else if (time / hourSeconds > 0) {
            showStr = time / hourSeconds + "小时前";
        } else if (time / minuteSeconds > 0) {
            showStr = time / minuteSeconds + "分钟前";
        } else if (time > 0) {
            showStr = time + "秒前";
        }
        return showStr;
    }
}
