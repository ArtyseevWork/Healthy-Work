package com.mordansoft.healthywork;

import android.content.Context;
import android.content.SharedPreferences;

public class TodayStatistics {


    public static String exerciseDeltaKey ="EXERCISE_DELTA";
    private static final String fileName = "todayStatistics";
    private int exerciseId;
    private static final int exerciseIdDefault = 0;
    private static final String exerciseIdKey = "EXERCISE_ID";
    private int countOfExerciseDone;
    private static final int countOfExerciseDoneDefault = 0;
    private static final String countOfExerciseDoneKey="COUNT_OF_EXERCISE_DONE";
    private int countOfExerciseSkipped;
    private static final int countOfExerciseSkippedDefault = 0;
    private static final String countOfExerciseSkippedKey="COUNT_OF_EXERCISE_SKIPPED";

    public TodayStatistics(int exerciseId, int countOfExerciseDone, int countOfExerciseSkipped) {
        this.exerciseId = exerciseId;
        this.countOfExerciseDone = countOfExerciseDone;
        this.countOfExerciseSkipped = countOfExerciseSkipped;
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

    public void setCountOfExerciseDelta(Context context, int delta){ //delta show only plus or minus
        if (delta != 0){
            Exercise exercise = Exercise.getExerciseById(context,this.getExerciseId());
            int exerciseCount = exercise.getCount();
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
        int exerciseId;
        int countOfExerciseDone;
        int countOfExerciseSkipped;
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            exerciseId = sharedPref.getInt(exerciseIdKey, exerciseIdDefault);
            countOfExerciseDone = sharedPref.getInt(countOfExerciseDoneKey, countOfExerciseDoneDefault);
            countOfExerciseSkipped = sharedPref.getInt(countOfExerciseSkippedKey, countOfExerciseSkippedDefault);

            todayStatistics = new TodayStatistics(
                    exerciseId,
                    countOfExerciseDone,
                    countOfExerciseSkipped
            );


            return todayStatistics;
        } catch (Exception e){
            todayStatistics.stop(context);
            MordanSoftLogger.addLog("getTodayStatisticsFromFile Error: " + e, 'e');
        }
        MordanSoftLogger.addLog("getTodayStatisticsFromFile END");
        return todayStatistics;
    }

    public static TodayStatistics getCleanTodayStatistics() {
        return new TodayStatistics(
                exerciseIdDefault,
                countOfExerciseDoneDefault,
                countOfExerciseSkippedDefault
        );
    }

    public TodayStatistics recreate(Context context){
        this.stop(context);
        return this.run(context);
    }

    public TodayStatistics run(Context context){
        MordanSoftLogger.addLog("TodayStatistics.run START");

        if(exerciseId == exerciseIdDefault) {
            Exercise exercise = Exercise.getRandomExercise(context);
            this.setExerciseId(context, exercise.getId());
        }
        MordanSoftLogger.addLog("TodayStatistics.run END");
        return this;
    }

    public void stop(Context context){
        MordanSoftLogger.addLog("TodayStatistics.stop START");
        Schedule.stop(context);
        this.setExerciseId(context,exerciseIdDefault);
        this.setCountOfExerciseDone(context,countOfExerciseDoneDefault);
        this.setCountOfExerciseSkipped(context,countOfExerciseSkippedDefault);
        MordanSoftLogger.addLog("TodayStatistics.stop END");
    }
}
