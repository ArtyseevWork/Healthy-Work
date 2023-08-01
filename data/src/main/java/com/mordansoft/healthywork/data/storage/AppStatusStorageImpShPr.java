package com.mordansoft.healthywork.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.mordansoft.healthywork.data.model.AppStatusD;
import com.mordansoft.healthywork.domain.MordanSoftLogger;

import java.util.Calendar;

public class AppStatusStorageImpShPr  implements  AppStatusStorage{

    public static final int applicationStatusDefault = 0;
    private static final String fileName = "currentStatus";
    private static final String applicationStatusKey = "APPLICATION_STATUS";
    private static final long nextAlarmTimeDefault = 0L;
    private static final String nextAlarmTimeKey="NEXT_ALARM_TIME";
    private static final int nextExerciseIdDefault = 0;
    private static final String nextExerciseIdKey = "NEXT_EXERCISE_ID";
    private static final int currentExerciseIdDefault = 0;
    private static final String currentExerciseIdKey = "CURRENT_EXERCISE_ID";

    Context context;

    public AppStatusStorageImpShPr(Context context) {
        this.context = context;
    }

    @Override
    public boolean updateAppStatus(AppStatusD appStatusD) {
            SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(applicationStatusKey, appStatusD.getApplicationStatus());
            editor.putLong(nextAlarmTimeKey, appStatusD.getNextAlarmTime());
            editor.putInt(currentExerciseIdKey, appStatusD.getCurrentExerciseId());
            editor.putInt(nextExerciseIdKey, appStatusD.getNextExerciseId());
            editor.apply();
        return true;
    }

    @Override
    public AppStatusD getAppStatus() {
        AppStatusD appStatus = null;
        MordanSoftLogger.addLog("getCurrentStatusFromFile START");
        int applicationStatus;
        long nextAlarmTime;
        int nextExerciseId;
        int currentExerciseId;

        try {
            SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            applicationStatus = sharedPref.getInt(applicationStatusKey, applicationStatusDefault);
            nextAlarmTime = sharedPref.getLong(nextAlarmTimeKey, nextAlarmTimeDefault);
            nextExerciseId = sharedPref.getInt(nextExerciseIdKey, nextExerciseIdDefault);
            currentExerciseId = sharedPref.getInt(currentExerciseIdKey, currentExerciseIdDefault);

            appStatus = new AppStatusD(applicationStatus,
                    nextAlarmTime,
                    nextExerciseId,
                    currentExerciseId
            );
        } catch (Exception e){
            MordanSoftLogger.addLog("getCurrentStatusFromFile Error: " + e, 'e');
        }
        MordanSoftLogger.addLog("getCurrentStatusFromFile END");
        return appStatus;

    }
}
