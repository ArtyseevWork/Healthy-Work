package com.mordansoft.healthywork;

import static androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class ItsTimeNotification {

    private static final String ACTION_SNOOZE = "com.mordansoft.healthywork" ;
    int NOTIFY_ID = 102;
    String CHANNEL_ID = "HealthWork channel";
    Context context;
    NotificationManagerCompat notificationManager;

    public ItsTimeNotification(Context context) {
        this.context = context;
        notificationManager = NotificationManagerCompat.from(context);
    }


    public void createNotification(){
        MordanSoftLogger.addLog("ItsTimeNotification createNotification START");

        Intent intent = new Intent(context, ToDoActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent,  0);

        Intent plusIntent = new Intent(context, NotificationReceiver.class);
        plusIntent.setAction(ACTION_SNOOZE);
        plusIntent.removeExtra(EXTRA_NOTIFICATION_ID);
        plusIntent.removeExtra(CurrentStatus.exerciseDeltaKey);
        plusIntent.putExtra(EXTRA_NOTIFICATION_ID, NOTIFY_ID);
        plusIntent.putExtra(CurrentStatus.exerciseDeltaKey, +100);

        PendingIntent plusPendingIntent =
                PendingIntent.getBroadcast(context, 1, plusIntent, 0);

        MordanSoftLogger.addLog("ItsTimeNotification Sent id: "+ NOTIFY_ID, context);

        Intent minusIntent = new Intent(context, NotificationReceiver.class);
        minusIntent.setAction(ACTION_SNOOZE);
        minusIntent.removeExtra(EXTRA_NOTIFICATION_ID);
        minusIntent.removeExtra(CurrentStatus.exerciseDeltaKey);
        minusIntent.putExtra(EXTRA_NOTIFICATION_ID, NOTIFY_ID);
        minusIntent.putExtra(CurrentStatus.exerciseDeltaKey, -100);
        PendingIntent minusPendingIntent =
                PendingIntent.getBroadcast(context, 2, minusIntent,  0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_alarm_add_24)
                        .setContentTitle(context.getText(R.string.app_name))
                        .setContentText(context.getText(R.string.notification_title))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setDefaults(android.app.Notification.DEFAULT_ALL)
                        .addAction(R.drawable.ic_baseline_done_24, context.getText(R.string.notification_coming_soon),
                                 plusPendingIntent)
                        .addAction(R.drawable.ic_baseline_not_interested_24, context.getText(R.string.notification_skip_one),
                                 minusPendingIntent);

        // createNotificationChannel();
        notificationManager.notify(NOTIFY_ID, builder.build());

        MordanSoftLogger.addLog("ItsTimeNotification createNotification END");

    }
    public void deleteNotification(){
        MordanSoftLogger.addLog("ItsTimeNotification deleteNotification START");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(NOTIFY_ID);

        MordanSoftLogger.addLog("ItsTimeNotification deleteNotification END");
    }

}
