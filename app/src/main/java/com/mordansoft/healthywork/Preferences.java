package com.mordansoft.healthywork;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private int period;
    private int countdown;

    private static final int periodDefault = 60;
    private static final String periodKey = "PERIOD";
    private static final int countdownDefault = 0;
    private static final String countdownKey = "COUNTDOWN";

    public Preferences(int period, int countdown) {
        this.period = period;
        this.countdown = countdown;
    }

    public int getPeriod() {

        return period;
    }

    public void setPeriod(Context context, int period) {
        this.period = period;
        String filename = context.getString(R.string.activity_preferences_filename);
        SharedPreferences sharedPref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(periodKey, period);
        editor.apply();
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(Context context, int countdown) {
        this.countdown = countdown;
        String filename = context.getString(R.string.activity_preferences_filename);
        SharedPreferences sharedPref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(countdownKey, countdown);
        editor.apply();
    }

    public static Preferences getPreferencesFromFile(Context context) {
       int period;
       int countdown;

       String filename = context.getString(R.string.activity_preferences_filename);
       SharedPreferences sharedPref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);

       period = sharedPref.getInt("PERIOD", periodDefault);
       countdown = sharedPref.getInt("COUNTDOWN", countdownDefault);
       return new Preferences(period, countdown);
    }

    private static void initPreferences (Context context){
        String filename = context.getString(R.string.activity_preferences_filename);
        SharedPreferences sharedPref = context.getSharedPreferences(filename, Context.MODE_PRIVATE);

        if ( !sharedPref.contains(periodKey)){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(periodKey, periodDefault);
            editor.apply();
        }

        if ( !sharedPref.contains(countdownKey)){
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(countdownKey, countdownDefault);
            editor.apply();
        }

    }

}
