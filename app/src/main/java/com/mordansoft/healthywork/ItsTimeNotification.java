package com.mordansoft.healthywork;

import static androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class ItsTimeNotification {

    private static final String ACTION_SNOOZE = "com.mordansoft.healthyremote" ;
    int NOTIFY_ID = 102;
    String CHANNEL_ID = "Cat channel2";
    Context context;
    NotificationManagerCompat notificationManager;

    public ItsTimeNotification(Context context) {
        this.context = context;
        notificationManager = NotificationManagerCompat.from(context);
    }

    public ItsTimeNotification(Context context, int newId) {
        this.context = context;
        this.NOTIFY_ID = newId;
        notificationManager = NotificationManagerCompat.from(context);
    }

    public void createNotification(){
        MordanSoftLogger.addLog("MyNotification createNotification START");



        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,  0);

        Intent plusIntent = new Intent(context, NotificationReceiver.class);
        plusIntent.setAction(ACTION_SNOOZE);
        plusIntent.putExtra(EXTRA_NOTIFICATION_ID, NOTIFY_ID);
        //plusIntent.putExtra(Exercise.EXTRA_EXERCISE_CNT_DELTA, -1);
        PendingIntent plusPendingIntent =
                PendingIntent.getBroadcast(context, 1, plusIntent, 0);

        MordanSoftLogger.addLog("Sent id: "+ NOTIFY_ID, context);

        Intent minusIntent = new Intent(context, NotificationReceiver.class);
        minusIntent.setAction(ACTION_SNOOZE);
        //minusIntent.removeExtra(Exercise.EXTRA_EXERCISE_CNT_DELTA);
        minusIntent.putExtra(EXTRA_NOTIFICATION_ID, NOTIFY_ID);
        //minusIntent.putExtra(Exercise.EXTRA_EXERCISE_CNT_DELTA, +1);
        PendingIntent minusPendingIntent =
                PendingIntent.getBroadcast(context, 2, minusIntent,  0);


        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_alarm_add_24)
                        .setContentTitle("Напоминание2")
                        .setContentText("Пора покормить кота2")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setDefaults(android.app.Notification.DEFAULT_ALL)
                        .addAction(R.drawable.ic_baseline_alarm_add_24, "button1",
                                 plusPendingIntent)
                        .addAction(R.drawable.ic_baseline_alarm_add_24, "button2",
                                 minusPendingIntent);
         
         

        // createNotificationChannel();
        notificationManager.notify(NOTIFY_ID, builder.build());


        //AlarmManager alarmManager = (AlarmManager) getSystemService(this.ALARM_SERVICE);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, nextAlarmTime.getTimeInMillis(), intervalMs, pendingIntent);

        MordanSoftLogger.addLog("MyNotification createNotification END");

    }
    public void deleteNotification(){
        MordanSoftLogger.addLog("MyNotification deleteNotification START");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(NOTIFY_ID);

        MordanSoftLogger.addLog("MyNotification deleteNotification END");
    }

}
