package com.mordansoft.healthywork;

import static androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MordanSoftLogger.addLog("NotificationReceiver onReceive Start", context);

        int notifyId = 0;
        int exCntDelta = 0;

        if(intent != null){
            if(intent.getExtras() != null){
                notifyId = intent.getExtras().getInt(EXTRA_NOTIFICATION_ID, 0);
                exCntDelta = intent.getExtras().getInt(CurrentStatus.exerciseDeltaKey, 0);
            }
        }

        MordanSoftLogger.addLog("Receive id: " + notifyId);
        MordanSoftLogger.addLog("exCntDelta: " + exCntDelta);

        CurrentStatus currentStatus = CurrentStatus.getCurrentStatusFromFile(context);
        currentStatus.setCountOfExerciseDelta(context, exCntDelta);

        ItsTimeNotification itsTimeNotification = new ItsTimeNotification(context);
        itsTimeNotification.deleteNotification();

        try {
            MainActivity.getInstance().updateUi();
        } catch (Exception e) {
            MordanSoftLogger.addLog("NotificationReceiver updateUI error: " + e, 'e');
        }
        //throw new UnsupportedOperationException("Not yet implemented");
        MordanSoftLogger.addLog("NotificationReceiver onReceive End", context);
    }
}