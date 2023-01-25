package com.mordansoft.healthywork.models;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private int period;

    private static final String fileName = "preferences";
    private static final int periodDefault = 60;
    private static final String periodKey = "PERIOD";
    private int countdown;
    private static final int countdownDefault = 0;
    private static final String countdownKey = "COUNTDOWN";

    private boolean firstStart;

    private static final boolean firstStartDefault = true;

    private static final String firstStartKey = "FIRST_START";


    public Preferences(int period, int countdown, boolean firstStart) {
        this.period = period;
        this.countdown = countdown;
        this.firstStart = firstStart;
    }

    public int getPeriod() {
        return period;
    }


    public boolean isFirstStart() {
        return firstStart;
    }

    public void setFirstStart(Context context, boolean firstStart) {
        this.firstStart = firstStart;
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(firstStartKey, firstStart);
        editor.apply();
    }

    public void setPeriod(Context context, int period) {
        this.period = period;
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(periodKey, period);
        editor.apply();
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(Context context, int countdown) {
        this.countdown = countdown;
        SharedPreferences sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(countdownKey, countdown);
        editor.apply();
    }

    public static Preferences getPreferencesFromFile(Context context) {
       int period;
       int countdown;
       boolean firstStart;

       SharedPreferences sharedPref = context.getSharedPreferences(Preferences.fileName, Context.MODE_PRIVATE);
       period = sharedPref.getInt(periodKey, periodDefault);
       countdown = sharedPref.getInt(countdownKey, countdownDefault);
       firstStart = sharedPref.getBoolean(firstStartKey, firstStartDefault);
       Preferences preferences = new Preferences(period, countdown, firstStart);
       firstRunWizard(context, preferences);
       return preferences;
    }

    private static void firstRunWizard(Context context, Preferences preferences){
        if (preferences.isFirstStart()){
            Exercise.insertConstantsData(context);
            preferences.setFirstStart(context, false);
        }
    }
}
