package cn.edu.tit.community.util;

public class TimeFormat {

    /*时间戳转换成几年前、几天前、几小时前等格式*/
    public static String timeFormat(long timestamp) {
        if (timestamp > 0) {
            String result;
            long minute = 1000 * 60;
            long hour = minute * 60;
            long day = hour * 24;
            long month = day * 30;
            long now = System.currentTimeMillis();
            long diffValue = now - timestamp;
            if (diffValue < 0) {
                return "timestamp error";
            }
            long currMonth = diffValue / month;
            long currWeek = diffValue / (7 * day);
            long currDay = diffValue / day;
            long currHour = diffValue / hour;
            long currMin = diffValue / minute;
            if (currMonth >= 1) {
                if (currMonth <= 12) {
                    result = "" + currMonth + "月前";
                } else {
                    result = "" + currMonth / 12 + "年前";
                }
            } else if (currWeek >= 1) {
                result = "" + currWeek + "周前";
            } else if (currDay >= 1) {
                result = "" + currDay + "天前";
            } else if (currHour >= 1) {
                result = "" + currHour + "小时前";
            } else if (currMin >= 1) {
                result = "" + currMin + "分钟前";
            } else {
                result = "刚刚";
            }
            return result;
        } else {
            return "timestamp error";
        }
    }

}
