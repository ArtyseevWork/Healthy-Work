package com.mordansoft.healthywork.worker;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private static final String fileName = "preferences";
    private boolean firstStart;

    private static final boolean firstStartDefault = true;

    private static final String firstStartKey = "FIRST_START";


    public Preferences(boolean firstStart) {
        this.firstStart = firstStart;
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


    public static Preferences getPreferencesFromFile(Context context) {
       boolean firstStart;

       SharedPreferences sharedPref = context.getSharedPreferences(Preferences.fileName, Context.MODE_PRIVATE);
       firstStart = sharedPref.getBoolean(firstStartKey, firstStartDefault);
       Preferences preferences = new Preferences(firstStart);
       firstRunWizard(context, preferences);
       return preferences;
    }

    private static void firstRunWizard(Context context, Preferences preferences){
        if (preferences.isFirstStart()){
            //Exercise.insertConstantsData(context); //todo
            preferences.setFirstStart(context, false);
        }
    }
}
