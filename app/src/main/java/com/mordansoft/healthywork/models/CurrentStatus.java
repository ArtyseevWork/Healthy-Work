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


    public CurrentStatus(int applicationStatus, long nextAlarmTime) {
        this.applicationStatus = applicationStatus;
        this.nextAlarmTime = nextAlarmTime;
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


    public static CurrentStatus getCurrentStatusFromFile(Context context) {
        MordanSoftLogger.addLog("getCurrentStatusFromFile START");
        CurrentStatus currentStatus = getCleanStatus();
        int applicationStatus;
        long nextAlarmTime;
        long currentTime = Calendar.getInstance().getTimeInMillis();
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            applicationStatus = sharedPref.getInt(applicationStatusKey, applicationStatusDefault);
            nextAlarmTime = sharedPref.getLong(nextAlarmTimeKey, nextAlarmTimeDefault);
            currentStatus = new CurrentStatus(applicationStatus,
                    nextAlarmTime
            );

            if (applicationStatus != applicationStatusDefault) {                   //schedule is run or pending
                if (nextAlarmTime == nextAlarmTimeDefault ||
                        nextAlarmTime < currentTime ) {
                    TodayStatistics todayStatistics = TodayStatistics.getTodayStatisticsFromFile(context);
                    currentStatus = currentStatus.run(context);
                    //todayStatistics.setCountOfExerciseDelta(context,-1);
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
               nextAlarmTimeDefault
        );
    }

    public CurrentStatus run(Context context){
        MordanSoftLogger.addLog("CurrentStatus.run START");

        this.setNextAlarmTime(context, Schedule.run(context).getTimeInMillis());
        this.setApplicationStatus(context, applicationStatusActive);
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


}
