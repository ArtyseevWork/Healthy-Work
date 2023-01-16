package com.mordansoft.healthywork;

import android.content.Context;
import android.content.SharedPreferences;

public class CurrentStatus {


    public static String exerciseDeltaKey ="EXERCISE_DELTA";
    private static final String fileName = "currentStatus";
    private int applicationStatus;
    public static final int applicationStatusDefault = 0;
    public static final int applicationStatusActive = 200;
    private static final String applicationStatusKey = "APPLICATION_STATUS";
    private int exerciseId;
    private static final int exerciseIdDefault = 0;
    private static final String exerciseIdKey = "EXERCISE_ID";
    private int countOfExerciseDone;
    private static final int countOfExerciseDoneDefault = 0;
    private static final String countOfExerciseDoneKey="COUNT_OF_EXERCISE_DONE";
    private int countOfExerciseSkipped;
    private static final int countOfExerciseSkippedDefault = 0;
    private static final String countOfExerciseSkippedKey="COUNT_OF_EXERCISE_SKIPPED";
    private String nextAlarmTime;
    private static final String nextAlarmTimeDefault = "";
    private static final String nextAlarmTimeKey="NEXT_ALARM_TIME";


    public CurrentStatus(int applicationStatus, int exerciseId, int countOfExerciseDone, int countOfExerciseSkipped, String nextAlarmTime) {
        this.applicationStatus = applicationStatus;
        this.exerciseId = exerciseId;
        this.countOfExerciseDone = countOfExerciseDone;
        this.countOfExerciseSkipped = countOfExerciseSkipped;
        this.nextAlarmTime = nextAlarmTime;
    }

    public String getNextAlarmTime() {
        return nextAlarmTime;
    }

    public void setNextAlarmTime(Context context, String nextAlarmTime) {
        this.nextAlarmTime = nextAlarmTime;
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(nextAlarmTimeKey, nextAlarmTime);
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
        return exerciseId;
    }

    public void setExerciseId(Context context,int exerciseId) {
        this.exerciseId = exerciseId;
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(exerciseIdKey, exerciseId);
        editor.apply();
    }

    public void setCountOfExerciseDelta(Context context, int delta){
        if (delta != 0){
            Exercise exercise = Exercise.getExerciseById(context,this.getExerciseId());
            int exerciseCount = exercise.getCount();
            if (delta < 0){
                setCountOfExerciseDone(context,exerciseCount);
            } else if (delta > 0) {
                setCountOfExerciseSkipped(context,exerciseCount);
            }
        }
    }

    public int getCountOfExerciseDone() {
        return countOfExerciseDone;
    }

    public void setCountOfExerciseDone(Context context,int countOfExerciseDone) {
        this.countOfExerciseDone = countOfExerciseDone;
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(countOfExerciseDoneKey, countOfExerciseDone);
        editor.apply();
    }

    public int getCountOfExerciseSkipped() {
        return countOfExerciseSkipped;
    }

    public void exerciseDonePlus(Context context, int count){
        setCountOfExerciseDone(context,this.getCountOfExerciseSkipped() + count);
    }

    public void exerciseSkipPlus(Context context, int count){
        setCountOfExerciseSkipped(context,this.getCountOfExerciseSkipped() + count);
    }

    public void setCountOfExerciseSkipped(Context context,int countOfExerciseSkipped) {
        this.countOfExerciseSkipped = countOfExerciseSkipped;
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(countOfExerciseSkippedKey, countOfExerciseSkipped);
        editor.apply();
    }


    public static CurrentStatus getCurrentStatusFromFile(Context context) {
        MordanSoftLogger.addLog("getCurrentStatusFromFile START");

        int applicationStatus;
        int exerciseId;
        int countOfExerciseDone;
        int countOfExerciseSkipped;
        String nextAlarmTime;
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            applicationStatus = sharedPref.getInt(applicationStatusKey, applicationStatusDefault);
            exerciseId = sharedPref.getInt(exerciseIdKey, exerciseIdDefault);
            countOfExerciseDone = sharedPref.getInt(countOfExerciseDoneKey, countOfExerciseDoneDefault);
            countOfExerciseSkipped = sharedPref.getInt(countOfExerciseSkippedKey, countOfExerciseSkippedDefault);
            nextAlarmTime = sharedPref.getString(nextAlarmTimeKey, nextAlarmTimeDefault);
            return new CurrentStatus(applicationStatus,
                    exerciseId,
                    countOfExerciseDone,
                    countOfExerciseSkipped,
                    nextAlarmTime
            );
        } catch (Exception e){
            MordanSoftLogger.addLog("getCurrentStatusFromFile Error: " + e, 'e');
        }
        return getCleanStatus();
    }

    public static CurrentStatus getCleanStatus() {
        return new CurrentStatus(
               applicationStatusDefault,
               exerciseIdDefault,
               countOfExerciseDoneDefault,
               countOfExerciseSkippedDefault,
               nextAlarmTimeDefault
        );
    }

    public CurrentStatus run(Context context){
        MordanSoftLogger.addLog("CurrentStatus.run START");

        if(exerciseId == exerciseIdDefault) {
            Exercise exercise = Exercise.getRandomExercise(context);
            this.setExerciseId(context, exercise.getId());
            this.setApplicationStatus(context, applicationStatusActive);
        }

        setNextAlarmTime(context, String.format("%tT",(Alarm.run(context).getTime())));
        MordanSoftLogger.addLog("CurrentStatus.run END");
        return this;
    }

    public CurrentStatus stop(Context context){
        MordanSoftLogger.addLog("CurrentStatus.stop START");
        Alarm.stop(context);
        this.setExerciseId(context,exerciseIdDefault);
        this.setApplicationStatus(context, applicationStatusDefault);
        this.setCountOfExerciseDone(context,countOfExerciseDoneDefault);
        this.setCountOfExerciseSkipped(context,countOfExerciseSkippedDefault);
        this.setNextAlarmTime(context,nextAlarmTimeDefault);
        MordanSoftLogger.addLog("CurrentStatus.stop END");
        return this;
    }
}
