package com.mordansoft.healthywork.worker;

import static android.content.Context.ALARM_SERVICE;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.mordansoft.healthywork.domain.AlarmCreator;
import com.mordansoft.healthywork.helpers.MordanSoftLogger;
import com.mordansoft.healthywork.receivers.AlarmReceiver;


public class AlarmCreatorImpl implements AlarmCreator {
    private final Context context;

    public AlarmCreatorImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean createAlarm(long time) {
        PendingIntent pendingIntent = getPendingIntent();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        } else {
            //alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(nextAlarmTime.getTimeInMillis(),pendingIntent),pendingIntent);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,time,pendingIntent);
            /*alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, nextAlarmTime.getTimeInMillis(),
                    intervalMs,
                    pendingIntent);*/
        }
        return true;
    }

    @Override
    public boolean stopAlarm(){
        MordanSoftLogger.addLog("Schedule.stop START " );
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(getPendingIntent());
        MordanSoftLogger.addLog("Schedule.stop END " );
        return true;
    }

    private PendingIntent getPendingIntent(){
        int flags;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags = PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA | PendingIntent.FLAG_IMMUTABLE;
        } else {
            flags = PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA;
        }
        Intent intent = new Intent(context, AlarmReceiver.class);
        return PendingIntent.getBroadcast(context,
                0,
                intent, flags);
    }

}
