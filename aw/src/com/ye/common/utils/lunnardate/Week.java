package com.ye.common.utils.lunnardate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Week {
	public static String getWeekOfDate() {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日");  
        cal.setTimeZone(TimeZone.getDefault());  
        System.out.println("公历日期:"+sdf.format(cal.getTime()));  
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        System.out.println(weekDays[w]);
        return weekDays[w];
    }
	
	public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return weekDays[w];
    }
	
	public static void main(String[] args) {
		getWeekOfDate();
	}
}
