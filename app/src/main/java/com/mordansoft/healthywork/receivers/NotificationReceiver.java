package com.mordansoft.healthywork.receivers;

import static androidx.core.app.NotificationCompat.EXTRA_NOTIFICATION_ID;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mordansoft.healthywork.data.repository.AppStatusRepositoryImpl;
import com.mordansoft.healthywork.data.repository.StatisticRepositoryImpl;
import com.mordansoft.healthywork.data.storage.AppStatusStorageImpShPr;
import com.mordansoft.healthywork.data.storage.StatisticStorageImplShPr;
import com.mordansoft.healthywork.domain.model.AppStatus;
import com.mordansoft.healthywork.domain.model.Statistic;
import com.mordansoft.healthywork.domain.usecase.statistic.AddDoneOneUc;
import com.mordansoft.healthywork.domain.usecase.statistic.AddSkippedOneUc;
import com.mordansoft.healthywork.presentation.ui.main.MainActivity;
import com.mordansoft.healthywork.helpers.MordanSoftLogger;
import com.mordansoft.healthywork.worker.NotificationCreatorImpl;

public class NotificationReceiver extends BroadcastReceiver {
    AppStatus appStatus;
    AddDoneOneUc addDoneOneUc;
    AddSkippedOneUc addSkippedOneUc;

    @Override
    public void onReceive(Context context, Intent intent) {
        MordanSoftLogger.addLog("NotificationReceiver onReceive Start", context);
        addSkippedOneUc = new AddSkippedOneUc(
                new StatisticRepositoryImpl(new StatisticStorageImplShPr(context)),
                new AppStatusRepositoryImpl(new AppStatusStorageImpShPr(context)),
                new NotificationCreatorImpl(context));
        addDoneOneUc = new AddDoneOneUc(
                new StatisticRepositoryImpl(new StatisticStorageImplShPr(context)),
                new AppStatusRepositoryImpl(new AppStatusStorageImpShPr(context)),
                new NotificationCreatorImpl(context));

        int notifyId = 0;
        int exCntDelta = 0;

        if(intent != null){
            if(intent.getExtras() != null){
                notifyId = intent.getExtras().getInt(EXTRA_NOTIFICATION_ID, 0);
                exCntDelta = intent.getExtras().getInt(Statistic.exerciseDeltaKey, 0);
            }
        }

        MordanSoftLogger.addLog("Receive id: " + notifyId);
        MordanSoftLogger.addLog("exCntDelta: " + exCntDelta);


        if (exCntDelta > 0){
            addDoneOneUc.execute();
        } else if (exCntDelta < 0){
            addSkippedOneUc.execute();
        }

        /*Notification itsTimeNotification = new Notification(context);
        itsTimeNotification.deleteNotification();*/

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