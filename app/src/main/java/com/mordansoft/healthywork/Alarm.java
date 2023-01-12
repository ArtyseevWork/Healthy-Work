package com.mordansoft.healthywork;

import android.content.Context;

import java.time.LocalTime;
import java.util.Calendar;

public class Alarm {

    public static Calendar getNextAlarmTime(Context context){

        Preferences preferences = Preferences.getPreferencesFromFile(context);
        //Schedule schedule = Schedule.getScheduleFromFile(context);

        int minutesAlarm;
        int hoursAlarm;
        int hoursNow;
        int minutesNow;
        int countdown = preferences.getCountdown();
        int period = preferences.getPeriod();

        Calendar calendarAlarm = Calendar.getInstance();
        hoursNow = calendarAlarm.get(Calendar.HOUR_OF_DAY);
        minutesNow = calendarAlarm.get(Calendar.MINUTE);
        minutesAlarm = countdown;
        hoursAlarm = hoursNow;

        if (period == 30){
            if (minutesNow > (countdown+period)){
                hoursAlarm= hoursNow +1;
            }
            else if (minutesNow > (countdown)){
                minutesAlarm = countdown+period;
            }
        } else if (period == 60){
            if (minutesNow > countdown){
                hoursAlarm = hoursNow + 1;
            }
        }

        calendarAlarm.set(Calendar.HOUR_OF_DAY, hoursAlarm);
        calendarAlarm.set(Calendar.MINUTE, minutesAlarm);
        calendarAlarm.set(Calendar.SECOND, 0);

        calendarAlarm.setTimeInMillis(Calendar.getInstance().getTimeInMillis()+(30*1000));  //for debugging
        return calendarAlarm;
    }
}
