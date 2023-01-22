package com.mordansoft.healthywork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

public class AlarmReceiver extends BroadcastReceiver {
    CurrentStatus currentStatus;

    @Override
    public void onReceive(Context context, Intent intent) {
        MordanSoftLogger.addLog("AlarmReceiver START");
        currentStatus = CurrentStatus.getCurrentStatusFromFile(context);
        if (currentStatus.getApplicationStatus() == CurrentStatus.applicationStatusActive) {
            ItsTimeNotification notification = new ItsTimeNotification(context);
            notification.createNotification();


        }

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);

        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        MordanSoftLogger.addLog("AlarmReceiver END");
    }
}
