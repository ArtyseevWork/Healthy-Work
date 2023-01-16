package com.mordansoft.healthywork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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
        MordanSoftLogger.addLog("AlarmReceiver END");
    }
}
