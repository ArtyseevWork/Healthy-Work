package com.mordansoft.healthywork.receivers;

import static androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mordansoft.healthywork.activity.MainActivity;
import com.mordansoft.healthywork.helpers.MordanSoftLogger;
import com.mordansoft.healthywork.models.CurrentStatus;
import com.mordansoft.healthywork.models.Notification;
import com.mordansoft.healthywork.models.TodayStatistics;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        MordanSoftLogger.addLog("NotificationReceiver onReceive Start", context);

        int notifyId = 0;
        int exCntDelta = 0;

        if(intent != null){
            if(intent.getExtras() != null){
                notifyId = intent.getExtras().getInt(EXTRA_NOTIFICATION_ID, 0);
                exCntDelta = intent.getExtras().getInt(TodayStatistics.exerciseDeltaKey, 0);
            }
        }

        MordanSoftLogger.addLog("Receive id: " + notifyId);
        MordanSoftLogger.addLog("exCntDelta: " + exCntDelta);

        TodayStatistics todayStatistics = TodayStatistics.getTodayStatisticsFromFile(context);
        todayStatistics.setCountOfExerciseDelta(context, exCntDelta);
        CurrentStatus currentStatus = CurrentStatus.getCurrentStatusFromFile(context);
        currentStatus.setApplicationStatus(context, CurrentStatus.applicationStatusActive);
        currentStatus.run(context);


        Notification itsTimeNotification = new Notification(context);
        itsTimeNotification.deleteNotification();

        try {
            MainActivity.getInstance().init();
            MainActivity.getInstance().updateUi();
        } catch (Exception e) {
            MordanSoftLogger.addLog("NotificationReceiver updateUI error: " + e, 'e');
        }
        //throw new UnsupportedOperationException("Not yet implemented");
        MordanSoftLogger.addLog("NotificationReceiver onReceive End", context);
    }
}