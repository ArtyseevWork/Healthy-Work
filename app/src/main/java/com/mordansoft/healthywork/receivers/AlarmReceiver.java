package com.mordansoft.healthywork.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.mordansoft.healthywork.helpers.MordanSoftLogger;
import com.mordansoft.healthywork.models.CurrentStatus;
import com.mordansoft.healthywork.models.Notification;

public class AlarmReceiver extends BroadcastReceiver {
    CurrentStatus currentStatus;

    @Override
    public void onReceive(Context context, Intent intent) {
        MordanSoftLogger.addLog("AlarmReceiver START");
        currentStatus = CurrentStatus.getCurrentStatusFromFile(context);
        if (   currentStatus.getApplicationStatus() == CurrentStatus.applicationStatusActive
            || currentStatus.getApplicationStatus() == CurrentStatus.applicationStatusPending) {
            currentStatus.setCurrentExerciseId(context, currentStatus.getNextExerciseId());
            Notification notification = new Notification(context);
            notification.createNotification();

            final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            int vibrateLong = 1000;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                final VibrationEffect vibrationEffect;
                vibrationEffect = VibrationEffect.createOneShot(vibrateLong,
                        VibrationEffect.DEFAULT_AMPLITUDE);
                vibrator.vibrate(vibrationEffect);
            } else {
                vibrator.vibrate(vibrateLong);
            }

            Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
            ringtone.play();

            currentStatus.setCurrentExerciseId(context, currentStatus.getNextExerciseId());
            currentStatus.run(context);
            currentStatus.setApplicationStatus(context, CurrentStatus.applicationStatusPending);
        }

        MordanSoftLogger.addLog("AlarmReceiver END");
    }
}
