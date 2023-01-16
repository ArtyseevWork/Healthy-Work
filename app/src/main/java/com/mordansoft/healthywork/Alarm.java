package com.mordansoft.healthywork;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

    public static Calendar run(Context context){
        MordanSoftLogger.addLog("Alarm run START " );
        Calendar nextAlarmTime = Alarm.getNextAlarmTime(context);
        long intervalMs = (long) Preferences.getPreferencesFromFile(context).getPeriod()*60*1000;
        intervalMs = 60*1000; // for debugging
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, nextAlarmTime.getTimeInMillis(),
                intervalMs,
                pendingIntent);

        //alarmManager.cancel(pendingIntent);
        //Toast.makeText(context, "someText",Toast.LENGTH_LONG).show();

        MordanSoftLogger.addLog("Alarm run END " );
        return nextAlarmTime;
    }

    public static void stop(Context context){
        MordanSoftLogger.addLog("Alarm.stop START " );
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        MordanSoftLogger.addLog("Alarm.stop END " );
    }
}
