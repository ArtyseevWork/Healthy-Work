package com.mordansoft.healthywork.worker;

import static androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mordansoft.healthywork.R;
import com.mordansoft.healthywork.domain.NotificationCreator;
import com.mordansoft.healthywork.domain.model.Statistic;
import com.mordansoft.healthywork.helpers.MordanSoftLogger;
import com.mordansoft.healthywork.presentation.ui.main.MainActivity;
import com.mordansoft.healthywork.receivers.NotificationReceiver;

public class NotificationCreatorImpl  implements NotificationCreator {
    Context context;
    NotificationManagerCompat notificationManager;

    public NotificationCreatorImpl(Context context) {
        this.context = context;
        notificationManager = NotificationManagerCompat.from(context);
    }

    @Override
    public boolean createNotification(com.mordansoft.healthywork.domain.model.Exercise exercise) {
        MordanSoftLogger.addLog("ItsTimeNotification createNotification START");

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        Intent plusIntent = new Intent(context, NotificationReceiver.class);
        plusIntent.setAction(ACTION_SNOOZE);
        plusIntent.removeExtra(EXTRA_NOTIFICATION_ID);
        plusIntent.removeExtra(Statistic.exerciseDeltaKey);
        plusIntent.putExtra(EXTRA_NOTIFICATION_ID, NOTIFY_ID);
        plusIntent.putExtra(Statistic.exerciseDeltaKey, +1);

        PendingIntent plusPendingIntent =
                PendingIntent.getBroadcast(context, 1, plusIntent, 0);

        MordanSoftLogger.addLog("ItsTimeNotification Sent id: " + NOTIFY_ID, context);

        Intent minusIntent = new Intent(context, NotificationReceiver.class);
        minusIntent.setAction(ACTION_SNOOZE);
        minusIntent.removeExtra(EXTRA_NOTIFICATION_ID);
        minusIntent.removeExtra(Statistic.exerciseDeltaKey);
        minusIntent.putExtra(EXTRA_NOTIFICATION_ID, NOTIFY_ID);
        minusIntent.putExtra(Statistic.exerciseDeltaKey, -1);
        PendingIntent minusPendingIntent =
                PendingIntent.getBroadcast(context, 2, minusIntent, 0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_baseline_alarm_add_24)
                        .setContentTitle(context.getText(R.string.app_name))
                        .setContentText(generateContentText(exercise))
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .setContentIntent(pendingIntent)
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                        .setDefaults(android.app.Notification.PRIORITY_MAX)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setDefaults(android.app.Notification.DEFAULT_ALL)
                        .addAction(R.drawable.ic_baseline_done_24, context.getText(R.string.notification_coming_soon),
                                plusPendingIntent)
                        .addAction(R.drawable.ic_baseline_not_interested_24, context.getText(R.string.notification_skip_one),
                                minusPendingIntent);

        createNotificationChannel();

        notificationManager.notify(NOTIFY_ID, builder.build());     //todo api33+ add permissions

        MordanSoftLogger.addLog("ItsTimeNotification createNotification END");
        return true;
    }

    @Override
    public boolean deleteNotification() {

        MordanSoftLogger.addLog("ItsTimeNotification deleteNotification START");

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(NOTIFY_ID);

        MordanSoftLogger.addLog("ItsTimeNotification deleteNotification END");
        return false;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            //channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            //channel.setVibrationPattern(new long[] { 100, 200, 150 });
            notificationManager.createNotificationChannel(channel);
        }
    }

    private String generateContentText(com.mordansoft.healthywork.domain.model.Exercise exercise){
        String exerciseName = exercise.getName();
        int exerciseCount = exercise.getCount();
        String exerciseUnit = exercise.getUnit();

        context.getString(R.string.notification_content_text_start);
        return(context.getString(R.string.notification_content_text_start) + exerciseName + ", "
                + exerciseCount + " " + exerciseUnit);
    }


}
