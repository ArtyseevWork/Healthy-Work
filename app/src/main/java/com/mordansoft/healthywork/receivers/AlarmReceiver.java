package com.mordansoft.healthywork.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.mordansoft.healthywork.data.repository.AppStatusRepositoryImpl;
import com.mordansoft.healthywork.data.repository.ExerciseRepositoryImpl;
import com.mordansoft.healthywork.data.repository.ScheduleRepositoryImpl;
import com.mordansoft.healthywork.data.repository.StatisticRepositoryImpl;
import com.mordansoft.healthywork.data.storage.AppStatusStorageImpShPr;
import com.mordansoft.healthywork.data.storage.ExerciseStorageImplSqlite;
import com.mordansoft.healthywork.data.storage.ScheduleStorageImplShPr;
import com.mordansoft.healthywork.data.storage.StatisticStorageImplShPr;
import com.mordansoft.healthywork.domain.usecase.status.RestartUc;
import com.mordansoft.healthywork.helpers.MordanSoftLogger;
import com.mordansoft.healthywork.worker.AlarmCreatorImpl;
import com.mordansoft.healthywork.worker.NotificationCreatorImpl;

public class AlarmReceiver extends BroadcastReceiver {
    RestartUc restartUc;

    @Override
    public void onReceive(Context context, Intent intent) {

        MordanSoftLogger.addLog("AlarmReceiver START");
        restartUc = new RestartUc(new AppStatusRepositoryImpl(new AppStatusStorageImpShPr(context)),
                              new StatisticRepositoryImpl(new StatisticStorageImplShPr(context)) ,
                              new ExerciseRepositoryImpl(new ExerciseStorageImplSqlite(context)),
                              new ScheduleRepositoryImpl(new ScheduleStorageImplShPr(context)),
                              new AlarmCreatorImpl(context),
                              new NotificationCreatorImpl(context));
         if (restartUc.isEnable()) {
             restartUc.execute();
             //светомузыка
             final Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
             int vibrateLong = 1500;

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
         }
        MordanSoftLogger.addLog("AlarmReceiver END");
    }
}
