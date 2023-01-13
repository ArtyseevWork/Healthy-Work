package com.mordansoft.healthywork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {

    boolean reminder = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (reminder ) {
            MordanSoftLogger.addLog("AlarmReceiver START");
            // Запускаем уведомление
            ItsTimeNotification notification = new ItsTimeNotification(context);
            notification.createNotification();
            MordanSoftLogger.addLog("AlarmReceiver END");
        }
    }
}
