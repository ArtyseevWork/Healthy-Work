package com.mordansoft.healthywork.data.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.mordansoft.healthywork.data.MordanSoftLogger;
import com.mordansoft.healthywork.data.model.StatisticD;

public class StatisticStorageImplShPr implements StatisticStorage {

    private static final String fileName = "todayStatistics";
    private static final int countOfExerciseDoneDefault = 0;
    private static final String countOfExerciseDoneKey="COUNT_OF_EXERCISE_DONE";
    private static final int countOfExerciseSkippedDefault = 0;
    private static final String countOfExerciseSkippedKey="COUNT_OF_EXERCISE_SKIPPED";

    Context context;


    public StatisticStorageImplShPr(Context context) {
        this.context = context;
    }

    @Override
    public boolean updateStatistic(StatisticD statistic) {
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(countOfExerciseSkippedKey, statistic.getCountOfExerciseSkipped());
        editor.putInt(countOfExerciseDoneKey, statistic.getCountOfExerciseDone());
        editor.apply();
        return true;
    }

    @Override
    public StatisticD getStatistic() {
        MordanSoftLogger.addLog("getTodayStatisticsFromFile START");
        StatisticD statistic = new StatisticD(
                countOfExerciseDoneDefault,
                countOfExerciseSkippedDefault
        );
        int countOfExerciseDone;
        int countOfExerciseSkipped;
        try {
            SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
            countOfExerciseDone = sharedPref.getInt(countOfExerciseDoneKey, countOfExerciseDoneDefault);
            countOfExerciseSkipped = sharedPref.getInt(countOfExerciseSkippedKey, countOfExerciseSkippedDefault);

            statistic.setCountOfExerciseDone(countOfExerciseDone);
            statistic.setCountOfExerciseSkipped(countOfExerciseSkipped);


            return statistic;
        } catch (Exception e){
            MordanSoftLogger.addLog("getTodayStatisticsFromFile Error: " + e, 'e');
        }
        MordanSoftLogger.addLog("getTodayStatisticsFromFile END");
        return statistic;
    }
}
