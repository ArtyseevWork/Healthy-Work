package com.mordansoft.healthywork.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.mordansoft.healthywork.helpers.MordanSoftLogger;

public class TodayStatistics {


    public static String exerciseDeltaKey ="EXERCISE_DELTA";
    private static final String fileName = "todayStatistics";

    private int countOfExerciseDone;
    private static final int countOfExerciseDoneDefault = 0;
    private static final String countOfExerciseDoneKey="COUNT_OF_EXERCISE_DONE";
    private int countOfExerciseSkipped;
    private static final int countOfExerciseSkippedDefault = 0;
    private static final String countOfExerciseSkippedKey="COUNT_OF_EXERCISE_SKIPPED";

    public TodayStatistics(int countOfExerciseDone, int countOfExerciseSkipped) {
        this.countOfExerciseDone = countOfExerciseDone;
        this.countOfExerciseSkipped = countOfExerciseSkipped;
    }


    public void setCountOfExerciseDelta(Context context, int delta){ //delta show only plus or minus
        if (delta != 0){
            int exerciseCount = 1;
            if (delta > 0){
                setCountOfExerciseDone(context,getCountOfExerciseDone() + exerciseCount);
            } else {
                setCountOfExerciseSkipped(context,getCountOfExerciseSkipped() + exerciseCount);
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

    public void setCountOfExerciseSkipped(Context context,int countOfExerciseSkipped) {
        this.countOfExerciseSkipped = countOfExerciseSkipped;
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(countOfExerciseSkippedKey, countOfExerciseSkipped);
        editor.apply();
    }


    public static TodayStatistics getTodayStatisticsFromFile(Context context) {
        MordanSoftLogger.addLog("getTodayStatisticsFromFile START");
        TodayStatistics todayStatistics = getCleanTodayStatistics();
        int countOfExerciseDone;
        int countOfExerciseSkipped;
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            countOfExerciseDone = sharedPref.getInt(countOfExerciseDoneKey, countOfExerciseDoneDefault);
            countOfExerciseSkipped = sharedPref.getInt(countOfExerciseSkippedKey, countOfExerciseSkippedDefault);

            todayStatistics = new TodayStatistics(
                    countOfExerciseDone,
                    countOfExerciseSkipped
            );


            return todayStatistics;
        } catch (Exception e){
            todayStatistics.recreate(context);
            MordanSoftLogger.addLog("getTodayStatisticsFromFile Error: " + e, 'e');
        }
        MordanSoftLogger.addLog("getTodayStatisticsFromFile END");
        return todayStatistics;
    }

    public static TodayStatistics getCleanTodayStatistics() {
        return new TodayStatistics(
                countOfExerciseDoneDefault,
                countOfExerciseSkippedDefault
        );
    }

    public TodayStatistics recreate(Context context){
        MordanSoftLogger.addLog("TodayStatistics.stop START");
        Schedule.stop(context);
        this.setCountOfExerciseDone(context,countOfExerciseDoneDefault);
        this.setCountOfExerciseSkipped(context,countOfExerciseSkippedDefault);
        MordanSoftLogger.addLog("TodayStatistics.stop END");
        return this;
    }



}
