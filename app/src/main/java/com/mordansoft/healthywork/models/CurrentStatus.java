package com.mordansoft.healthywork.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.mordansoft.healthywork.helpers.MordanSoftLogger;

import java.util.Calendar;

public class CurrentStatus {


    private static final String fileName = "currentStatus";
    private int applicationStatus;
    public static final int applicationStatusDefault = 0;
    public static final int applicationStatusPending = 100;
    public static final int applicationStatusActive = 200;
    private static final String applicationStatusKey = "APPLICATION_STATUS";
    private long nextAlarmTime;
    private static final long nextAlarmTimeDefault = 0L;
    private static final String nextAlarmTimeKey="NEXT_ALARM_TIME";

    private int exerciseId;
    private static final int exerciseIdDefault = 0;
    private static final String exerciseIdKey = "EXERCISE_ID";


    public CurrentStatus(int applicationStatus, long nextAlarmTime, int exerciseId) {
        this.applicationStatus = applicationStatus;
        this.nextAlarmTime = nextAlarmTime;
        this.exerciseId = exerciseId;
    }

    public long getNextAlarmTime() {
        return nextAlarmTime;
    }

    public String getStringNextAlarmTime() {
        long time = this.getNextAlarmTime();
        return String.format("%ta %tT", time,time); //todo localize
    }

    public void setNextAlarmTime(Context context, long nextAlarmTime) {
        this.nextAlarmTime = nextAlarmTime;
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(nextAlarmTimeKey, nextAlarmTime);
        editor.apply();
    }

    public int getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Context context, int applicationStatus) {
        this.applicationStatus = applicationStatus;
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(applicationStatusKey, applicationStatus);
        editor.apply();
    }

    public int getExerciseId() {
        return this.exerciseId;
    }

    public void setExerciseId(Context context, int exerciseId) {
        this.exerciseId = exerciseId;
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(exerciseIdKey, exerciseId);
        editor.apply();
    }


    public static CurrentStatus getCurrentStatusFromFile(Context context) {
        MordanSoftLogger.addLog("getCurrentStatusFromFile START");
        CurrentStatus currentStatus = getCleanStatus();
        int applicationStatus;
        long nextAlarmTime;
        long currentTime = Calendar.getInstance().getTimeInMillis();
        int exerciseId;
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            applicationStatus = sharedPref.getInt(applicationStatusKey, applicationStatusDefault);
            nextAlarmTime = sharedPref.getLong(nextAlarmTimeKey, nextAlarmTimeDefault);
            exerciseId = sharedPref.getInt(exerciseIdKey, exerciseIdDefault);


            currentStatus = new CurrentStatus(applicationStatus,
                    nextAlarmTime,
                    exerciseId
            );

            if (applicationStatus != applicationStatusDefault) {                   //schedule is run or pending
                if (nextAlarmTime == nextAlarmTimeDefault ||
                        nextAlarmTime < currentTime ) {
                    currentStatus = currentStatus.run(context);
                }
            }
        } catch (Exception e){
            currentStatus.stop(context);
            MordanSoftLogger.addLog("getCurrentStatusFromFile Error: " + e, 'e');
        }
        MordanSoftLogger.addLog("getCurrentStatusFromFile END");
        return currentStatus;
    }

    public static CurrentStatus getCleanStatus() {
        return new CurrentStatus(
               applicationStatusDefault,
               nextAlarmTimeDefault, exerciseIdDefault
        );
    }

    public CurrentStatus run(Context context){
        MordanSoftLogger.addLog("CurrentStatus.run START");

        this.setNextAlarmTime(context, Schedule.run(context).getTimeInMillis());
        this.setApplicationStatus(context, applicationStatusActive);
        changeExercise(context);

        //setNextAlarmTime(context, String.format("%tT",(Alarm.run(context).getTime())));
        MordanSoftLogger.addLog("CurrentStatus.run END");
        return this;
    }

    public CurrentStatus stop(Context context){
        MordanSoftLogger.addLog("CurrentStatus.stop START");
        //Schedule.stop(context);
        this.setApplicationStatus(context, applicationStatusDefault);
        this.setNextAlarmTime(context,nextAlarmTimeDefault);
        MordanSoftLogger.addLog("CurrentStatus.stop END");
        return this;
    }

    public int changeExercise(Context context){
        int newExercise = Exercise.getRandomExercise(context).getId();
        this.setExerciseId(context, newExercise);
        return newExercise;
    }


}
